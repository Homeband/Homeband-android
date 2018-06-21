package be.heh.homeband.activities.Offline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import be.heh.homeband.Dao.UtilisateurDao;
import be.heh.homeband.DaoImpl.UtilisateurDaoImpl;
import be.heh.homeband.R;
import be.heh.homeband.activities.MainActivity;
import be.heh.homeband.app.HomebandApiInterface;
import be.heh.homeband.app.HomebandApiReponse;
import be.heh.homeband.app.HomebandConnectivity;
import be.heh.homeband.app.HomebandRetrofit;
import be.heh.homeband.app.HomebandTools;
import be.heh.homeband.entities.Utilisateur;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText etLogin;
    private EditText etPassword;

    private Button btnMotDePasseOublie;
    private Button btnCreerCompte;

    private boolean estConnecte = false;

    UtilisateurDao utilisateurDao;

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

        utilisateurDao = new UtilisateurDaoImpl();
        Utilisateur user = utilisateurDao.getConnectedUser();

        if (user != null){
            HomebandTools.writeAutoConnect(this,true);
            Intent intent = new Intent (getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }

        initialisation();

    }

    private void initialisation(){
        this.etLogin = findViewById(R.id.etLogin);
        this.etPassword = findViewById(R.id.etPassword);

        this.btnMotDePasseOublie=findViewById(R.id.bMotDePasseOublie);
        btnMotDePasseOublie.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(v.getContext(), PasswordForgetActivity.class);
                startActivity(i);
            }
        });

        btnCreerCompte = findViewById(R.id.btnCreerCompte);
        btnCreerCompte.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(v.getContext(), InscriptionActivity.class);
                startActivity(i);
            }
        });
    }


    public void onClickConnexion(View v){
        String login = this.etLogin.getText().toString();
        String pass = this.etPassword.getText().toString();
        if (HomebandConnectivity.isConnectedToInternet(this)==true){
            connect(login,pass);
            if(this.estConnecte == true){
                HomebandTools.writeAutoConnect(this,false);
                // Connexion réussie, Changement de fenêtre
                Toast.makeText(this, "Connexion réussie !", Toast.LENGTH_LONG).show();
               if (HomebandTools.readInit(this) == 0) {
                   //Je force le téléchargement des tables de références
                   HomebandTools.updateStyles(this);
                   HomebandTools.updateVilles(this);
                   HomebandTools.writeInit(this);
               }
               else{
                   HomebandTools.checkReferenceUpdate(this);
               }
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
                            utilisateurDao.write(user);
//                            Realm realm = Realm.getDefaultInstance();
//                            realm.executeTransaction(new Realm.Transaction() {
//                                @Override
//                                public void execute(Realm realm) {
//
//                                    realm.copyToRealmOrUpdate(user);
//                                }
//                            });


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
