package com.example.jpasectest.spotify;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Component
public class SpotifyAPiImpl {

    private String token = "";

    @Value("${spotify.auth.token}")
    private String auth;

    public SpotifyAPiImpl(){

    }
    //TODO:Implement separating artistName and albumName words with "-" if they consist of multiple words,
    //  or else the request url is broken
    public String getAlbumUrl(String artistName, String albumName) throws IOException {
        String urlString ="https://api.spotify.com/v1/search?q=" + artistName + "-" + albumName
                + "&type=album&access_token=" + token;
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject jsonResponse = new JSONObject(response.toString());
        JSONObject albums = jsonResponse.getJSONObject("albums");
        JSONArray items = albums.getJSONArray("items");
        JSONObject imagesObject = items.getJSONObject(0);
        JSONArray images = imagesObject.getJSONArray("images");
        JSONObject image = images.getJSONObject(0);
        String imgUrl = image.getString("url");

        return imgUrl;

    }

    @Scheduled(fixedDelay = 3000000)
    public void refreshToken() throws IOException {
        String urlParameters = "grant_type=client_credentials";

        URL url = new URL("https://accounts.spotify.com/api/token");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", auth);

        byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );

        con.setDoOutput(true);
        con.setInstanceFollowRedirects(false);
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("charset", "utf-8");
        con.setUseCaches(false);
        try(DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            wr.write(postData);
        }
        StringBuilder sb = new StringBuilder();
        int HttpResult = con.getResponseCode();

        BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"));
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        br.close();

        JSONObject jsonResponse = new JSONObject(sb.toString());
        System.out.println(jsonResponse.getString("access_token"));
        token = jsonResponse.getString("access_token");

    }
}
