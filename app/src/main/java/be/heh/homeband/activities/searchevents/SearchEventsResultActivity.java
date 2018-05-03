package be.heh.homeband.activities.searchevents;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import be.heh.homeband.R;
import be.heh.homeband.activities.BandOnClick.EventDetailsActivity;
import be.heh.homeband.activities.BandOnClick.GroupeDetailsActivity;
import be.heh.homeband.activities.BandOnClick.RecyclerTouchListener;
import be.heh.homeband.app.HomebandApiInterface;
import be.heh.homeband.app.HomebandApiReponse;
import be.heh.homeband.app.HomebandRetrofit;
import be.heh.homeband.entities.Evenement;
import be.heh.homeband.entities.Groupe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by christopher on 27-02-18.
 */

public class SearchEventsResultActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private List<Evenement> events = new ArrayList<Evenement>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listevents);

        events = (ArrayList<Evenement>) getIntent().getSerializableExtra("events");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewEvents);

        //définit l'agencement des cellules, ici de façon verticale, comme une ListView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //pour adapter en grille comme une RecyclerView, avec 2 cellules par ligne
        //recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        //puis créer un SearchGroupAdapter, lui fournir notre liste de villes.
        //cet adapter servira à remplir notre recyclerview
        recyclerView.setAdapter(new SearchEventsAdapter(events));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Evenement event = events.get(position);
                getEvent(event.getId_evenements());
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){

        this.finish();
        return true;
    }

    private void getEvent(int id){

        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HomebandRetrofit.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            // Création d'une instance du service avec Retrofit
            HomebandApiInterface serviceApi = retrofit.create(HomebandApiInterface.class);

            // Requête vers l'API
            serviceApi.getEvent(id).enqueue(new Callback<HomebandApiReponse>() {
                @Override
                public void onResponse(Call<HomebandApiReponse> call, Response<HomebandApiReponse> response) {

                    // En fonction du code HTTP de Retour (2** = Successful)
                    if (response.isSuccessful()) {
                        // Récupération de la réponse
                        HomebandApiReponse res = response.body();
                        res.mapResultat();

                        CharSequence messageToast;
                        if (res.isOperationReussie() == true) {
                            Gson gson = new Gson();
                            //C'est le groupe que l'on va récupérer en objet json et transforme en objet groupe. Le dernier parametre c'est le type d'objet retourner
                            Evenement event = gson.fromJson(res.get("event"),Evenement.class);
                            Intent intent = new Intent (getApplicationContext(),EventDetailsActivity.class);
                            intent.putExtra("event",event);
                            startActivity(intent);

                        } else {
                            messageToast = "Impossible de récupérer les détails de l'évènement\r\n" + res.getMessage();

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
