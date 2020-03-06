package com.example.timetotrain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class TrainingMenu1 extends AppCompatActivity{
    private FirebaseAuth firebaseauth;
    private FirebaseAuth.AuthStateListener firebaseauthListener;
    private Button btnlol;
    private Button btncod;



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menusobrenosotros:
                FragmentManager dialogoaboutus = getSupportFragmentManager();
                AboutUs aboutus = new AboutUs();
                aboutus.show(dialogoaboutus, "AboutUs");
                break;
            case R.id.menuexit:
                logOut();
                break;


        }
        return true;
    }

    private void logOut() {
        firebaseauth.signOut();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_menu1);
        btnlol = findViewById(R.id.btnlol);
        btncod = findViewById(R.id.botoncallofdutty);
        btnlol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent intent = new Intent(TrainingMenu1.this, Lol_Training_Selection.class );
              startActivityForResult(intent, 0);
            }
        });



    }
}
