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

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import be.heh.homeband.Dao.AdresseDao;
import be.heh.homeband.Dao.EvenementDao;
import be.heh.homeband.Dao.GroupeDao;
import be.heh.homeband.Dao.UtilisateurDao;
import be.heh.homeband.DaoImpl.AdresseDaoImpl;
import be.heh.homeband.DaoImpl.EvenementDaoImpl;
import be.heh.homeband.DaoImpl.GroupeDaoImpl;
import be.heh.homeband.DaoImpl.UtilisateurDaoImpl;
import be.heh.homeband.R;
import be.heh.homeband.activities.Evenements.Fiche.EventDetailsActivity;
import be.heh.homeband.activities.Groupes.Fiche.GroupeDetailsActivity;
import be.heh.homeband.app.RecyclerTouchListener;
import be.heh.homeband.activities.Evenements.Favoris.FragmentHomeAdapter;
import be.heh.homeband.entities.Adresse;
import be.heh.homeband.entities.Evenement;
import be.heh.homeband.entities.Groupe;
import be.heh.homeband.entities.Membre;
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

    private RecyclerView recyclerView;
    private TextView emptyView;
    private TextView tvTitleHome;
    private List<Evenement> events = new ArrayList<Evenement>();

    EvenementDao evenementDao;
    AdresseDao adresseDao;
    GroupeDao groupeDao;
    UtilisateurDao utilisateurDao;
    private OnFragmentInteractionListener mListener;

    public HomeFrag() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFrag.
     */

    public static HomeFrag newInstance(String param1, String param2) {
        HomeFrag fragment = new HomeFrag();
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

        View myview = inflater.inflate(R.layout.fragment_home, container, false);
        evenementDao = new EvenementDaoImpl();
        adresseDao = new AdresseDaoImpl();
        groupeDao = new GroupeDaoImpl();
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

    private void getEvent(int id){

        Evenement evenement = evenementDao.get(id);
        Groupe groupe = groupeDao.get(evenement.getId_groupes());
        Adresse adresse = adresseDao.get(evenement.getId_adresses());


        Bundle bundle = new Bundle();
        bundle.putParcelable("event", Parcels.wrap(evenement));
        bundle.putParcelable("group", Parcels.wrap(groupe));

        Intent intent = new Intent (getContext(), EventDetailsActivity.class);
        intent.putExtra("type", "local");
        intent.putExtra("address", adresse);
        intent.putExtra("params", bundle);
        startActivity(intent);
    }

    public List<Evenement> getFavouriteEvent(){
        Utilisateur user = utilisateurDao.getConnectedUser();
        return evenementDao.listByUser(user.getId_utilisateurs());
    }
}
