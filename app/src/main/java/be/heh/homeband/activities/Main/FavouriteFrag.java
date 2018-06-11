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

import be.heh.homeband.Dao.GroupeDao;
import be.heh.homeband.Dao.MembreDao;
import be.heh.homeband.Dao.UtilisateurDao;
import be.heh.homeband.DaoImpl.GroupeDaoImpl;
import be.heh.homeband.DaoImpl.MembreDaoImpl;
import be.heh.homeband.DaoImpl.UtilisateurDaoImpl;
import be.heh.homeband.R;
import be.heh.homeband.activities.Groupes.Fiche.GroupeDetailsActivity;
import be.heh.homeband.app.RecyclerTouchListener;
import be.heh.homeband.activities.Groupes.Recherche.SearchGroupAdapter;
import be.heh.homeband.entities.Groupe;
import be.heh.homeband.entities.Membre;
import be.heh.homeband.entities.Utilisateur;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavouriteFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavouriteFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouriteFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private TextView emptyView;
    private TextView tvTitleFavourite;
    private List<Groupe> groupes = new ArrayList<Groupe>();

    GroupeDao groupeDao;
    UtilisateurDao utilisateurDao;
    MembreDao membreDao;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FavouriteFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavouriteFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static FavouriteFrag newInstance(String param1, String param2) {
        FavouriteFrag fragment = new FavouriteFrag();
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
        View myview = inflater.inflate(R.layout.fragment_favourite, container, false);
        groupeDao = new GroupeDaoImpl();
        utilisateurDao = new UtilisateurDaoImpl();
        membreDao = new MembreDaoImpl();
        groupes = getFavouriteGroup();

        recyclerView = (RecyclerView) myview.findViewById(R.id.rvFavourite);
        emptyView = (TextView) myview.findViewById(R.id.empty_view);
        tvTitleFavourite = (TextView) myview.findViewById(R.id.tvTitleFavourite);

        if (groupes.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            tvTitleFavourite.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            tvTitleFavourite.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(new SearchGroupAdapter(groupes));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Groupe groupe = groupes.get(position);
                getGroupe(groupe.getId_groupes());
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

    private void getGroupe(int id){
        Groupe groupe = groupeDao.get(id);
        List<Membre> membres = membreDao.getByGroupe(id);

        Intent intent = new Intent (getContext(),GroupeDetailsActivity.class);
        intent.putExtra("groupe",groupe);
        intent.putExtra("members",(ArrayList<Membre>) membres);
        startActivity(intent);
    }

    public List<Groupe> getFavouriteGroup(){
        Utilisateur user = utilisateurDao.getConnectedUser();
        return groupeDao.listByUser(user.getId_utilisateurs());
    }
}
