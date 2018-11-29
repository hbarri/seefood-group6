package com.test.seefood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class LoadingView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_view);


        initialize();
    }

    public void initialize() {
        // start ec2 instance

        // start python server
        //Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"cd C:\\Users\\hanee_000\\Desktop && ssh -i \"4110key.pem\" ubuntu@18.188.220.241\"");

        // run api to get image database
        API_O api = new API_O();
        api.execute();

        // run second api to parse imagesToConfirm from database and store in gallery
        API_G api2 = new API_G(GalleryView.getImages(), getBaseContext());
        api2.execute();
    }
}
