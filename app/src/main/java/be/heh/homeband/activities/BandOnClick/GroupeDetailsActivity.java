package be.heh.homeband.activities.BandOnClick;


import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.TabLayout;
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

import org.w3c.dom.Text;

import be.heh.homeband.Dao.AlbumDao;
import be.heh.homeband.Dao.GroupeDao;
import be.heh.homeband.Dao.MembreDao;
import be.heh.homeband.Dao.StyleDao;
import be.heh.homeband.Dao.UtilisateurDao;
import be.heh.homeband.Dao.UtilisateursGroupesDao;
import be.heh.homeband.Dao.VersionDao;
import be.heh.homeband.Dao.VilleDao;
import be.heh.homeband.DaoImpl.AlbumDaoImpl;
import be.heh.homeband.DaoImpl.GroupeDaoImpl;
import be.heh.homeband.DaoImpl.MembreDaoImpl;
import be.heh.homeband.DaoImpl.StyleDaoImpl;
import be.heh.homeband.DaoImpl.UtilisateurDaoImpl;
import be.heh.homeband.DaoImpl.UtilisateursGroupesDaoImpl;
import be.heh.homeband.DaoImpl.VersionDaoImpl;
import be.heh.homeband.DaoImpl.VilleDaoImpl;
import be.heh.homeband.R;
import be.heh.homeband.activities.LoadingDialog;
import be.heh.homeband.app.HomebandApiInterface;
import be.heh.homeband.app.HomebandApiReponse;
import be.heh.homeband.app.HomebandRetrofit;
import be.heh.homeband.entities.Album;
import be.heh.homeband.entities.Evenement;
import be.heh.homeband.entities.Groupe;
import be.heh.homeband.entities.Membre;
import be.heh.homeband.entities.Style;
import be.heh.homeband.entities.Utilisateur;
import be.heh.homeband.entities.UtilisateursGroupes;
import be.heh.homeband.entities.Version;
import be.heh.homeband.entities.Ville;
import io.realm.Realm;
import io.realm.RealmQuery;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class GroupeDetailsActivity extends AppCompatActivity implements FragmentBio.OnFragmentInteractionListener,FragmentContacts.OnFragmentInteractionListener, FragmentMembres.OnFragmentInteractionListener {

    Button btnMusiques;
    Button btnEvents;

    TextView tvBandName;
    TextView tvBandCity;
    TextView tvBandStyle;

    ImageButton ibFavourite;

    Groupe groupe;

    ViewPager viewPager;

    VilleDao villeDao;
    StyleDao styleDao;
    UtilisateurDao utilisateurDao;
    UtilisateursGroupesDao utilisateursGroupesDao;
    GroupeDao groupeDao;
    MembreDao membreDao;
    AlbumDao albumDao;

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

        ibFavourite = (ImageButton)  findViewById(R.id.ibFavourite);
        ibFavourite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        tvBandName = (TextView) findViewById(R.id.tvBandName);
        tvBandCity = (TextView) findViewById(R.id.tvBandCity);
        tvBandStyle = (TextView) findViewById(R.id.tvBandStyle);

        villeDao = new VilleDaoImpl();
        styleDao = new StyleDaoImpl();
        utilisateurDao = new UtilisateurDaoImpl();
        utilisateursGroupesDao = new UtilisateursGroupesDaoImpl();
        groupeDao = new GroupeDaoImpl();
        membreDao = new MembreDaoImpl();
        albumDao = new AlbumDaoImpl();

    }

    public void bindData(Groupe groupe){

       Ville ville = villeDao.get(groupe.getId_villes());
       Style style = styleDao.get(groupe.getId_styles());


        tvBandName.setText(groupe.getNom());
        tvBandCity.setText(ville.getNom());
        tvBandStyle.setText(style.getNom());

    }

    public void addFavourite(){
        UtilisateursGroupes liaison = new UtilisateursGroupes();

        Utilisateur user = utilisateurDao.getConnectedUser();

        liaison.setId_groupes(groupe.getId_groupes());
        liaison.setId_utilisateurs(user.getId_utilisateurs());

        utilisateursGroupesDao.write(liaison);

        //Copier dans l'api les liaisons

        //Ajouter le groupe sur le téléphone
        groupeDao.write(groupe);

        //Ajouter les albums sur le téléphone et les membres
        getMembre(groupe.getId_groupes());
        getAlbums(groupe.getId_groupes());

        //Variable qui signale si le groupe est déjà en favoris

        //Arriere plan du bouton à mettre en jaune
        ibFavourite.setBackgroundColor(getResources().getColor(R.color.rbFavourite));
    }

    public void removeFavourite(){


    }

    public void getMembre(int id){


        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HomebandRetrofit.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // Création d'une instance du service avec Retrofit
            HomebandApiInterface serviceApi = retrofit.create(HomebandApiInterface.class);

            // Requête vers l'API
            serviceApi.getMembres(id).enqueue(new Callback<HomebandApiReponse>() {
                @Override
                public void onResponse(Call<HomebandApiReponse> call, Response<HomebandApiReponse> response) {
                    boolean toUpdate=false;

                    // En fonction du code HTTP de Retour (2** = Successful)
                    if (response.isSuccessful()) {

                        // Récupération de la réponse de l'API
                        HomebandApiReponse res = response.body();
                        res.mapResultat();

                        CharSequence messageToast;
                        if (res.isOperationReussie() == true) {
                            Type typeListe = new TypeToken<List<Membre>>(){}.getType();
                            Gson gson = new Gson();
                            List<Membre> listeMembres = gson.fromJson(res.get("members").getAsJsonArray(), typeListe);
                            for(int i=0;i<listeMembres.size();i++){
                                membreDao.write(listeMembres.get(i));
                            }

                        } else {

                            messageToast = "Échec de la connexion\r\n" + res.getMessage();

                            // Affichage d'un toast pour indiquer le résultat
                            Toast toast = Toast.makeText(getApplicationContext(), messageToast, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    } else {

                        int statusCode = response.code();

                        String res = response.toString();
                        CharSequence message ="Erreur lors de l'appel à l'API (" + statusCode +")";
                        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }

                @Override
                public void onFailure(Call<HomebandApiReponse> call, Throwable t) {

                    Log.d("LoginActivity", t.getMessage());
                }
            });
        } catch (Exception e){

            Toast.makeText(this,(CharSequence)"Exception",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void getAlbums(int id){


        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HomebandRetrofit.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // Création d'une instance du service avec Retrofit
            HomebandApiInterface serviceApi = retrofit.create(HomebandApiInterface.class);

            // Requête vers l'API
            serviceApi.getAlbums(id).enqueue(new Callback<HomebandApiReponse>() {
                @Override
                public void onResponse(Call<HomebandApiReponse> call, Response<HomebandApiReponse> response) {
                    boolean toUpdate=false;

                    // En fonction du code HTTP de Retour (2** = Successful)
                    if (response.isSuccessful()) {

                        // Récupération de la réponse de l'API
                        HomebandApiReponse res = response.body();
                        res.mapResultat();

                        CharSequence messageToast;
                        if (res.isOperationReussie() == true) {
                            Type typeListe = new TypeToken<List<Album>>(){}.getType();
                            Gson gson = new Gson();
                            List<Album> listeAlbums = gson.fromJson(res.get("albums").getAsJsonArray(), typeListe);
                            for(int i=0;i<listeAlbums.size();i++){
                                albumDao.write(listeAlbums.get(i));
                            }

                        } else {

                            messageToast = "Échec de la connexion\r\n" + res.getMessage();

                            // Affichage d'un toast pour indiquer le résultat
                            Toast toast = Toast.makeText(getApplicationContext(), messageToast, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    } else {

                        int statusCode = response.code();

                        String res = response.toString();
                        CharSequence message ="Erreur lors de l'appel à l'API (" + statusCode +")";
                        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }

                @Override
                public void onFailure(Call<HomebandApiReponse> call, Throwable t) {

                    Log.d("LoginActivity", t.getMessage());
                }
            });
        } catch (Exception e){

            Toast.makeText(this,(CharSequence)"Exception",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

}
