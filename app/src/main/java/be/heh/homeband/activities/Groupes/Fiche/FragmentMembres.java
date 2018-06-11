package be.heh.homeband.activities.Groupes.Fiche;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import be.heh.homeband.Dao.MembreDao;
import be.heh.homeband.R;
import be.heh.homeband.entities.Groupe;
import be.heh.homeband.entities.Membre;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentMembres.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentMembres#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMembres extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

   ListView lvMembres;
    Groupe groupe;

    ArrayList<Membre> membres;
    MembreDao membreDao;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentMembres() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMembres.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMembres newInstance(String param1, String param2) {
        FragmentMembres fragment = new FragmentMembres();
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
        groupe = (Groupe) getActivity().getIntent().getSerializableExtra("groupe");
        membres = (ArrayList<Membre>) getActivity().getIntent().getSerializableExtra("members");
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_membres, container, false);
        initialisation(myview);
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

    public void initialisation(View view){

        lvMembres = (ListView) view.findViewById(R.id.lvMembres);

        MembreAdapter adapter = new MembreAdapter(getContext(),membres);
        lvMembres.setAdapter(adapter);
    }
}
