package Factory;

public class Factory {
    public static Driver build(String type){
        switch (type){
            case "Chrome":
                return new Chrome();
            case "FireFox":
                return new FireFox();
            case "Edge":
                return new Edge();
            default:
                System.out.println("The entered browser is not valid");
                return null;
        }
    }
}
