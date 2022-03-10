package API;

import au.com.bytecode.opencsv.CSVReader;

import java.io.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Methods {
    public Boolean compare(String expDate){
        Timestamp tm1,tm2;
        tm1=Timestamp.valueOf(expDate.substring(0,19));
        LocalDateTime local=LocalDateTime.now(ZoneOffset.UTC);
        tm2=Timestamp.valueOf(local);
        return tm1.compareTo(tm2) < 0;
    }

    public Object[][] readFile(String path) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(path), ',', '"' , 0);
        List<String[]> allRows = reader.readAll();
        Object[][] obj = new Object[allRows.size()][];
        for(int i=0; i < allRows.size();i++){
            obj[i]= allRows.get(i);
        }
        reader.close();
        return obj;
    }
}
