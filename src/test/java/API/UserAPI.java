package API;

import Pages.ActorPage;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.slf4j.Logger;

import java.io.*;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.slf4j.LoggerFactory.getLogger;

public class UserAPI {
    String key, sessionID, requestToken, expiresAt;
    Methods methods = new Methods();
    int listID;

    private static final Logger log = getLogger(UserAPI.class);

    public void setListID(int listID) { this.listID = listID; }

    public int getListID() {  return listID; }

    public void setKey(String key) {
        this.key = key;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    public void setRequestToken(String requestToken) {
        this.requestToken = requestToken;
    }

    @Step("Setting credentials")
    public UserAPI setCredentials() throws IOException {
        Properties properties = new Properties();
        InputStream inCred = new FileInputStream("Credentials.properties");
        properties.load(inCred);
        inCred.close();
        UserAPI user = new UserAPI();
        user.setKey(properties.getProperty("Key"));
        if(methods.compare(properties.getProperty("Expires_at"))){
            log.info("New credentials needed");
            user.requestToken()
                    .validateToken(properties.getProperty("Username"),properties.getProperty("Password"))
                    .createSession();
            properties.setProperty("Session_id",user.sessionID);
            properties.setProperty("Expires_at",user.expiresAt);
            properties.setProperty("RequestToken",user.requestToken);
            OutputStream outCred = new FileOutputStream("Credentials.properties");
            properties.store(outCred,null);
        }else{
            log.info("Valid credentials");
            user.setSessionID(properties.getProperty("Session_id"));
        }
        return user;
    }

    @Step("Request token")
    public UserAPI requestToken(){
        setRequestToken(
                given().params("api_key",this.key)
                .when().get("https://api.themoviedb.org/3/authentication/token/new")
                .then().assertThat()
                    .statusCode(200)
                    .body("success",is(true))
                    .and().extract().path("request_token"));
        return this;
    }

    @Step("Validate token")
    public UserAPI validateToken(String username,String password){
        JSONObject body = new JSONObject();
        body.put("username",username);
        body.put("password",password);
        body.put("request_token",this.requestToken);
        setExpiresAt(given().contentType(ContentType.JSON)
                        .accept(ContentType.JSON).body(body.toJSONString())
                    .when().post("https://api.themoviedb.org/3/authentication/token/validate_with_login?api_key="+this.key)
                    .then().assertThat()
                        .statusCode(200)
                        .body("success",is(true))
                        .and().extract().path("expires_at"));
        return this;
    }

    @Step("Create session")
    public UserAPI createSession(){
        JSONObject body = new JSONObject();
        body.put("request_token",this.requestToken);
        setSessionID(
                given().contentType(ContentType.JSON)
                    .accept(ContentType.JSON).body(body.toJSONString())
                .when().post("https://api.themoviedb.org/3/authentication/session/new?api_key="+this.key)
                .then().assertThat()
                    .statusCode(200)
                    .body("success",is(true))
                    .and().extract().path("session_id"));
        return this;
    }

    @Step("Create list")
    public UserAPI createList(String genre, String description){
        log.info("Create list "+genre);
        JSONObject body = new JSONObject();
        body.put("name",genre);
        body.put("description",description);
        body.put("language","en");
        setListID(given().contentType(ContentType.JSON).accept(ContentType.JSON).body(body.toJSONString())
                .when().post("/list?api_key="+key+"&session_id="+sessionID)
                .then().assertThat()
                    .statusCode(201)
                    .body("success",is(true))
                    .and().log().body()
                    .and().extract().path("list_id"));

        log.info("List id "+listID);
        return this;
    }

    @Step("Add movie to list")
    public UserAPI addMovie(String movieName, int movieID) throws IOException {
        log.info("Adding movie '"+movieName+"' to list "+ this.listID);
        JSONObject body = new JSONObject();
        body.put("media_id",movieID);
        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(body.toJSONString())
                .when().post("/list/"+listID+"/add_item?api_key="+key+"&session_id="+sessionID)
                .then().assertThat()
                    .statusCode(201)
                    .body("status_message",is( "The item/record was updated successfully."))
                    .and().log().body();
        return this;
    }
}
