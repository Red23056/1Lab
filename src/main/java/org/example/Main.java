package org.example;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class Main
{
    static String url_encoder(String encoded_string){
        try {
            encoded_string = URLEncoder.encode(encoded_string,"UTF-8");
            encoded_string = "https://ru.wikipedia.org/w/api.php?action=query&list=search&utf8=&format=json&srsearch=" + encoded_string;
            return(encoded_string);
        } catch (Exception error) {
            System.out.println("Error" + error.toString());
            return "";
        }
    }

    static void open_browser(int command, json_to_java complete_responce_to_read) {
        boolean wrong_page = true;
        while (wrong_page){
            if ((command < complete_responce_to_read.query.search.size()) && (command >= 0)) {
                try {
                    URI page = new URI("https://ru.wikipedia.org/w/index.php?curid=" + complete_responce_to_read.query.search.get(command).pageid);
                    java.awt.Desktop.getDesktop().browse(page);
                    wrong_page = false;
                } catch (Exception e) {
                    System.out.println("ERROR " + e.toString());
                }
            } else {
                System.out.println("Некорректный номер страницы!!!");
            }
        }
    }

    static void formating_question(String search_question, Scanner scanner) {
        search_question = url_encoder(search_question);
        String complete_responce_of_wiki_API = null;
        try {
            URL url = new URL(search_question);
            HttpURLConnection http_url_connection = (HttpURLConnection) url.openConnection();
            int responce_for_check = http_url_connection.getResponseCode();
            if (responce_for_check == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(http_url_connection.getInputStream()));
                String buffer;
                StringBuilder response_of_wiki_buffer = new StringBuilder();
                while ((buffer = reader.readLine()) != null) {
                    response_of_wiki_buffer.append(buffer);
                }
                complete_responce_of_wiki_API = response_of_wiki_buffer.toString();
                reader.close();
            }
        } catch (Exception error) {
            System.out.println("Error" + error.toString());
            return;
        }
        Parser parser = new Parser();
        json_to_java complete_responce_to_read = parser.parse(complete_responce_of_wiki_API);
        System.out.println("Результаты поиска:");
        if (complete_responce_to_read.query.search.isEmpty()) {
            System.out.println("Ничего не найдено");
            return;
        }
        for (int i = 0; i < complete_responce_to_read.query.search.size(); i++) {
            System.out.printf("%d - %s\n", i, complete_responce_to_read.query.search.get(i).title);
        }
        System.out.print("Выберите страницу: ");
        int command = scanner.nextInt();
        open_browser(command, complete_responce_to_read);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите запрос: ");
        String request_search = scanner.nextLine();
        if (request_search.isEmpty()) {
            System.out.println("Вы ввели пустой запрос!!!");
            return;
        }
        formating_question(request_search, scanner);
    }
}