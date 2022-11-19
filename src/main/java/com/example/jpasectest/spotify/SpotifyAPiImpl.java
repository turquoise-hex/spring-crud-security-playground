package com.example.jpasectest.spotify;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

@Component
public class SpotifyAPiImpl {

    private String token = "BQByWfcVB-BdyEHHRnQgPLkTCyV4P5-d_GNc2ovgzN-Ml7dhoA2IEjm9eIqad9xuW2wAjb6HQWaoVKXNVDwPHgmjk2MEbpVndHmVpUaEIKqUcTCwykg";

    public SpotifyAPiImpl(){

    }

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
}
