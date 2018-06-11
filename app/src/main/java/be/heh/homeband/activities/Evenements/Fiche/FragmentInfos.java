package be.heh.homeband.activities.Evenements.Fiche;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import be.heh.homeband.Dao.GroupeDao;
import be.heh.homeband.Dao.VilleDao;
import be.heh.homeband.DaoImpl.GroupeDaoImpl;
import be.heh.homeband.DaoImpl.VilleDaoImpl;
import be.heh.homeband.R;
import be.heh.homeband.entities.Adresse;
import be.heh.homeband.entities.Evenement;
import be.heh.homeband.entities.Ville;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentInfos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentInfos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentInfos extends Fragment {

    Evenement event;
    Adresse adresse;

    VilleDao villeDao;
    GroupeDao groupeDao;

    TextView tvEventName;
    TextView tvEventAdresse;
    TextView tvEventDate;
    TextView tvPrix;

    SimpleDateFormat dateFormatter;


    private OnFragmentInteractionListener mListener;

    public FragmentInfos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FragmentInfos.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentInfos newInstance() {
        FragmentInfos fragment = new FragmentInfos();
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
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_infos, container, false);
        event = (Evenement) getActivity().getIntent().getSerializableExtra("event");
        adresse = (Adresse) getActivity().getIntent().getSerializableExtra("address");
        init(myview);
        bind(event);
        return myview;
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

    public void init(View myview){
        tvEventName = (TextView) myview.findViewById(R.id.tvEventName);
        tvEventAdresse = (TextView) myview.findViewById(R.id.tvEventAdresse);
        tvEventDate = (TextView) myview.findViewById(R.id.tvEventDate);
        tvPrix = (TextView) myview.findViewById(R.id.tvPrix);
        villeDao = new VilleDaoImpl();
        groupeDao = new GroupeDaoImpl();
    }

    public void bind(Evenement event){
        Ville ville = villeDao.get(adresse.getId_villes());
        String adresseComplete = adresse.getRue() + " " + adresse.getNumero() + "\n" + ville.getCode_postal() + " " + ville.getNom();

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        tvEventName.setText(event.getNom());
        tvEventAdresse.setText(adresseComplete);
        tvEventDate.setText(dateFormatter.format(event.getDate_heure()));
        if (event.getPrix()==0){
            tvPrix.setText("Gratuit");

        }else{
            tvPrix.setText(String.format("%.2f",event.getPrix())+" €");

        }
    }
}
