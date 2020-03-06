package com.example.timetotrain;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;

public class Wards_Training extends AppCompatActivity {
    private VideoView videowards;
    private Button btnplaypause;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wards_training);
        videowards = findViewById(R.id.videoWard);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.prueba;
        videowards.setVideoURI(Uri.parse(path));
        btnplaypause = findViewById(R.id.btnplay);

        btnplaypause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(videowards.isPlaying()){
                    videowards.pause();
                    btnplaypause.setBackgroundResource(R.drawable.play);
                }else{
                    videowards.start();
                    btnplaypause.setBackgroundResource(R.drawable.pause);
                }

            }
        });

    }
    private FirebaseAuth firebaseauth;
    private FirebaseAuth.AuthStateListener firebaseauthListener;

    private void logOut() {
        firebaseauth.signOut();
    }

}
