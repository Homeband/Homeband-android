package be.heh.homeband.activities.Main;

import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFrag extends Fragment implements View.OnClickListener {

    Utilisateur user;

    UtilisateurDao utilisateurDao;
    EditText etMail;
    EditText etMDP;
    EditText etMDPC;

    Button btnModifierSettings;
    Button btnDisconnect;


    private OnFragmentInteractionListener mListener;

    public SettingsFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static SettingsFrag newInstance(String param1, String param2) {
        SettingsFrag fragment = new SettingsFrag();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview =  inflater.inflate(R.layout.fragment_settings, container, false);
        initialisation(myview);
        return myview;
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

    public void onClick(View v) {
        utilisateurDao.disconnectUser();
        getActivity().finish();
    }

    public void initialisation(View myview){
        utilisateurDao = new UtilisateurDaoImpl();

        btnDisconnect = (Button) myview.findViewById(R.id.btnDisconnect);
        btnDisconnect.setOnClickListener(this);

        user = utilisateurDao.getConnectedUser();

        etMail = myview.findViewById(R.id.etMailSettings);
        etMail.setText(user.getEmail());
        etMDP = myview.findViewById(R.id.etMDP);
        etMDPC = myview.findViewById(R.id.etMDPC);

        btnModifierSettings = myview.findViewById(R.id.btnModifierSettings);
        btnModifierSettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeParams();

            }
        });


    }

    public void changeParams(){

        if(isEmailValid(etMail.getText().toString())){
            if(etMDP.getText().toString() != "" || etMDPC.getText().toString() != "" ){
                if(etMDP.getText().toString().equals(etMDPC.getText().toString())){
                    user.setMot_de_passe(etMDP.getText().toString());
                }
                else{
                    Toast toast = Toast.makeText(getContext(), "Les deux mot de passe ne sont pas identiques", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }
            }
            user.setEmail(etMail.getText().toString());
            user.setEst_actif(true);
            modifierMail(user);
        }
        else{
            Toast toast = Toast.makeText(getContext(), "Adresse mail non valide", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }



    }

    public void modifierMail(Utilisateur user){
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("user",user);
        final DialogFragment loading = new LoadingDialog();
        android.app.FragmentManager frag = ((AppCompatActivity) getActivity()).getFragmentManager();
        loading.show(frag,"LoadingDialog");
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HomebandRetrofit.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // Création d'une instance du service avec Retrofit
            HomebandApiInterface serviceApi = retrofit.create(HomebandApiInterface.class);

            // Requête vers l'API
            serviceApi.putSettings(user.getId_utilisateurs(),params).enqueue(new Callback<HomebandApiReponse>() {
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
                            final Utilisateur user = gson.fromJson(res.get("user").getAsJsonObject(), Utilisateur.class);
                            utilisateurDao.write(user);
                            Toast toast = Toast.makeText(getContext(), "Les modifications demandées ont bien été effectuées.", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            loading.dismiss();

                        } else {
                            loading.dismiss();
                            messageToast = "Une erreur s'est produite lors de la modification des données.\r\n" + res.getMessage();

                            // Affichage d'un toast pour indiquer le résultat
                            Toast toast = Toast.makeText(getContext(), messageToast, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    } else {
                        loading.dismiss();
                        int statusCode = response.code();

                        String res = response.toString();
                        CharSequence message ="Erreur lors de l'appel à l'API (" + statusCode +")";
                        Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_LONG);
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
            Toast.makeText(getContext(),(CharSequence)"Exception",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
