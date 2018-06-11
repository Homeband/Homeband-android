package be.heh.homeband.activities.Evenements.Fiche;

import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import be.heh.homeband.Dao.AdresseDao;
import be.heh.homeband.Dao.EvenementDao;
import be.heh.homeband.Dao.GroupeDao;
import be.heh.homeband.Dao.UtilisateurDao;
import be.heh.homeband.Dao.VilleDao;
import be.heh.homeband.DaoImpl.AdresseDaoImpl;
import be.heh.homeband.DaoImpl.EvenementDaoImpl;
import be.heh.homeband.DaoImpl.GroupeDaoImpl;
import be.heh.homeband.DaoImpl.UtilisateurDaoImpl;
import be.heh.homeband.DaoImpl.VilleDaoImpl;
import be.heh.homeband.R;
import be.heh.homeband.activities.LoadingDialog;
import be.heh.homeband.entities.Adresse;
import be.heh.homeband.entities.Evenement;
import be.heh.homeband.entities.Groupe;
import be.heh.homeband.entities.Utilisateur;
import be.heh.homeband.entities.Ville;

public class EventDetailsActivity extends AppCompatActivity implements FragmentDescriptionEvent.OnFragmentInteractionListener, FragmentInfos.OnFragmentInteractionListener {

    Evenement event;
    Adresse adresse;
    Groupe groupe;

    Boolean isFavorite;

    VilleDao villeDao;
    GroupeDao groupeDao;
    AdresseDao adresseDao;
    EvenementDao evenementDao;
    UtilisateurDao utilisateurDao;

    ImageButton ibFavourite;

    ImageView ivEvent;

    ViewPager viewPager;

    Button btnCalendar;
    Button btnEvents;

    SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_events);
        event = (Evenement) getIntent().getSerializableExtra("event");
        groupe = (Groupe) getIntent().getSerializableExtra("group");
        adresse = (Adresse) getIntent().getSerializableExtra("address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        setTitle(event.getNom());

        // Locate the viewpager in activity_main.xml
        viewPager = (ViewPager) findViewById(R.id.pager);

        // Set the ViewPagerAdapter into ViewPager
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new FragmentDescriptionEvent(), "Description");
        adapter.addFrag(new FragmentInfos(), "Infos");

        viewPager.setAdapter(adapter);

        TabLayout mTabLayout = (TabLayout) findViewById(R.id.pager_header);
        mTabLayout.setupWithViewPager(viewPager);
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

    @Override
    public boolean onSupportNavigateUp(){

        this.finish();
        return true;
    }

    public void init(){

        ivEvent = (ImageView) findViewById(R.id.ivEvent);
        String url = "http://dev.zen-project.be/homeband/images/";
        if (event.getIllustration() == ""){
            url += "no_image.png";
        }
        else{
            url += "event/" + event.getIllustration();
        }
        Picasso.with(this).load(url).centerCrop().fit().into(ivEvent);

        btnCalendar = (Button) findViewById(R.id.btnCalendar);
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addCalendar();
            }
        });

        btnEvents = (Button) findViewById(R.id.btnEvents);
        btnEvents.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        adresseDao = new AdresseDaoImpl();
        villeDao = new VilleDaoImpl();
        groupeDao = new GroupeDaoImpl();
        evenementDao = new EvenementDaoImpl();
        utilisateurDao = new UtilisateurDaoImpl();


        ibFavourite = (ImageButton)  findViewById(R.id.ibFavouriteEvent);
        ibFavourite.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(!isFavorite) {
                    add_favourite();
                    ibFavourite.setBackgroundResource(R.drawable.round_favourite);

                } else {
                    remove_favourite();
                    ibFavourite.setBackgroundResource(R.drawable.round_disabled);
                }
            }
        });

        Utilisateur user = utilisateurDao.getConnectedUser();
        if(utilisateurDao.getEvent(user.getId_utilisateurs(), event.getId_evenements()) == null){
            isFavorite = false;
        } else {
            isFavorite = true;
            ibFavourite.setBackgroundResource(R.drawable.round_favourite);
        }

    }

    public void addCalendar(){
        Ville ville = villeDao.get(adresse.getId_villes());
        Calendar cal = Calendar.getInstance();
        cal.setTime(event.getDate_heure());
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", cal.getTimeInMillis());
        intent.putExtra("eventLocation",adresse.getNumero() +" " + adresse.getRue() + ", " + ville.getCode_postal()+ " " + ville.getNom() );
        intent.putExtra("description", groupe.getNom());
        intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
        intent.putExtra("title", event.getNom());
        startActivity(intent);
    }

    public void add_favourite(){
        final DialogFragment loading = new LoadingDialog();
        android.app.FragmentManager frag = ((AppCompatActivity) this).getFragmentManager();
        loading.show(frag,"LoadingDialog");

        Utilisateur user = utilisateurDao.getConnectedUser();
        utilisateurDao.addEvent(user.getId_utilisateurs(), event);

        //1. Ajout de l'adresse
        adresseDao.write(adresse);

        //2. Si le groupe n'existe pas sur le téléphone on l'ajoute
        if(groupeDao.get(groupe.getId_groupes()) == null){
            groupeDao.write(groupe);
        }

        isFavorite = true;
        ibFavourite.setBackgroundResource(R.drawable.round_favourite);

        loading.dismiss();
    }

    public void remove_favourite(){
        final DialogFragment loading = new LoadingDialog();
        android.app.FragmentManager frag = ((AppCompatActivity) this).getFragmentManager();
        loading.show(frag,"LoadingDialog");

        Utilisateur user = utilisateurDao.getConnectedUser();
        utilisateurDao.deleteEvent(user.getId_utilisateurs(), event.getId_evenements());

        if(!evenementDao.isUsed(event.getId_evenements())){
            adresseDao.delete(adresse.getId_adresses());
            if(groupeDao.isUsed(event.getId_groupes())){
                groupeDao.delete(event.getId_groupes());
            }
        }

        isFavorite = false;
        ibFavourite.setBackgroundResource(R.drawable.round_disabled);

        loading.dismiss();
    }
}
