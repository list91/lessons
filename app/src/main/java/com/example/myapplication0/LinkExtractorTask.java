package com.example.myapplication0;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkExtractorTask extends AsyncTask<String, Void, List<String>> {

    private static final String TAG = LinkExtractorTask.class.getSimpleName();

    @Override
    protected List<String> doInBackground(String... urls) {
        List<String> links = new ArrayList<>();

        if (urls.length > 0) {
            String urlString = urls[0];
            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
                reader.close();
                conn.disconnect();

                String patternString = "<a\\s+(?:[^>]*?\\s+)?href=(['\"])(.*?)\\1";
                Pattern pattern = Pattern.compile(patternString);
                Matcher matcher = pattern.matcher(content.toString());

                while (matcher.find()) {
                    links.add(matcher.group(2));
                }
            } catch (IOException e) {
                Log.e(TAG, "URL ошибка: " + e.getMessage());
            } catch (Exception e) {
                Log.e(TAG, "Произошла ошибка при получении ссылок");
            }
        }

        return links;
    }

    @Override
    protected void onPostExecute(List<String> links) {
        Log.d(TAG, "Ссылки на странице:");
        for (String link : links) {
            Log.d(TAG, link);
        }
    }
}