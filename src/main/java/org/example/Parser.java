package org.example;
import com.google.gson.Gson;
import java.io.FileReader;
import java.util.ArrayList;

public class Parser {
    public json_to_java parse(){

        Gson gson = new Gson();
        json_to_java java_json = null;

        try(FileReader reader = new FileReader("file.json")){
            java_json = gson.fromJson(reader, json_to_java.class);
            return java_json;
        }catch (Exception e){
            System.out.println("ERROR " + e.toString());
        }

        return null;
    }
}
