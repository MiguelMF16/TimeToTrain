package com.example.timetotrain;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

public class Wards_Training extends AppCompatActivity {
    private VideoView videowards;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wards__training);
        videowards = findViewById(R.id.videoWard);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.prueba;
        videowards.setVideoURI(Uri.parse(path));
        videowards.start();
    }
}
