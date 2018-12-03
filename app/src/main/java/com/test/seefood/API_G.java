package com.test.seefood;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.GridView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;

public class API_G extends AsyncTask<Void, Void, Void> {
    List<Image> images;
    Context context;

    API_G(List<Image> images, Context context) {
        this.images = images;
        this.context = context.getApplicationContext();
    }

    @Override
    protected Void doInBackground(Void... Void) {
        String output = "";

        // retrieve imagesToConfirm
        try {
            URL url = new URL("http://18.188.220.241:5000/api/images");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String line;
            while ((line = br.readLine()) != null) {
                output += line;
            }

            br.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        parseList(output);
        Log.d("CREATION", "done retrieving images into gallery");

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Intent intent = new Intent(context, SeeFood.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void parseList(String toParse) {
        String[] tokens = toParse.split(Pattern.quote("}"));

        for (int i = 1; i < tokens.length-1; i++) {
            Image newImage = new Image();
            String[] moreTokens = tokens[i].split(Pattern.quote(","));

            for (int j = 1; j < moreTokens.length; j++) {
                String[] moreSubstrings1 = moreTokens[j].split(Pattern.quote(":"));
                String substring1 = moreSubstrings1[0];
                substring1 = substring1.replaceAll("[{\"]", "");

                String substring;

                if (substring1.equals("byte")) {
                    String[] moreSubstrings = moreTokens[j].split(Pattern.quote(":"));
                    substring = moreSubstrings[1];
                    substring = substring.replaceAll("[\"]", "");
                    substring = substring.replaceAll("\\\\n", "\n");
                    newImage.createImage(byteToBitmap(substring));
                }
                if (substring1.equals("confidence")) {
                    String[] moreSubstrings = moreTokens[j].split(Pattern.quote(":"));
                    substring = moreSubstrings[1];
                    substring = substring.replaceAll("[\"]", "");
                    newImage.setConfidenceLevel(substring);
                }
                if (substring1.equals("id")) {
                    String[] moreSubstrings = moreTokens[j].split(Pattern.quote(":"));
                    substring = moreSubstrings[1];
                    int id = Integer.parseInt(substring);
                    newImage.setId(id);
                    newImage.setName("image_" + id);
                }
                if (substring1.equals("isFood")) {
                    String[] moreSubstrings = moreTokens[j].split(Pattern.quote(":"));
                    substring = moreSubstrings[1];
                    newImage.setIsFood(Boolean.parseBoolean(substring));
                }
                if (substring1.equals("isFavorite")) {
                    String[] moreSubstrings = moreTokens[j].split(Pattern.quote(":"));
                    substring = moreSubstrings[1];
                    newImage.setIsFavorite(Boolean.parseBoolean(substring));
                }
            }
            images.add(newImage);
        }
    }

    public Bitmap byteToBitmap(String bytes) {
        byte[] decodedString = Base64.decode(bytes, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return decodedByte;
    }
}
