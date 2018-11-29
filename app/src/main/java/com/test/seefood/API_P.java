package com.test.seefood;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.GridView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;

public class API_P extends AsyncTask<Void, Void, Void> {
    List<Image> images;
    List<Image> imagesToConfirm;
    GridView gridView;
    ImageAdapter adapter;

    API_P(List<Image> imagesToConfirm, List<Image> images, GridView gridView, ImageAdapter adapter) {
        this.imagesToConfirm = imagesToConfirm;
        this.images = images;
        this.gridView = gridView;
        this.adapter = adapter;

    }

    @Override
    protected Void doInBackground(Void... Void) {
        for (int i = 0; i < imagesToConfirm.size(); i++) {
            Bitmap bm = imagesToConfirm.get(i).getImage();
            sendImage(bm);
        }

        return null;
    }

    public void sendImage(Bitmap bitmap) {
        // receive imagesToConfirm and convert to byte array output stream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        // create JSON object
        JSONObject json = new JSONObject();
        String output = "";

        try {
            json.put("byte", encodedImage);

            // set url to ec2 server
            URL url = new URL("http://18.188.220.241:5000/api/images");

            // create HttpURLConnection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

            // add JSON content to POST request body
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(json.toString());
            writer.flush();
            writer.close();
            os.close();

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String line;
            while ((line = br.readLine()) != null) {
                output += line;
            }

            // make POST request to the given URL
            //conn.connect();
            conn.getResponseMessage();
            conn.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        parseList(output);
    }

    public void parseList(String toParse) {
        String[] tokens = toParse.split(Pattern.quote("{"));

        Image newImage = new Image();
        String[] moreTokens = tokens[2].split(Pattern.quote(","));

        for (int j = 0; j < moreTokens.length; j++) {
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
                substring = substring.replaceAll("[}]", "");
                newImage.setIsFood(Boolean.parseBoolean(substring));
            }
        }
        images.add(newImage);

        //adapter.notifyDataSetChanged();
        //gridView.setAdapter(adapter);
    }

    //@Override
    //protected void onPostExecute(Void aVoid) {
    //    adapter.notifyDataSetChanged();
    //    gridView.setAdapter(adapter);
    //}

    public Bitmap byteToBitmap(String bytes) {
        byte[] decodedString = Base64.decode(bytes, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return decodedByte;
    }
}
