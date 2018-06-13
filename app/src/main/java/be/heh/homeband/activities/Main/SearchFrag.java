package be.heh.homeband.activities.Main;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import be.heh.homeband.Dao.StyleDao;
import be.heh.homeband.DaoImpl.StyleDaoImpl;
import be.heh.homeband.R;
import be.heh.homeband.activities.Evenements.Recherche.SearchEventsResultActivity;
import be.heh.homeband.activities.Groupes.Recherche.SearchGroupResultActivity;
import be.heh.homeband.app.HomebandApiInterface;
import be.heh.homeband.app.HomebandApiReponse;
import be.heh.homeband.app.HomebandGPSTracker;
import be.heh.homeband.app.HomebandRetrofit;
import be.heh.homeband.entities.Evenement;
import be.heh.homeband.entities.Groupe;
import be.heh.homeband.entities.Style;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFrag extends Fragment implements View.OnClickListener {
    Button btnEvents;
    Button btnGroupe;
    Button btnRecherche;

    int btnChoice;
    public static int SEARCH_GROUP = 0;
    public static int SEARCH_EVENT = 1;


    ArrayAdapter<Style> adapterStyle;
    Spinner spinStyle;

    EditText etAdresse;
    EditText etDu;
    EditText etAu;
    EditText etKilometre;

    RelativeLayout rlDate;
    ImageButton btnLocalisationGroupe;

    TextView tvDateDu;
    TextView tvDateAu;

    Switch swAfficherDate;

    Calendar calendarDateDu;
    Calendar calendarDateAu;

    DatePickerDialog pickerDateDu;
    DatePickerDialog pickerDateAu;

    SimpleDateFormat dateFormatter;
    SimpleDateFormat dateFormatterAPI;

    StyleDao styleDao;

    private OnFragmentInteractionListener mListener;

    public SearchFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFrag newInstance(String param1, String param2) {
        SearchFrag fragment = new SearchFrag();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myview = inflater.inflate(R.layout.fragment_search, container, false);
        initialisation(myview);
        return myview;
    }

    @Override
    public void onClick(View v) {
        if(v == btnGroupe){
            onClickBtnGroupes();
        } else if(v == btnEvents){
            onClickBtnEvents();
        } else if (v==btnLocalisationGroupe){
            getLocalisations();
        } else if (v == etDu) {
            pickerDateDu.show();
        } else if (v == etAu) {
            pickerDateAu.show();
        } else if(v == btnRecherche){
            if( btnChoice == SEARCH_GROUP ){
                getGroupes();
            } else {
                getEvents();
            }
        }
    }

    private void onClickBtnGroupes(){
        btnChoice=SEARCH_GROUP;
        int hbred = Color.parseColor("#CE2828");
        btnGroupe.setBackgroundColor(hbred);
        btnEvents.setBackgroundColor(Color.WHITE);
        rlDate.setVisibility(View.INVISIBLE);
    }

    private void onClickBtnEvents(){
        btnChoice=SEARCH_EVENT;
        int hbred = Color.parseColor("#CE2828");
        btnEvents.setBackgroundColor(hbred);
        btnGroupe.setBackgroundColor(Color.WHITE);
        rlDate.setVisibility(View.VISIBLE);

    }

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
       void onFragmentInteraction(Uri uri);
    }
    public void initialisation(View myview){

        btnEvents = (Button) myview.findViewById(R.id.btnEvenement);
        btnEvents.setOnClickListener(this);
        btnGroupe = (Button) myview.findViewById(R.id.btnGroupe);
        btnGroupe.setOnClickListener(this);

        spinStyle = (Spinner) myview.findViewById(R.id.spinStyle);
        etAdresse = (EditText) myview.findViewById(R.id.etAdresse);
        etKilometre = (EditText) myview.findViewById(R.id.etKilometre);
        btnRecherche = (Button) myview.findViewById(R.id.btnRechercheGroupe);
        btnLocalisationGroupe = (ImageButton) myview.findViewById(R.id.btnLocalisationEvents);
        btnLocalisationGroupe.setOnClickListener(this);
        btnRecherche.setOnClickListener(this);

        etDu = (EditText)  myview.findViewById(R.id.etDu);
        etAu = (EditText)  myview.findViewById(R.id.etAu);
        etAdresse = (EditText)  myview.findViewById(R.id.etAdresse);
        tvDateDu = (TextView) myview.findViewById(R.id.tvDu);
        tvDateAu = (TextView) myview.findViewById(R.id.tvAu);
        swAfficherDate = (Switch) myview.findViewById(R.id.swDate);

        rlDate = (RelativeLayout) myview.findViewById(R.id.rlDate);

        // Switch Affichage Date
        swAfficherDate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    etDu.setVisibility(View.VISIBLE);
                    etAu.setVisibility(View.VISIBLE);
                    tvDateDu.setVisibility(View.VISIBLE);
                    tvDateAu.setVisibility(View.VISIBLE);
                } else {
                    etDu.setVisibility(View.INVISIBLE);
                    etAu.setVisibility(View.INVISIBLE);
                    tvDateDu.setVisibility(View.INVISIBLE);
                    tvDateAu.setVisibility(View.INVISIBLE);
                }
            }
        });

        etKilometre.setText("10");

        initDate();
        initStyles();
    }

    public void initStyles(){

        styleDao = new StyleDaoImpl();
        List<Style> listeStyles = styleDao.list();

        //Ajout de "Tous les styles" en position 0
        Style allStyles = new Style();
        allStyles.setNom("Tous les styles");
        allStyles.setId_styles(0);
        listeStyles.add(0,allStyles);


        // Initialisation de l'adapter
        adapterStyle = new ArrayAdapter<Style>(getActivity().getApplicationContext(), R.layout.list_item_spinner,R.id.tvItem);

        // Ajout de la liste des styles à l'adapter
        adapterStyle.addAll(listeStyles);

        // Application de l'adapter au spinner
        spinStyle.setAdapter(adapterStyle);

    }

    public void initDate (){

        // Initialisation des calendriers
        calendarDateDu = Calendar.getInstance();
        calendarDateAu = Calendar.getInstance();

        // Ajout des listeners sur les EditText
        etDu.setOnClickListener(this);
        etAu.setOnClickListener(this);

        // Initialisation du formateur de date
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        dateFormatterAPI = new SimpleDateFormat("yyyy-MM-dd");

        // Initialisation du DatePickerDialog de la première date (et définition du comportement lors de la sélection)
        pickerDateDu = new DatePickerDialog(getContext(), R.style.ThemeDatePicker, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etDu.setText(dateFormatter.format(newDate.getTime()));
                // Si datedu > dateau ou dateau vide => dateAu = dateDu

            }
        }, calendarDateDu.get(Calendar.YEAR), calendarDateDu.get(Calendar.MONTH), calendarDateDu.get(Calendar.DAY_OF_MONTH));

        // Initialisation du DatePickerDialog de la deuxième date (et définition du comportement lors de la sélection)
        pickerDateAu = new DatePickerDialog(getContext(), R.style.ThemeDatePicker, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etAu.setText(dateFormatter.format(newDate.getTime()));
                //DateAu < DateDu alors dateAu = DateDu
            }
        }, calendarDateAu.get(Calendar.YEAR), calendarDateAu.get(Calendar.MONTH), calendarDateAu.get(Calendar.DAY_OF_MONTH));
    }

    public void getGroupes(){
        int var_style = ((Style)(spinStyle.getSelectedItem())).getId_styles();

        String var_adresse = etAdresse.getText().toString();
        int var_kilometre = etKilometre.getText().toString() != "" ? Integer.parseInt(etKilometre.getText().toString()) : 0;

        if(var_adresse.equals("")){
            var_kilometre = 0;
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
            serviceApi.getGroupes(var_style,var_adresse,var_kilometre).enqueue(new Callback<HomebandApiReponse>() {
                @Override
                public void onResponse(Call<HomebandApiReponse> call, Response<HomebandApiReponse> response) {

                    // En fonction du code HTTP de Retour (2** = Successful)
                    if (response.isSuccessful()) {

                        // Récupération de la réponse de l'API
                        HomebandApiReponse res = response.body();
                        res.mapResultat();

                        CharSequence messageToast;
                        if (res.isOperationReussie() == true) {
                            Type typeListe = new TypeToken<List<Groupe>>(){}.getType();
                            Gson gson = new Gson();
                            List<Groupe> listeGroupe = gson.fromJson(res.get("groups").getAsJsonArray(), typeListe);
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

    public void getEvents(){
        int var_style = ((Style)(spinStyle.getSelectedItem())).getId_styles();
        String adresse = etAdresse.getText().toString();
        int var_kilometre = etKilometre.getText().toString() != "" ? Integer.parseInt(etKilometre.getText().toString()) : 0;

        if(adresse.equals("")){
            var_kilometre = 0;
        }

        String dateDu = null;
        String dateAu = null;

       if(swAfficherDate.isChecked()){
           try{
               dateDu = dateFormatterAPI.format(dateFormatter.parse(etDu.getText().toString()));
               dateAu = dateFormatterAPI.format(dateFormatter.parse(etAu.getText().toString()));
           } catch(Exception e){
               e.printStackTrace();
           }
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
            serviceApi.getEvenements(var_style,adresse,var_kilometre,dateDu,dateAu, 1).enqueue(new Callback<HomebandApiReponse>() {
                @Override
                public void onResponse(Call<HomebandApiReponse> call, Response<HomebandApiReponse> response) {

                    // En fonction du code HTTP de Retour (2** = Successful)
                    if (response.isSuccessful()) {

                        // Récupération de la réponse de l'API
                        HomebandApiReponse res = response.body();
                        res.mapResultat();

                        CharSequence messageToast;
                        if (res.isOperationReussie() == true) {
                            Type typeListe = new TypeToken<List<Evenement>>(){}.getType();
                            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                            List<Evenement> listeEvents = gson.fromJson(res.get("events").getAsJsonArray(), typeListe);
                            Intent intent = new Intent (getView().getContext(),SearchEventsResultActivity.class);
                            intent.putExtra("events",(ArrayList<Evenement>)listeEvents);
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
