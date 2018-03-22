package be.heh.homeband.app;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import be.heh.homeband.R;
import be.heh.homeband.activities.LoadingDialog;
import be.heh.homeband.activities.MainActivity;
import be.heh.homeband.entities.Style;
import be.heh.homeband.entities.Utilisateur;
import be.heh.homeband.entities.Version;
import be.heh.homeband.entities.Ville;
import io.realm.Realm;
import io.realm.RealmQuery;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by christopher on 07/03/2018.
 */

public abstract class HomebandTools {

    public static Utilisateur getConnectedUser() {
        Realm realm = Realm.getDefaultInstance();
        // Build the query looking at all users:
        RealmQuery<Utilisateur> query = realm.where(Utilisateur.class);

        // Add query conditions:
        query.equalTo("est_connecte", true);

        // Execute the query:
        Utilisateur user = query.findFirst();
        return user;
    }

    public static void disconnectUser() {
        final Utilisateur user = getConnectedUser();
        if (user != null) {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    user.setEst_connecte(false);
                    realm.copyToRealmOrUpdate(user);
                }
            });
        }
    }

    public static void checkUpdateVille(final Context context){
        final DialogFragment loading = new LoadingDialog();
        FragmentManager frag = ((MainActivity) context).getFragmentManager();
        loading.show(frag,"LoadingDialog");

        try {
            Thread.sleep(5500);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HomebandRetrofit.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // Création d'une instance du service avec Retrofit
            HomebandApiInterface serviceApi = retrofit.create(HomebandApiInterface.class);



            // Requête vers l'API
            serviceApi.getVersions("VILLES").enqueue(new Callback<HomebandApiReponse>() {
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
                            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
                            final Version versionAPI = gson.fromJson(res.get("version").getAsJsonObject(), Version.class);

                            //Requête vers base de donnée interne
                            Realm realm = Realm.getDefaultInstance();
                            RealmQuery<Version> query = realm.where(Version.class);
                            query.equalTo("nom_table", "VILLES");
                            Version versionDB = query.findFirst();

                            if (versionDB == null)
                            {
                                toUpdate=true;
                            }
                            else{
                                if ( versionAPI.getDate_maj().after(versionDB.getDate_maj()) ){
                                    toUpdate=true;
                                }

                            }

                            loading.dismiss();

                            if (toUpdate == true){
                                AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.ThemeYesNo);
                                builder.setTitle(R.string.alert_update_title);
                                builder.setMessage(R.string.alert_update_message)
                                        .setPositiveButton(R.string.alert_yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                HomebandTools.updateVilles(context);
                                            }
                                        })
                                        .setNegativeButton(R.string.alert_no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // User cancelled the dialog
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();


                            }

                        } else {
                            loading.dismiss();
                            messageToast = "Échec de la connexion\r\n" + res.getMessage();

                            // Affichage d'un toast pour indiquer le résultat
                            Toast toast = Toast.makeText(context.getApplicationContext(), messageToast, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    } else {
                        loading.dismiss();
                        int statusCode = response.code();

                        String res = response.toString();
                        CharSequence message ="Erreur lors de l'appel à l'API (" + statusCode +")";
                        Toast toast = Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG);
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
            Toast.makeText(context,(CharSequence)"Exception",Toast.LENGTH_LONG).show();
            e.printStackTrace();

        }
    }

    public static void updateVilles(final Context context){

        final DialogFragment loading = new LoadingDialog();
        FragmentManager frag = ((MainActivity) context).getFragmentManager();
        loading.show(frag,"LoadingDialog");

        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HomebandRetrofit.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // Création d'une instance du service avec Retrofit
            HomebandApiInterface serviceApi = retrofit.create(HomebandApiInterface.class);



            // Requête vers l'API
            serviceApi.getVilles().enqueue(new Callback<HomebandApiReponse>() {
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
                            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
                            Type typeListe = new TypeToken<List<Ville>>(){}.getType();
                            final List<Ville> villes = gson.fromJson(res.get("villes").getAsJsonArray(), typeListe);

                            //Requête vers base de donnée interne
                            Realm realm = Realm.getDefaultInstance();
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {

                                    for (Iterator<Ville> i = villes.iterator(); i.hasNext();) {
                                        Ville item = i.next();
                                        realm.copyToRealmOrUpdate(item);
                                    }

                                }
                            });
                            RealmQuery<Version> query = realm.where(Version.class);
                            query.equalTo("nom_table", "VILLES");
                            Version versionDB = query.findFirst();
                            if (versionDB==null){
                                versionDB = new Version() ;
                                versionDB.setNom_table("VILLES");
                                versionDB.setNum_table(2);
                            }



                            versionDB.setDate_maj(new Date());


                            loading.dismiss();
                        } else {
                            loading.dismiss();
                            messageToast = "Échec de la connexion\r\n" + res.getMessage();

                            // Affichage d'un toast pour indiquer le résultat
                            Toast toast = Toast.makeText(context.getApplicationContext(), messageToast, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    } else {
                        loading.dismiss();
                        int statusCode = response.code();

                        String res = response.toString();
                        CharSequence message ="Erreur lors de l'appel à l'API (" + statusCode +")";
                        Toast toast = Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG);
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
            Toast.makeText(context,(CharSequence)"Exception",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


}
