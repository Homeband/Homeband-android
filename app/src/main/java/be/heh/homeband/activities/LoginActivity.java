package be.heh.homeband.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import be.heh.homeband.R;
import be.heh.homeband.app.HomebandApiInterface;
import be.heh.homeband.app.HomebandApiReponse;
import be.heh.homeband.app.HomebandConnectivity;
import be.heh.homeband.app.HomebandRetrofit;
import be.heh.homeband.app.HomebandTools;
import be.heh.homeband.entities.Utilisateur;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText etLogin;
    private EditText etPassword;

    private boolean estConnecte = false;

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
       Utilisateur user = HomebandTools.getConnectedUser();

        if (user!=null){
            Intent intent = new Intent (getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        initialisation();

    }

    private void initialisation(){
        this.etLogin = findViewById(R.id.etLogin);
        this.etPassword = findViewById(R.id.etPassword);

        this.etLogin.setText("carole");
        this.etPassword.setText("testtest");
    }

    public void onClickConnexion(View v){
        String login = this.etLogin.getText().toString();
        String pass = this.etPassword.getText().toString();
        if (HomebandConnectivity.isConnectedToInternet(this)==true){
            connect(login,pass);
            if(this.estConnecte == true){
                // Connexion réussie, Changement de fenêtre
                Toast.makeText(this, "Connexion réussie !", Toast.LENGTH_LONG).show();
                Intent intent = new Intent (getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        }
        else{
            Toast.makeText(this, "Vous devez être connecté à internet", Toast.LENGTH_LONG).show();
        }

    }

    private void connect(String login, String password){

        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HomebandRetrofit.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            // Création d'une instance du service avec Retrofit
            HomebandApiInterface serviceApi = retrofit.create(HomebandApiInterface.class);

            // Requête vers l'API
            serviceApi.connexion(login, password,1).enqueue(new Callback<HomebandApiReponse>() {
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
                            final Utilisateur user = gson.fromJson(res.get("user").getAsJsonObject(), Utilisateur.class);
                            user.setEst_connecte(true);
                            estConnecte = true;
                            Realm realm = Realm.getDefaultInstance();
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {

                                    realm.copyToRealmOrUpdate(user);
                                }
                            });

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
