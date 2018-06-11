package be.heh.homeband.activities.Groupes.Fiche;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.parceler.Parcels;

import be.heh.homeband.R;
import be.heh.homeband.entities.Groupe;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentContacts.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentContacts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentContacts extends Fragment {

    TextView tvContact;
    Groupe groupe;

    private OnFragmentInteractionListener mListener;

    public FragmentContacts() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentContacts.
     */
    public static FragmentContacts newInstance(String param1, String param2) {
        FragmentContacts fragment = new FragmentContacts();
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
        Intent intent = getActivity().getIntent();
        if(intent.hasExtra("type") && intent.getStringExtra("type").equals("local")){
            Bundle bundle = intent.getBundleExtra("params");
            groupe = Parcels.unwrap(bundle.getParcelable("groupe"));
        } else {
            groupe = (Groupe) getActivity().getIntent().getSerializableExtra("groupe");
        }
        View myview = inflater.inflate(R.layout.fragment_contacts, container, false);
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
        tvContact = (TextView) view.findViewById(R.id.tvContact);
        tvContact.setText(groupe.getContacts());
    }
}
