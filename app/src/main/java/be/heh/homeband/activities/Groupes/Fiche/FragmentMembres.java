package be.heh.homeband.activities.Groupes.Fiche;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.parceler.Parcels;

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

   ListView lvMembres;
    Groupe groupe;

    ArrayList<Membre> membres;

    private OnFragmentInteractionListener mListener;

    public FragmentMembres() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentMembres.
     */
    public static FragmentMembres newInstance(String param1, String param2) {
        FragmentMembres fragment = new FragmentMembres();
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
        Intent intent = getActivity().getIntent();

        if(intent.hasExtra("type") && intent.getStringExtra("type").equals("local")){
            Bundle bundle = intent.getBundleExtra("params");
            groupe = Parcels.unwrap(bundle.getParcelable("groupe"));
            membres = (ArrayList<Membre>) bundle.get("members");
        } else {
            groupe = (Groupe) intent.getSerializableExtra("groupe");
            membres = (ArrayList<Membre>) intent.getSerializableExtra("members");
        }

        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_membres, container, false);
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

    public void initialisation(View view){

        lvMembres = (ListView) view.findViewById(R.id.lvMembres);

        MembreAdapter adapter = new MembreAdapter(getContext(),membres);
        lvMembres.setAdapter(adapter);
    }
}
