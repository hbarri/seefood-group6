package com.test.seefood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class splashActivity extends AppCompatActivity {

    /**
     * onCreate method called on activity start up
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // sets loading view on application start up
        Intent intent = new Intent(this, LoadingView.class);
        startActivity(intent);
        finish();
    }

}
