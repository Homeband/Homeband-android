package be.heh.homeband.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;

import be.heh.homeband.R;

public class LoginActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_login);

    }
}
