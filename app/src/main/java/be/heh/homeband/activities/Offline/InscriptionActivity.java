package be.heh.homeband.activities.Offline;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import be.heh.homeband.Dao.UtilisateurDao;
import be.heh.homeband.DaoImpl.UtilisateurDaoImpl;
import be.heh.homeband.R;
import be.heh.homeband.activities.LoadingDialog;
import be.heh.homeband.app.HomebandApiInterface;
import be.heh.homeband.app.HomebandApiReponse;
import be.heh.homeband.app.HomebandRetrofit;
import be.heh.homeband.entities.Utilisateur;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InscriptionActivity extends AppCompatActivity {

    EditText etLogin;
    EditText etEmail;
    EditText etMDP;
    EditText etMDPC;


    Button btnSendInscription;
    Button btnSendInscriptionCancel;

    UtilisateurDao utilisateurDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide Action Bar
        getSupportActionBar().hide();

        // Hide Navigation Bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);

        // Set ContentView
        setContentView(R.layout.activity_inscription);

        //Initialisation
        init();


    }

    public void init(){
        etLogin = (EditText) findViewById(R.id.etUser);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etMDP = (EditText) findViewById(R.id.etMDP);
        etMDPC = (EditText) findViewById(R.id.etMDPC);

        btnSendInscription = (Button) findViewById(R.id.btnSendInscription);
        btnSendInscription.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                inscription();
            }
        });

        btnSendInscriptionCancel = (Button) findViewById(R.id.btnSendInscriptionCancel);
        btnSendInscriptionCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        utilisateurDao = new UtilisateurDaoImpl();
    }



    public void inscription(){

        if (etLogin.getText().toString().equals("") || etEmail.getText().toString().equals("") || etMDP.getText().toString().equals("") || etMDPC.getText().toString().equals("")){
            Toast toast = Toast.makeText(getApplicationContext(), "Vous devez remplir tous les champs pour vous inscrire", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else{
            if(etMDP.getText().toString().equals(etMDPC.getText().toString()) ){
                if(isEmailValid(etEmail.getText().toString())){
                    Utilisateur user = new Utilisateur();
                    user.setEmail(etEmail.getText().toString());
                    user.setLogin(etLogin.getText().toString());
                    user.setMot_de_passe(etMDP.getText().toString());
                    inscriptionAPI(user);
                    this.finish();

                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "L'adresse mail n'est pas valide", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
            else{
                Toast toast = Toast.makeText(getApplicationContext(), "Les deux champs de mot de passe ne sont pas identiques", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }

        }
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void inscriptionAPI(Utilisateur user){
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("user",user);
        final DialogFragment loading = new LoadingDialog();
        android.app.FragmentManager frag = ((AppCompatActivity) this).getFragmentManager();
        loading.show(frag,"LoadingDialog");
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HomebandRetrofit.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // Création d'une instance du service avec Retrofit
            HomebandApiInterface serviceApi = retrofit.create(HomebandApiInterface.class);

            // Requête vers l'API
            serviceApi.postUser(params).enqueue(new Callback<HomebandApiReponse>() {
                @Override
                public void onResponse(Call<HomebandApiReponse> call, Response<HomebandApiReponse> response) {


                    // En fonction du code HTTP de Retour (2** = Successful)
                    if (response.isSuccessful()) {

                        // Récupération de la réponse de l'API
                        HomebandApiReponse res = response.body();
                        res.mapResultat();

                        CharSequence messageToast;
                        if (res.isOperationReussie() == true) {

                            Toast toast = Toast.makeText(getApplicationContext(), "InscriptionActivity réussie", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            loading.dismiss();

                        } else {
                            loading.dismiss();
                            messageToast = "Échec de la connexion\r\n" + res.getMessage();

                            // Affichage d'un toast pour indiquer le résultat
                            Toast toast = Toast.makeText(getApplicationContext(), messageToast, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    } else {
                        loading.dismiss();
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
                    loading.dismiss();

                    Log.d("LoginActivity", t.getMessage());
                }
            });
        } catch (Exception e){
            loading.dismiss();
            Toast.makeText(this,(CharSequence)"Exception",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
