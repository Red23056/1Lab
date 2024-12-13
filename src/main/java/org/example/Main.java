package org.example;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.ArrayList;

public class Main
{
    static String url_encoder(String encoded_string){
        try {
            encoded_string = URLEncoder.encode(encoded_string,"UTF-8");
            encoded_string = "https://ru.wikipedia.org/w/api.php?action=query&list=search&utf8=&format=json&srsearch=" + encoded_string;
            System.out.println(encoded_string);
            return(encoded_string);
        } catch (Exception error) {
            System.out.println("Error" + error.toString());
            return " ";
        }
    }

    static String formating_question(String search_question, Scanner scanner){
        search_question = url_encoder(search_question);
        InputStream input_stream = null;
        FileOutputStream output_stream_in_file = null;
        try{
            URL url = new URL(search_question);
            HttpURLConnection http_url_connection = (HttpURLConnection)url.openConnection();
            int responce = http_url_connection.getResponseCode();
            if (responce == HttpURLConnection.HTTP_OK){
                input_stream = http_url_connection.getInputStream();
                File file = new File("file.json");
                output_stream_in_file = new FileOutputStream(file);
                int byteRead = -1;
                byte[] buffer = new byte[2048];
                while((byteRead = input_stream.read(buffer)) != -1) {
                    output_stream_in_file.write(buffer, 0, byteRead);
                }
            }
        } catch(Exception error){
            System.out.println("Error" + error.toString());
        }
        finally {
            try {
                input_stream.close();
                output_stream_in_file.close();
            } catch (Exception error){
                System.out.println("Error" + error);
            }
        }

        return
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите запрос: ");
        String request_search = scanner.nextLine();
        System.out.print(scanner);
        System.out.print(request_search);
        if (request_search.isEmpty()) {
            System.out.println("Ошибка, вы ввели пустой запрос!!!");
            return;
        }
        formating_question(request_search, scanner);
    }
}