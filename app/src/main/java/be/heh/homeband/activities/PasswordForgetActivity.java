package be.heh.homeband.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import be.heh.homeband.R;
import be.heh.homeband.app.HomebandTools;
import be.heh.homeband.entities.Utilisateur;

public class PasswordForgetActivity extends AppCompatActivity {

    Button btnSendMail;
    Button btnCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Hide Action Bar
        getSupportActionBar().hide();

        // Hide Navigation Bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);

        // Set ContentView
        setContentView(R.layout.activity_mot_de_passe_oublie);

        init();
    }

    public void init(){
        btnSendMail = findViewById(R.id.btnSendMail);
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), LoginActivity.class);
                startActivity(i);
            }
        });

    }
}
