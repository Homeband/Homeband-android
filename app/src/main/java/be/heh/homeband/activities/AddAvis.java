package be.heh.homeband.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import be.heh.homeband.R;

public class AddAvis extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_avis);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){

        this.finish();
        return true;
    }
}
