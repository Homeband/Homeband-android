package be.heh.homeband.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import be.heh.homeband.Dao.UtilisateurDao;
import be.heh.homeband.DaoImpl.UtilisateurDaoImpl;
import be.heh.homeband.R;
import be.heh.homeband.activities.avisRecyclerView.AvisResultActivity;
import be.heh.homeband.app.HomebandApiInterface;
import be.heh.homeband.app.HomebandApiReponse;
import be.heh.homeband.app.HomebandRetrofit;
import be.heh.homeband.entities.Avis;
import be.heh.homeband.entities.Groupe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddAvis extends AppCompatActivity {

    private Button btnAddAvis;
    private EditText etAddAvis;

    Groupe groupe;

    UtilisateurDao utilisateurDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_avis);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    @Override
    public boolean onSupportNavigateUp(){

        this.finish();
        return true;
    }

    public void init(){

        groupe = (Groupe) getIntent().getSerializableExtra("group");

        btnAddAvis = (Button) findViewById(R.id.btnAddAvis);
        etAddAvis = (EditText) findViewById(R.id.etAddAvis);

        utilisateurDao = new UtilisateurDaoImpl();

        btnAddAvis.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Avis avis = new Avis();
                avis.setCommentaire(etAddAvis.getText().toString());
                avis.setDate_ajout(new Date());
                avis.setId_utilisateurs(utilisateurDao.getConnectedUser().getId_utilisateurs());
                avis.setId_groupes(groupe.getId_groupes());
                avis.setEst_accepte(false);
                avis.setEst_verifie(false);
                avis.setEst_actif(1);
               addAvis(groupe.getId_groupes(),avis);
            }
        });
    }

    public void addAvis(int id,Avis avis){
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HomebandRetrofit.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // Création d'une instance du service avec Retrofit
            HomebandApiInterface serviceApi = retrofit.create(HomebandApiInterface.class);

            HashMap<String,Object> params = new HashMap<String,Object>();
            params.put("comment", avis);
            // Requête vers l'API
            serviceApi.postAvis(id,params).enqueue(new Callback<HomebandApiReponse>() {
                @Override
                public void onResponse(Call<HomebandApiReponse> call, Response<HomebandApiReponse> response) {


                    // En fonction du code HTTP de Retour (2** = Successful)
                    if (response.isSuccessful()) {

                        // Récupération de la réponse de l'API
                        HomebandApiReponse res = response.body();
                        res.mapResultat();

                        CharSequence messageToast;
                        if (res.isOperationReussie() == true) {
                            messageToast = "Votre avis a bien été envoyé et sera visible après avoir été validé\r\n" + res.getMessage();
                            Toast toast = Toast.makeText(getApplicationContext(), messageToast, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

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

