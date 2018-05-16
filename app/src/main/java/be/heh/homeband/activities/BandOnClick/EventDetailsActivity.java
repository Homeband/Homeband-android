package be.heh.homeband.activities.BandOnClick;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import be.heh.homeband.Dao.AdresseDao;
import be.heh.homeband.Dao.VilleDao;
import be.heh.homeband.DaoImpl.AdresseDaoImpl;
import be.heh.homeband.DaoImpl.VilleDaoImpl;
import be.heh.homeband.R;
import be.heh.homeband.entities.Adresse;
import be.heh.homeband.entities.Evenement;
import be.heh.homeband.entities.Style;
import be.heh.homeband.entities.Ville;

public class EventDetailsActivity extends AppCompatActivity {

    Evenement event;
    Adresse adresse;

    TextView tvEventName;
    TextView tvEventAdresse;
    TextView tvEventDate;
    TextView tvPrix;

    VilleDao villeDao;

    public static String FACEBOOK_URL = "https://fr-fr.facebook.com/LeslieLouiseOFC/";
    public static String FACEBOOK_PAGE_ID = "LeslieLouiseOFC";


    Button btnCalendar;
    Button btnEvents;

    SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_events);
        event = (Evenement) getIntent().getSerializableExtra("event");
        adresse = (Adresse) getIntent().getSerializableExtra("address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        bindData(event);
    }

    @Override
    public boolean onSupportNavigateUp(){

        this.finish();
        return true;
    }

    public void init(){
        btnCalendar = (Button) findViewById(R.id.btnCalendar);
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addCalendar();
            }
        });

        btnEvents = (Button) findViewById(R.id.btnEvents);
        btnEvents.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                String facebookUrl = getFacebookPageURL(getApplicationContext());
                facebookIntent.setData(Uri.parse(facebookUrl));
                startActivity(facebookIntent);
            }
        });

        tvEventName = (TextView) findViewById(R.id.tvEventName);
        tvEventAdresse = (TextView) findViewById(R.id.tvEventAdresse);
        tvEventDate = (TextView) findViewById(R.id.tvEventDate);
        tvPrix = (TextView) findViewById(R.id.tvPrix);

        villeDao = new VilleDaoImpl();
    }

    public void bindData(Evenement event){

        Ville ville = villeDao.get(adresse.getId_villes());
        String adresseComplete = adresse.getRue() + " " + adresse.getNumero() + "\n" + ville.getCode_postal() + " " + ville.getNom();

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        tvEventName.setText(event.getNom());
        tvEventAdresse.setText(adresseComplete);
        tvEventDate.setText(dateFormatter.format(event.getDate_heure()));
        if (event.getPrix()==0){
            tvPrix.setText("Gratuit");

        }else{
            tvPrix.setText(String.format("%.2f",event.getPrix())+" €");

        }
    }

    public void addCalendar(){
        Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", cal.getTimeInMillis());
        intent.putExtra("allDay", true);
        intent.putExtra("rrule", "FREQ=YEARLY");
        intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
        intent.putExtra("title", "A Test Event from android app");
        startActivity(intent);
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
