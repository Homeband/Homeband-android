package be.heh.homeband.activities.Main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import be.heh.homeband.Dao.AdresseDao;
import be.heh.homeband.Dao.EvenementDao;
import be.heh.homeband.Dao.UtilisateurDao;
import be.heh.homeband.DaoImpl.AdresseDaoImpl;
import be.heh.homeband.DaoImpl.EvenementDaoImpl;
import be.heh.homeband.DaoImpl.UtilisateurDaoImpl;
import be.heh.homeband.R;
import be.heh.homeband.activities.Evenements.Fiche.EventDetailsActivity;
import be.heh.homeband.app.RecyclerTouchListener;
import be.heh.homeband.activities.Evenements.Favoris.FragmentHomeAdapter;
import be.heh.homeband.entities.Adresse;
import be.heh.homeband.entities.Evenement;
import be.heh.homeband.entities.Utilisateur;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private TextView emptyView;
    private TextView tvTitleHome;
    private List<Evenement> events = new ArrayList<Evenement>();

    EvenementDao evenementDao;
    AdresseDao adresseDao;
    UtilisateurDao utilisateurDao;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFrag newInstance(String param1, String param2) {
        HomeFrag fragment = new HomeFrag();
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
        // Inflate the layout for this fragment

        View myview = inflater.inflate(R.layout.fragment_home, container, false);
        evenementDao = new EvenementDaoImpl();
        adresseDao = new AdresseDaoImpl();
        utilisateurDao = new UtilisateurDaoImpl();
        events = getFavouriteEvent();

        recyclerView = (RecyclerView) myview.findViewById(R.id.rvHome);
        emptyView = (TextView) myview.findViewById(R.id.empty_view);
        tvTitleHome = (TextView) myview.findViewById(R.id.tvTitleHome);

        if (events.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            tvTitleHome.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            tvTitleHome.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }

        //définit l'agencement des cellules, ici de façon verticale, comme une ListView
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //pour adapter en grille comme une RecyclerView, avec 2 cellules par ligne
        //recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        //puis créer un SearchGroupAdapter, lui fournir notre liste de villes.
        //cet adapter servira à remplir notre recyclerview
        recyclerView.setAdapter(new FragmentHomeAdapter(events));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Evenement event = events.get(position);
                getEvent(event.getId_evenements());
            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return myview;
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

    private void getEvent(int id){

        Evenement evenement = evenementDao.get(id);
        Adresse adresse = adresseDao.get(evenement.getId_adresses());

        Intent intent = new Intent (getContext(),EventDetailsActivity.class);
        intent.putExtra("event", evenement);
        intent.putExtra("address", adresse);
        startActivity(intent);
    }

    public List<Evenement> getFavouriteEvent(){
        Utilisateur user = utilisateurDao.getConnectedUser();
        return evenementDao.listByUser(user.getId_utilisateurs());
    }
}