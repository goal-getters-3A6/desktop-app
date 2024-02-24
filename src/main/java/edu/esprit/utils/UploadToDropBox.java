package edu.esprit.utils;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UploadToDropBox {
    private static final String URL = "https://api.dropbox.com/oauth2/token";
    private static final String CODE = "qF29mvpd3kcAAAAAAAAAGiAlPT6C8p1jUNCDOikiYvA";
    private static final String GRANT_TYPE = "authorization_code";
    private static final String CLIENT_ID = "29jc4g04ebdszbm";
    private static final String CLIENT_SECRET = "7el38mr9szx12fu";
    private static String ACCESS_TOKEN;

    static {
        try {
            String urlParameters = "code=" + CODE +
                    "&grant_type=" + GRANT_TYPE +
                    "&client_id=" + CLIENT_ID +
                    "&client_secret=" + CLIENT_SECRET;
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(urlParameters))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            ACCESS_TOKEN = response.body();
            System.out.println(ACCESS_TOKEN); // For debugging purposes
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to get access token");
        }
    }

    public static String uploadPhoto(String path, String name) {
        DbxRequestConfig config = new DbxRequestConfig("dropbox/go-fit-pro");
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
        // Use client for further operations
        return null; // Placeholder return
    }
}
