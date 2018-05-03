package be.heh.homeband.activities.BandOnClick;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import be.heh.homeband.Dao.AdresseDao;
import be.heh.homeband.DaoImpl.AdresseDaoImpl;
import be.heh.homeband.R;
import be.heh.homeband.entities.Adresse;
import be.heh.homeband.entities.Evenement;
import be.heh.homeband.entities.Style;

public class EventDetailsActivity extends AppCompatActivity {

    Evenement event;

    TextView tvEventName;
    TextView tvEventAdresse;
    TextView tvEventDate;
    TextView tvPrix;

    AdresseDao adresseDao;

    Button btnCalendar;
    Button btnEvents;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_events);
        event = (Evenement) getIntent().getSerializableExtra("event");
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

            }
        });

        btnEvents = (Button) findViewById(R.id.btnEvents);
        btnEvents.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        tvEventName = (TextView) findViewById(R.id.tvEventName);
        tvEventAdresse = (TextView) findViewById(R.id.tvEventAdresse);
        tvEventDate = (TextView) findViewById(R.id.tvEventDate);
        tvPrix = (TextView) findViewById(R.id.tvPrix);

        adresseDao = new AdresseDaoImpl();
    }

    public void bindData(Evenement event){

        Adresse adresse = adresseDao.get(event.getId_adresses());

        tvEventName.setText(event.getNom());
        tvEventAdresse.setText(adresse.getRue()+adresse.getNumero());
        tvEventDate.setText(event.getDate_heure().toString());
        //tvPrix.setText(event.getPrix());
    }
}
