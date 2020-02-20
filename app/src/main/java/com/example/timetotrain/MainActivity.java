package com.example.timetotrain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private Button btnlogin;
    private Button btnregistro;
    private TextView txtnombre, txtpassword;
    private GoogleApiClient googleApiClient;
    private SignInButton btngoogle;
    private Intent intent;
    private FirebaseAuth firebaseauth;
    private FirebaseAuth.AuthStateListener firebaseauthListener;
    private ProgressBar progressbar;
    private static final int SIGN_IN_CODE = 777;


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
    public void logOut(){
        firebaseauth.signOut();
       // Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback((ResultCallback)(status));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this, TrainingMenu1.class);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        progressbar = findViewById(R.id.cargalogin);
        txtnombre = findViewById(R.id.txtnombre);
        txtpassword = findViewById(R.id.txtpassword);
        googleApiClient = new GoogleApiClient.Builder
                (this).enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        btngoogle = findViewById(R.id.googleacount);
        btngoogle.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN_CODE);
            }

        });
        firebaseauth = FirebaseAuth.getInstance();
        firebaseauthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    goMainScreen();
                }
            }
        };
        btnregistro = findViewById(R.id.btnregistro);
        btnlogin = findViewById(R.id.btnlogin);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //boton login

                String nombretexto = txtnombre.getText().toString();
                if (comprobarExistencia(nombretexto) == 1) {
                    intent = new Intent(MainActivity.this, TrainingMenu1.class);
                    startActivityForResult(intent, 0);
                } else {

                }
            }
        });

        btnregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(MainActivity.this, "TTTDB", null, 1);
                SQLiteDatabase basededatos = admin.getWritableDatabase();
                String nombreuser = txtnombre.getText().toString();
                String passworduser = txtpassword.getText().toString();
                if (nombreuser.isEmpty() || passworduser.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Campos vacíos", Toast.LENGTH_SHORT).show();
                } else {
                    if (comprobarExistencia(nombreuser) == -1) {
                        ContentValues registro = new ContentValues();
                        registro.put("nombre", nombreuser);
                        registro.put("password", passworduser);
                        basededatos.insert("usuario", null, registro);
                        Toast.makeText(MainActivity.this, "Usuario registrado", Toast.LENGTH_SHORT).show();
                        basededatos.close();
                    } else {
                        Toast.makeText(MainActivity.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);

        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            //goMainScreen();
            FireBaseAuthWithGoogle(result.getSignInAccount());
        } else {
            Toast.makeText(this, "Error en el inicio de Sesión", Toast.LENGTH_SHORT).show();
        }
    }

    private void FireBaseAuthWithGoogle(GoogleSignInAccount signInAccount) {
        progressbar.setVisibility(View.VISIBLE);
        btngoogle.setVisibility(View.GONE);
        AuthCredential credencial = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
        firebaseauth.signInWithCredential(credencial).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressbar.setVisibility(View.GONE);
                btngoogle.setVisibility(View.VISIBLE);
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Error en firebase", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void goMainScreen() {

        intent = new Intent(this, TrainingMenu1.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseauth.addAuthStateListener(firebaseauthListener);
    }

    public int comprobarExistencia(String buscarusuario) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "TTTDB", null, 1);
        SQLiteDatabase basededatos = admin.getWritableDatabase();
        Cursor filas = basededatos.rawQuery("Select COUNT(*) from usuario where nombre like '" + buscarusuario + "'", null);

        if (filas.moveToFirst()) {
            String resultado = filas.getString(0);
            if (resultado.equals("1")) {
                Toast.makeText(this, "El usuario ya existe.", Toast.LENGTH_SHORT).show();
                basededatos.close();
                return 1;
            }
        }
        basededatos.close();
        return -1;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseauthListener != null) {
            firebaseauth.removeAuthStateListener(firebaseauthListener);
        }
    }
}
