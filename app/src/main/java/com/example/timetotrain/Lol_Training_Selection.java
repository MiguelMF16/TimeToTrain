package com.example.timetotrain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Lol_Training_Selection extends AppCompatActivity {
    private Button btnwards;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lol__training__selection);
        btnwards = findViewById(R.id.btnwards);
        btnwards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lol_Training_Selection.this, Wards_Training.class);
                startActivityForResult(intent, 0);
            }
        });


    }
}
