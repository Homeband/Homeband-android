package be.heh.homeband.activities.Groupes.Fiche;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

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

    public static String FACEBOOK_URL;
    public static String TWITTER_URL;
    public static String YOUTUBE_URL;
    public static String BANDCAMP_URL;
    public static String SOUNDCLOUD_URL;
    public static String APPLE_URL;
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

        //Facebook
        FACEBOOK_URL = groupe.getLien_facebook();
        ibFacebook = (ImageButton) view.findViewById(R.id.ibFacebook);
        if(groupe.getLien_facebook() != "") {
            ibFacebook.setBackgroundResource(R.drawable.round_facebook);
            ibFacebook.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(FACEBOOK_URL));
                    startActivity(browserIntent);
                }
            });
        }

        //Apple
        APPLE_URL = groupe.getLien_itunes();
        ibApple  = (ImageButton) view.findViewById(R.id.ibApple);
        if(groupe.getLien_itunes() != "") {
            ibApple.setBackgroundResource(R.drawable.round_apple);
            ibApple.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(APPLE_URL));
                    startActivity(browserIntent);
                }
            });
        }


        //Bandcamp
        BANDCAMP_URL = groupe.getLien_bandcamp();
        ibBandcamp  = (ImageButton) view.findViewById(R.id.ibBandcamp);
        if(groupe.getLien_bandcamp() != "") {
            ibBandcamp.setBackgroundResource(R.drawable.round_bandcamp);
            ibBandcamp.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(BANDCAMP_URL));
                    startActivity(browserIntent);
                }
            });
        }

        //Instagram
        ibInstagram  = (ImageButton) view.findViewById(R.id.ibInstagram);
        if(groupe.getLien_instagram() != "") {
            ibInstagram.setBackgroundResource(R.drawable.round_insta);
            ibInstagram.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                }
            });
        }

        //Soundcloud
        SOUNDCLOUD_URL = groupe.getLien_soundcloud();
        ibSoundcloud  = (ImageButton) view.findViewById(R.id.ibSoundcloud);
        if(groupe.getLien_soundcloud() != "") {
            ibSoundcloud.setBackgroundResource(R.drawable.round_soundcloud);
            ibSoundcloud.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(SOUNDCLOUD_URL));
                    startActivity(browserIntent);
                }
            });
        }

        //Spotify
        ibSpotify  = (ImageButton) view.findViewById(R.id.ibSpotify);
        if(groupe.getLien_spotify() != "") {
            ibSpotify.setBackgroundResource(R.drawable.round_spotify);
            ibSpotify.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //requière uniquement l'app
                }
            });
        }

        //Youtube
        YOUTUBE_URL = groupe.getLien_youtube();
        ibYoutube  = (ImageButton) view.findViewById(R.id.ibYoutube);
        if(groupe.getLien_youtube() != "") {
            ibYoutube.setBackgroundResource(R.drawable.round_youtube);
            ibYoutube.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //requière uniquement l'app
                }
            });
        }

        //Twitter
        TWITTER_URL = groupe.getLien_twitter();
        ibTwitter  = (ImageButton) view.findViewById(R.id.ibTwitter);
        if(groupe.getLien_twitter() != "") {
            ibTwitter.setBackgroundResource(R.drawable.round_twitter);
           ibTwitter.setOnClickListener(new View.OnClickListener() {
               public void onClick(View v) {

                   //requière uniquement l'app
               }
           });
        }


    }
}