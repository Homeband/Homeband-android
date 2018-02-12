package be.heh.homeband.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.List;

import be.heh.homeband.R;
import be.heh.homeband.app.HomebandApiInterface;
import be.heh.homeband.app.HomebandApiReponse;
import be.heh.homeband.app.HomebandRetrofit;
import be.heh.homeband.entities.Style;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by christopher on 03/02/2018.
 */

public class SearchEventsActivity extends AppCompatActivity {
    ArrayAdapter<Style> adapterStyle;
    Spinner spinStyle;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:


                    Intent intent = new Intent (getApplicationContext(),HomeActivity.class);
                    startActivity(intent);
                    break;
                case R.id.navigation_place:


                    Intent intent2 = new Intent (getApplicationContext(),SearchGroupeActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.navigation_favorite:

                    return true;
                case R.id.navigation_settings:

                    return true;
            }
            return false;
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchevents);

        initialisation();
        initStyles();

        Button next = (Button) findViewById(R.id.btnGroupe);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), SearchGroupeActivity.class);
                startActivityForResult(myIntent, 0);
            }

        });
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Switch s = (Switch) findViewById(R.id.switch1);

        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    EditText Du = (EditText) findViewById(R.id.etDu);
                    EditText Au = (EditText) findViewById(R.id.etAu);
                    TextView Dut = (TextView) findViewById(R.id.textView);
                    TextView Aut = (TextView) findViewById(R.id.textView5);
                    Du.setVisibility(View.VISIBLE);
                    Au.setVisibility(View.VISIBLE);
                    Dut.setVisibility(View.VISIBLE);
                    Aut.setVisibility(View.VISIBLE);
                }
                else   {
                    EditText Du = (EditText) findViewById(R.id.etDu);
                    EditText Au = (EditText) findViewById(R.id.etAu);
                    TextView Dut = (TextView) findViewById(R.id.textView);
                    TextView Aut = (TextView) findViewById(R.id.textView5);
                    Du.setVisibility(View.INVISIBLE);
                    Au.setVisibility(View.INVISIBLE);
                    Dut.setVisibility(View.INVISIBLE);
                    Aut.setVisibility(View.INVISIBLE);
                }


            }
        });
    }
    public void initialisation(){
        this.spinStyle = (Spinner)findViewById(R.id.spinner1);
    }

    public void initStyles(){
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HomebandRetrofit.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // Création d'une instance du service avec Retrofit
            HomebandApiInterface serviceApi = retrofit.create(HomebandApiInterface.class);

            // Requête vers l'API
            serviceApi.getStyles().enqueue(new Callback<HomebandApiReponse>() {
                @Override
                public void onResponse(Call<HomebandApiReponse> call, Response<HomebandApiReponse> response) {

                    // En fonction du code HTTP de Retour (2** = Successful)
                    if (response.isSuccessful()) {

                        // Récupération de la réponse de l'API
                        HomebandApiReponse res = response.body();
                        res.mapResultat();

                        CharSequence messageToast;
                        if (res.isOperationReussie() == true) {
                            // Element de retour sera de type List<style>
                            Type typeListe = new TypeToken<List<Style>>(){}.getType();

                            // Désérialisation du tableau JSON (JsonArray) en liste d'objets Style

                            //gson.fromJson prend 2 paramètres
                            //Premier paramètre c'est l'élément Json qu'il va falloir récupérer
                            //Deuxième paramètre c'est le type d'élément à récupérer
                            Gson gson = new Gson();
                            List<Style> listeStyles = gson.fromJson(res.get("styles").getAsJsonArray(), typeListe);

                            // Initialisation de l'adapter
                            adapterStyle = new ArrayAdapter<Style>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item);

                            // Ajout de la liste des styles à l'adapter
                            adapterStyle.addAll(listeStyles);

                            // Application de l'adapter au spinner
                            spinStyle.setAdapter(adapterStyle);
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
