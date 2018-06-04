package be.heh.homeband.activities.BandOnClick;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import be.heh.homeband.Dao.AdresseDao;
import be.heh.homeband.Dao.GroupeDao;
import be.heh.homeband.Dao.VilleDao;
import be.heh.homeband.DaoImpl.AdresseDaoImpl;
import be.heh.homeband.DaoImpl.GroupeDaoImpl;
import be.heh.homeband.DaoImpl.VilleDaoImpl;
import be.heh.homeband.R;
import be.heh.homeband.activities.LoadingDialog;
import be.heh.homeband.app.HomebandApiInterface;
import be.heh.homeband.app.HomebandApiReponse;
import be.heh.homeband.app.HomebandRetrofit;
import be.heh.homeband.entities.Adresse;
import be.heh.homeband.entities.Album;
import be.heh.homeband.entities.Evenement;
import be.heh.homeband.entities.Groupe;
import be.heh.homeband.entities.Style;
import be.heh.homeband.entities.Titre;
import be.heh.homeband.entities.UtilisateursGroupes;
import be.heh.homeband.entities.Ville;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventDetailsActivity extends AppCompatActivity implements FragmentDescriptionEvent.OnFragmentInteractionListener, FragmentInfos.OnFragmentInteractionListener {

    Evenement event;
    Adresse adresse;
    Groupe groupe;



    VilleDao villeDao;
    GroupeDao groupeDao;

    ImageButton ibFavourite;

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

        ibFavourite = (ImageButton)  findViewById(R.id.ibFavouriteEvent);
        ibFavourite.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

            }
        });


        villeDao = new VilleDaoImpl();
        groupeDao = new GroupeDaoImpl();
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

//    public void add_favourite(){
//        final DialogFragment loading = new LoadingDialog();
//        android.app.FragmentManager frag = ((AppCompatActivity) this).getFragmentManager();
//        loading.show(frag,"LoadingDialog");
//        try {
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(HomebandRetrofit.API_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//
//            // Création d'une instance du service avec Retrofit
//            HomebandApiInterface serviceApi = retrofit.create(HomebandApiInterface.class);
//
//            // Requête vers l'API
//            serviceApi.addUtilisateurGroupe().enqueue(new Callback<HomebandApiReponse>() {
//                @Override
//                public void onResponse(Call<HomebandApiReponse> call, Response<HomebandApiReponse> response) {
//
//
//                    // En fonction du code HTTP de Retour (2** = Successful)
//                    if (response.isSuccessful()) {
//
//                        // Récupération de la réponse de l'API
//                        HomebandApiReponse res = response.body();
//                        res.mapResultat();
//
//                        CharSequence messageToast;
//                        if (res.isOperationReussie() == true) {
//
//                            //1. Récupération info API
//                            Type typeListe = new TypeToken<List<Album>>(){}.getType();
//                            Type typeListeTitre = new TypeToken<List<Titre>>(){}.getType();
//                            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
//                            List<Album> listeAlbums = gson.fromJson(res.get("albums").getAsJsonArray(), typeListe);
//                            List<Titre> listeTitres = gson.fromJson(res.get("titles").getAsJsonArray(), typeListeTitre);
//
//                            //2. Création liaison locale
//
//
//                            //3. Ajout groupe local
//
//
//                            //4. Ajout Membres
//
//
//                            //5. Ajout Album
//
//
//                            //6. Ajout Titres
//
//
//                            //7. Modofication bouton favoris
//
//
//
//                            isFavorite = true;
//                            loading.dismiss();
//
//                        } else {
//                            loading.dismiss();
//                            messageToast = "Échec de la connexion\r\n" + res.getMessage();
//
//                            // Affichage d'un toast pour indiquer le résultat
//                            Toast toast = Toast.makeText(getApplicationContext(), messageToast, Toast.LENGTH_LONG);
//                            toast.setGravity(Gravity.CENTER, 0, 0);
//                            toast.show();
//                        }
//                    } else {
//                        loading.dismiss();
//                        int statusCode = response.code();
//
//                        String res = response.toString();
//                        CharSequence message ="Erreur lors de l'appel à l'API (" + statusCode +")";
//                        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
//                        toast.setGravity(Gravity.CENTER, 0, 0);
//                        toast.show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<HomebandApiReponse> call, Throwable t) {
//                    loading.dismiss();
//
//                    Log.d("LoginActivity", t.getMessage());
//                }
//            });
//        } catch (Exception e){
//            loading.dismiss();
//            Toast.makeText(this,(CharSequence)"Exception",Toast.LENGTH_LONG).show();
//            e.printStackTrace();
//        }
//    }

    public void remove_favourite(){

    }
}
