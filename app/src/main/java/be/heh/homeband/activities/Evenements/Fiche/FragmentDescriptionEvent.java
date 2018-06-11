package be.heh.homeband.activities.Evenements.Fiche;

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

import java.text.SimpleDateFormat;

import be.heh.homeband.Dao.GroupeDao;
import be.heh.homeband.Dao.VilleDao;
import be.heh.homeband.R;
import be.heh.homeband.entities.Adresse;
import be.heh.homeband.entities.Evenement;
import be.heh.homeband.entities.Groupe;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentDescriptionEvent.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentDescriptionEvent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDescriptionEvent extends Fragment {

    Evenement event;
    Adresse adresse;
    TextView tvEventDetail;

    private OnFragmentInteractionListener mListener;

    public FragmentDescriptionEvent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentDescriptionEvent.
     */
    public static FragmentDescriptionEvent newInstance() {
        FragmentDescriptionEvent fragment = new FragmentDescriptionEvent();
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

         View myView = inflater.inflate(R.layout.fragment_description_event, container, false);
        Intent intent = getActivity().getIntent();

        if(intent.hasExtra("type") && intent.getStringExtra("type").equals("local")){
            Bundle bundle = intent.getBundleExtra("params");
            event = Parcels.unwrap(bundle.getParcelable("event"));
            adresse = (Adresse) intent.getSerializableExtra("address");
        } else {
            event = (Evenement) intent.getSerializableExtra("event");
            adresse = (Adresse) intent.getSerializableExtra("address");
        }
         init(myView);
        return myView;
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

    public void init(View myView){
        tvEventDetail = (TextView) myView.findViewById(R.id.tvEventDetails);
        tvEventDetail.setText(event.getDescription());
    }


}
