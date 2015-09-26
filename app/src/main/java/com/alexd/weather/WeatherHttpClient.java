package com.alexd.weather;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherHttpClient {
    public String getWeatherData(String longitude, String latitude) {
        HttpURLConnection con = null ;
        InputStream is = null;

        try {
            String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?units=metric";
            String url = String.format("%s&lat=%s&lon=%s", BASE_URL, latitude, longitude);
            con = (HttpURLConnection) ( new URL(url)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            StringBuilder buffer = new StringBuilder();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while (  (line = br.readLine()) != null )
                buffer.append(line).append("\r\n");

            is.close();
            con.disconnect();
            return buffer.toString();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch(Throwable ignored) {}
            try {
                if (con != null) {
                    con.disconnect();
                }
            } catch(Throwable ignored) {}
        }

        return null;

    }

    public Bitmap getImage(String code) {
        try {
            String IMG_URL = "http://openweathermap.org/img/w/";
            String src = String.format("%s%s.png", IMG_URL, code);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
