package be.heh.homeband.activities.BandOnClick;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import be.heh.homeband.R;
import be.heh.homeband.entities.Groupe;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentBio.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentBio#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBio extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView tvBio;
    Groupe groupe;

    public static String FACEBOOK_URL = "https://fr-fr.facebook.com/LeslieLouiseOFC/";
    public static String FACEBOOK_PAGE_ID = "LeslieLouiseOFC";

    ImageButton ibFacebook;
    ImageButton ibTwitter;
    ImageButton ibInstagram;
    ImageButton ibYoutube;
    ImageButton ibSpotify;
    ImageButton ibBandcamp;
    ImageButton ibSoundcloud;
    ImageButton ibApple;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentBio() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBio.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBio newInstance(String param1, String param2) {
        FragmentBio fragment = new FragmentBio();
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
        //C'est l'objet groupe reçu depuis l'API
        groupe = (Groupe) getActivity().getIntent().getSerializableExtra("groupe");
        View myview = inflater.inflate(R.layout.fragment_bio, container, false);
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
        tvBio = (TextView) view.findViewById(R.id.tvBio);
        tvBio.setText(groupe.getBiographie());

        ibFacebook = (ImageButton) view.findViewById(R.id.ibFacebook);
        ibFacebook.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                String facebookUrl = getFacebookPageURL(getContext());
                facebookIntent.setData(Uri.parse(facebookUrl));
                startActivity(facebookIntent);
            }
        });

        ibApple  = (ImageButton) view.findViewById(R.id.ibApple);
        ibApple.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
        ibBandcamp  = (ImageButton) view.findViewById(R.id.ibBandcamp);
        ibBandcamp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
        ibInstagram  = (ImageButton) view.findViewById(R.id.ibInstagram);
        ibInstagram.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //requiere uniquement l'app
            }
        });
        ibSoundcloud  = (ImageButton) view.findViewById(R.id.ibSoundcloud);
        ibSoundcloud.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
        ibSpotify  = (ImageButton) view.findViewById(R.id.ibSpotify);
        ibSpotify.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //requière uniquement l'app
            }
        });
        ibYoutube  = (ImageButton) view.findViewById(R.id.ibYoutube);
        ibYoutube.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
        ibTwitter  = (ImageButton) view.findViewById(R.id.ibTwitter);
        ibTwitter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("twitter://LeslieLouiseOFC"));
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://twitter.com/LeslieLouiseOFC")));
                }
            }
        });
    }
    //method to get the right URL to use in the intent
    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }

}
