package com.test.seefood;

import android.os.AsyncTask;
import java.net.HttpURLConnection;
import java.net.URL;

public class API_O extends AsyncTask<Void, Void, Void> {
    String cmd;
    String request;

    /**
     * constructor to store command to run with request
     * opens connection with server
     * @param cmd
     * @param request
     */
    API_O(String cmd, String request) {
        this.cmd = cmd;
        this.request = request;
    }

    /**
     * AsyncTask to set connection with server
     * @param Void
     * @return
     */
    @Override
    protected Void doInBackground(Void... Void) {
        try {
            // set url to ec2 server
            String urlSt = "http://18.188.220.241:5000/api/" + cmd;
            URL url2 = new URL(urlSt);

            // connects to server
            HttpURLConnection conn3 = (HttpURLConnection) url2.openConnection();
            conn3.setRequestMethod(request);
            conn3.setRequestProperty("Content-Type", "application/json; charset=utf-8");

            // gets response and disconnects
            conn3.getResponseMessage();
            conn3.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
