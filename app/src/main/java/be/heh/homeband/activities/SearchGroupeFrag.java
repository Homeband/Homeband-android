package be.heh.homeband.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import be.heh.homeband.R;
import be.heh.homeband.activities.searchgroup.SearchGroupResultActivity;
import be.heh.homeband.app.HomebandApiInterface;
import be.heh.homeband.app.HomebandApiReponse;
import be.heh.homeband.app.HomebandGPSTracker;
import be.heh.homeband.app.HomebandRetrofit;
import be.heh.homeband.entities.Groupe;
import be.heh.homeband.entities.Style;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchGroupeFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchGroupeFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchGroupeFrag extends Fragment implements View.OnClickListener {
    ArrayAdapter<Style> adapterStyle;
    Spinner spinStyle;
    EditText etAdresse;
    EditText etKilometre;
    Button btnRecherche;
    ImageButton btnLocalisationGroupe;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SearchGroupeFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchGroupeFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchGroupeFrag newInstance(String param1, String param2) {
        SearchGroupeFrag fragment = new SearchGroupeFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myview = inflater.inflate(R.layout.fragment_search_groupe, container, false);
        initialisation(myview);

        initStyles();

        return myview;
    }

    @Override
    public void onClick(View v) {
        if(v == btnRecherche){
        getGroupes();
        }
        else if (v==btnLocalisationGroupe){
            getLocalisations();
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void initialisation(View myview){
        spinStyle = (Spinner) myview.findViewById(R.id.spinStyle);
        etAdresse = (EditText) myview.findViewById(R.id.etAdresse);
        etKilometre = (EditText) myview.findViewById(R.id.etKilometre);
        btnRecherche = (Button) myview.findViewById(R.id.btnRechercheGroupe);
        btnLocalisationGroupe = (ImageButton) myview.findViewById(R.id.btnLocalisationEvents);
        btnLocalisationGroupe.setOnClickListener(this);
        btnRecherche.setOnClickListener(this);
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
                            adapterStyle = new ArrayAdapter<Style>(getActivity().getApplicationContext(), R.layout.support_simple_spinner_dropdown_item);

                            // Ajout de la liste des styles à l'adapter
                            adapterStyle.addAll(listeStyles);

                            // Application de l'adapter au spinner
                            spinStyle.setAdapter(adapterStyle);
                        } else {
                            messageToast = "Échec de la connexion\r\n" + res.getMessage();

                            // Affichage d'un toast pour indiquer le résultat
                            Toast toast = Toast.makeText(getActivity().getApplicationContext(), messageToast, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    } else {
                        int statusCode = response.code();

                        String res = response.toString();
                        CharSequence message ="Erreur lors de l'appel à l'API (" + statusCode +")";
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG);
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
            Toast.makeText(getActivity(),(CharSequence)"Exception",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void getGroupes(){
        int var_style = ((Style)(spinStyle.getSelectedItem())).getId_styles();
        String var_cp = etAdresse.getText().toString();
        int var_kilometre = Integer.parseInt(etKilometre.getText().toString());

        try {
            Gson gson = new GsonBuilder().setLenient().create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HomebandRetrofit.API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            // Création d'une instance du service avec Retrofit
            HomebandApiInterface serviceApi = retrofit.create(HomebandApiInterface.class);
            Log.d("style",String.valueOf(var_style));
            Log.d("cp",var_cp);
            Log.d("kilometre",String.valueOf(var_kilometre));
            // Requête vers l'API
            serviceApi.getGroupes(var_style,var_cp,var_kilometre,0,0).enqueue(new Callback<HomebandApiReponse>() {
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
                            Type typeListe = new TypeToken<List<Groupe>>(){}.getType();

                            // Désérialisation du tableau JSON (JsonArray) en liste d'objets Style

                            //gson.fromJson prend 2 paramètres
                            //Premier paramètre c'est l'élément Json qu'il va falloir récupérer
                            //Deuxième paramètre c'est le type d'élément à récupérer
                            Gson gson = new Gson();
                            List<Groupe> listeGroupe = gson.fromJson(res.get("groups").getAsJsonArray(), typeListe);
                            Log.d("caca",listeGroupe.toString());
                            Intent intent = new Intent (getView().getContext(),SearchGroupResultActivity.class);
                            intent.putExtra("groupes",(ArrayList<Groupe>)listeGroupe);
                            startActivity(intent);





                        } else {
                            messageToast = "Échec de la connexion\r\n" + res.getMessage();

                            // Affichage d'un toast pour indiquer le résultat
                            Toast toast = Toast.makeText(getActivity().getApplicationContext(), messageToast, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    } else {
                        int statusCode = response.code();

                        String res = response.toString();
                        CharSequence message ="Erreur lors de l'appel à l'API (" + statusCode +")";
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG);
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
            Toast.makeText(getActivity(),(CharSequence)"Exception",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void getLocalisations(){
        double lat;
        double lon;

        HomebandGPSTracker gps = new HomebandGPSTracker(getContext(),this);
        if(gps.canGetLocation()){
            lat = gps.getLatitude();
            lon=gps.getLongitude();
        }
        else{
            gps.showSettingsAlert(2);
            return;
        }
        try {
            Gson gson = new GsonBuilder().setLenient().create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HomebandRetrofit.API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            // Création d'une instance du service avec Retrofit
            HomebandApiInterface serviceApi = retrofit.create(HomebandApiInterface.class);

            // Requête vers l'API
            serviceApi.getLocalisations(1,null,lat,lon).enqueue(new Callback<HomebandApiReponse>() {
                @Override
                public void onResponse(Call<HomebandApiReponse> call, Response<HomebandApiReponse> response) {

                    // En fonction du code HTTP de Retour (2** = Successful)
                    if (response.isSuccessful()) {

                        // Récupération de la réponse de l'API
                        HomebandApiReponse res = response.body();
                        res.mapResultat();

                        CharSequence messageToast;
                        if (res.isOperationReussie() == true) {
                            // Désérialisation du tableau JSON (JsonArray) en liste d'objets Style

                            //gson.fromJson prend 2 paramètres
                            //Premier paramètre c'est l'élément Json qu'il va falloir récupérer
                            //Deuxième paramètre c'est le type d'élément à récupérer
                            Gson gson = new Gson();
                            String address = res.get("address").getAsString();
                            etAdresse.setText(address);

                        } else {
                            messageToast = "Échec de la connexion\r\n" + res.getMessage();

                            // Affichage d'un toast pour indiquer le résultat
                            Toast toast = Toast.makeText(getActivity().getApplicationContext(), messageToast, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    } else {
                        int statusCode = response.code();

                        String res = response.toString();
                        CharSequence message ="Erreur lors de l'appel à l'API (" + statusCode +")";
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG);
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
            Toast.makeText(getActivity(),(CharSequence)"Exception",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("requestcode",String.valueOf(requestCode));
        Log.d("Permission",String.valueOf(permissions.length));
        Log.d("grantResult",String.valueOf(grantResults.length));
        if (grantResults.length>0){
            switch (requestCode){
                case 15 :
                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                        getLocalisations();
                    }
                    break;
            }


        }
    }
}
