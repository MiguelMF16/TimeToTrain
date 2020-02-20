package com.example.timetotrain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class TrainingMenu1 extends AppCompatActivity{
    private FirebaseAuth firebaseauth;
    private FirebaseAuth.AuthStateListener firebaseauthListener;



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
    }
}
