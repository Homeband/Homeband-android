package be.heh.homeband.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.Toast;

import be.heh.homeband.R;
import be.heh.homeband.app.HomebandApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Converter.Factory.*;
import retrofit2.converter.gson.GsonConverterFactory;

import com.google.gson.*;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide Action Bar
        getSupportActionBar().hide();

        // Hide Navigation Bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);

        // Set ContentView
        setContentView(R.layout.activity_login);


    }

    public void onClickConnexion(View v){

        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2/homeband-api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // Création d'une instance du service avec Retrofit
            HomebandApi service = retrofit.create(HomebandApi.class);

            // Appel de la méthode connexion de HomebandApi avec un callback
            service.connexion("Nicola", "Test123*", 1).enqueue(new Callback<JsonObject>() {

                // En cas de réussite pour joindre l'API
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    // En fonction du code HTTP de Retour (2** = Successful)
                    if (response.isSuccessful()) {
                        // Récupération de la réponse
                        JsonObject res = response.body();

                        // Récupération du status de la requête et du message
                        boolean status = res.get("status").getAsBoolean();
                        String message = res.get("message").getAsString();

                        CharSequence messageToast;
                        if (status == true) {
                            messageToast = message;
                        } else {
                            messageToast = "Échec de la connexion\r\n" + message;
                        }

                        // Affichage d'un toast pour indiquer le résultat
                        Toast toast = Toast.makeText(getApplicationContext(), messageToast, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                    } else {
                        int statusCode = response.code();

                         String res = response.toString();
                            CharSequence message = res;//"Erreur lors de l'appel à l'API (" + statusCode +")";
                            Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    // showErrorMessage();
                    Log.d("MainActivity", "Erreur : " + t.getMessage());
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
