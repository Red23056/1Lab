package org.example;
import com.google.gson.Gson;

public class Parser {
    public json_to_java parse(String complete_responce_of_wiki_API){

        Gson gson = new Gson();
        json_to_java java_json = null;
        java_json = gson.fromJson(complete_responce_of_wiki_API, json_to_java.class);
        return java_json;
    }
}
