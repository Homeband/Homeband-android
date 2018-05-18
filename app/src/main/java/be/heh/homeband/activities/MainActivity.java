package be.heh.homeband.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import  android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.support.v7.app.ActionBar;

import be.heh.homeband.R;
import be.heh.homeband.app.HomebandTools;
import be.heh.homeband.entities.Style;


public class MainActivity extends AppCompatActivity implements  HomeFrag.OnFragmentInteractionListener,
                                                                SearchFrag.OnFragmentInteractionListener,
                                                                SettingsFrag.OnFragmentInteractionListener,
                                                                FavouriteFrag.OnFragmentInteractionListener{
    @Override
    protected void onStart() {
        super.onStart();
        if (HomebandTools.readAutoConnect(this) == 1 ){
            HomebandTools.checkReferenceUpdate(this);
        }

    }

    private ActionBar toolbar;
    ArrayAdapter<Style> adapterStyle;
    Spinner spinStyle;
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
                    toolbar.setTitle("Acceuil");
                    fragment = new HomeFrag();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_place:
                    toolbar.setTitle("Recherche");
                    fragment = new SearchFrag();
                    loadFragment(fragment);

                    return true;
                case R.id.navigation_favorite:
                    toolbar.setTitle("Favoris");
                    fragment = new FavouriteFrag();
                    loadFragment(fragment);

                    return true;
                case R.id.navigation_settings:
                    toolbar.setTitle("Paramètres");
                    fragment = new SettingsFrag();
                    loadFragment(fragment);

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
