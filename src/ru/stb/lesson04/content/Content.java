package ru.stb.lesson04.content;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Content {
    public static void readConnect(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        URLConnection connection = url.openConnection();

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

        StringBuilder content = new StringBuilder();
        String current;
        while((current = in.readLine()) != null)
        {
            content.append(current).append("\r\n");
        }
        System.out.println(content);
    }
}
