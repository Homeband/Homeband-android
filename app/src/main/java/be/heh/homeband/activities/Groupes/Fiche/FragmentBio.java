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

import org.parceler.Parcels;

import java.util.ArrayList;

import be.heh.homeband.R;
import be.heh.homeband.entities.Groupe;
import be.heh.homeband.entities.Membre;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentBio.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentBio#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBio extends Fragment {

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

    private OnFragmentInteractionListener mListener;

    public FragmentBio() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentBio.
     */
    public static FragmentBio newInstance(String param1, String param2) {
        FragmentBio fragment = new FragmentBio();
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
        //C'est l'objet groupe reçu depuis l'API
        Intent intent = getActivity().getIntent();
        if(intent.hasExtra("type") && intent.getStringExtra("type").equals("local")){
            Bundle bundle = intent.getBundleExtra("params");
            groupe = Parcels.unwrap(bundle.getParcelable("groupe"));
        } else {
            groupe = (Groupe) getActivity().getIntent().getSerializableExtra("groupe");
        }

        View myview = inflater.inflate(R.layout.fragment_bio, container, false);
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
        tvBio = (TextView) view.findViewById(R.id.tvBio);
        tvBio.setText(groupe.getBiographie());

        //Facebook
        FACEBOOK_URL = groupe.getLien_facebook();
        ibFacebook = (ImageButton) view.findViewById(R.id.ibFacebook);
        if(!groupe.getLien_facebook().equals("")) {
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
        if(!groupe.getLien_itunes().equals("")) {
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
        if(!groupe.getLien_bandcamp().equals("")) {
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
        if(!groupe.getLien_instagram().equals("")) {
            ibInstagram.setBackgroundResource(R.drawable.round_insta);
            ibInstagram.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                }
            });
        }

        //Soundcloud
        SOUNDCLOUD_URL = groupe.getLien_soundcloud();
        ibSoundcloud  = (ImageButton) view.findViewById(R.id.ibSoundcloud);
        if(!groupe.getLien_soundcloud().equals("")) {
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
        if(!groupe.getLien_spotify().equals("")) {
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
        if(!groupe.getLien_youtube().equals("")) {
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
        if(!groupe.getLien_twitter().equals("")) {
            ibTwitter.setBackgroundResource(R.drawable.round_twitter);
           ibTwitter.setOnClickListener(new View.OnClickListener() {
               public void onClick(View v) {

                   //requière uniquement l'app
               }
           });
        }


    }
}
