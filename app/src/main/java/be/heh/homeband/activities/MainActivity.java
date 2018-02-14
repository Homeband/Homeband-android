package be.heh.homeband.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import  android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v7.app.ActionBar;

import be.heh.homeband.R;
import be.heh.homeband.activities.SearchGroupeFrag;
import be.heh.homeband.activities.HomeFrag;


public class MainActivity extends AppCompatActivity implements  HomeFrag.OnFragmentInteractionListener,
                                                                SearchGroupeFrag.OnFragmentInteractionListener {

    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = getSupportActionBar();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // load the store fragment by default
        toolbar.setTitle("Home");
        loadFragment(new HomeFrag());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    toolbar.setTitle("Home");
                    fragment = new HomeFrag();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_place:
                    toolbar.setTitle("Recherche");
                    fragment = new SearchGroupeFrag();
                    loadFragment(fragment);

                    return true;
                case R.id.navigation_favorite:
                    toolbar.setTitle("Cart");


                    return true;
                case R.id.navigation_settings:
                    toolbar.setTitle("Profile");


                    return true;
            }

            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
