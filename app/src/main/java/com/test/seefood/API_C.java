package com.test.seefood;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.GridView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;

public class API_C extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... Void) {
        try {
            URL url2 = new URL("http://18.188.220.241:5000/api/close");

            HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
            conn2.setRequestMethod("GET");
            conn2.setRequestProperty("Content-Type", "application/json; charset=utf-8");

            //conn2.connect();
            conn2.getResponseMessage();
            conn2.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
