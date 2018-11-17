package com.test.seefood;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class API extends AsyncTask<Void, Void, Void> {
    Bitmap bitmap;
    Image image;
    boolean responseBool;

    API(Bitmap bitmap, Image image) {
        this.bitmap = bitmap;
        this.image = image;
    }

    @Override
    protected Void doInBackground(Void... Void) {
        try{
            // send images list up to api to get tested by AI
            // open socket to connect to server
            Socket socket = new Socket("18.188.220.241", 9090);
            OutputStream outputStream = socket.getOutputStream();

            // receive images and convert to byte array output stream
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);

            // send size of image and image itself
            byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
            outputStream.write(size);
            outputStream.write(byteArrayOutputStream.toByteArray());
            outputStream.flush();

            // receive response from AI
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = input.readLine();
            image.setConfidenceLevel(input.readLine());
            response += input.readLine();

            if (response.contains("I see food!")) responseBool = true;
            else responseBool = false;

            outputStream.flush();
            outputStream.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        image.setIsFood(responseBool);
    }
}
