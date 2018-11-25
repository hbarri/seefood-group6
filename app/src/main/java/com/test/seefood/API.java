package com.test.seefood;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class API extends AsyncTask<Void, Void, Void> {
    Bitmap bitmap;

    API(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    protected Void doInBackground(Void... Void) {
        // receive images and convert to byte array output stream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        // create JSON object
        JSONObject json = new JSONObject();

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

            // make POST request to the given URL
            //conn.connect();
            conn.getResponseMessage();

            URL url2 = new URL("http://18.188.220.241:5000/api/close");

            HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
            conn2.setRequestMethod("GET");
            conn2.setRequestProperty("Content-Type", "application/json; charset=utf-8");

            //conn2.connect();
            conn2.getResponseMessage();

            conn.disconnect();
            conn2.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
