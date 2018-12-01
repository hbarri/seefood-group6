package com.test.seefood;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.GridView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;

public class


API_O extends AsyncTask<Void, Void, Void> {
    String cmd;

    API_O(String cmd) {
        this.cmd = cmd;
    }

    @Override
    protected Void doInBackground(Void... Void) {
        try {
            // set url to ec2 server
            String urlSt = "http://18.188.220.241:5000/api/" + cmd;
            URL url2 = new URL(urlSt);

            HttpURLConnection conn3 = (HttpURLConnection) url2.openConnection();
            conn3.setRequestMethod("GET");
            conn3.setRequestProperty("Content-Type", "application/json; charset=utf-8");

            //conn3.connect();
            conn3.getResponseMessage();
            conn3.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
