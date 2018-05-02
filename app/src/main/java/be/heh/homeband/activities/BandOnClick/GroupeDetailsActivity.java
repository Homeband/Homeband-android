package be.heh.homeband.activities.BandOnClick;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import be.heh.homeband.Dao.StyleDao;
import be.heh.homeband.Dao.VersionDao;
import be.heh.homeband.Dao.VilleDao;
import be.heh.homeband.DaoImpl.StyleDaoImpl;
import be.heh.homeband.DaoImpl.VersionDaoImpl;
import be.heh.homeband.DaoImpl.VilleDaoImpl;
import be.heh.homeband.R;
import be.heh.homeband.entities.Groupe;
import be.heh.homeband.entities.Style;
import be.heh.homeband.entities.Ville;
import io.realm.Realm;

import java.util.ArrayList;
import java.util.List;

public class GroupeDetailsActivity extends AppCompatActivity implements FragmentBio.OnFragmentInteractionListener,FragmentContacts.OnFragmentInteractionListener, FragmentMembres.OnFragmentInteractionListener {

    Button btnMusiques;
    Button btnEvents;

    TextView tvBandName;
    TextView tvBandCity;
    TextView tvBandStyle;

    Groupe groupe;

    ViewPager viewPager;

    VilleDao villeDao;
    StyleDao styleDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_onclick);


        //C'est l'objet groupe reçu depuis l'API
        groupe = (Groupe) getIntent().getSerializableExtra("groupe");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        bindData(groupe);
        // Locate the viewpager in activity_main.xml
        viewPager = (ViewPager) findViewById(R.id.pager);

        // Set the ViewPagerAdapter into ViewPager
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new FragmentBio(), "Biographie");
        adapter.addFrag(new FragmentContacts(), "Contacts");
        adapter.addFrag(new FragmentMembres(), "Membres");

        viewPager.setAdapter(adapter);

        TabLayout mTabLayout = (TabLayout) findViewById(R.id.pager_header);
        mTabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onSupportNavigateUp(){

        this.finish();
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void init(){
        btnMusiques = (Button) findViewById(R.id.btnMusiques);
        btnMusiques.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(),AlbumDetailsActivity.class);
                startActivity(intent);
            }
        });

        btnEvents = (Button) findViewById(R.id.btnEvents);
        btnEvents.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(),EventDetailsActivity.class);
                startActivity(intent);
            }
        });

        tvBandName = (TextView) findViewById(R.id.tvBandName);
        tvBandCity = (TextView) findViewById(R.id.tvBandCity);
        tvBandStyle = (TextView) findViewById(R.id.tvBandStyle);

        villeDao = new VilleDaoImpl();
        styleDao = new StyleDaoImpl();
    }

    public void bindData(Groupe groupe){

       Ville ville = villeDao.get(groupe.getId_villes());
       Style style = styleDao.get(groupe.getId_styles());


        tvBandName.setText(groupe.getNom());
        tvBandCity.setText(ville.getNom());
        tvBandStyle.setText(style.getNom());

    }

}
