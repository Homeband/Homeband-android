package be.heh.homeband.activities.searchevents;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import be.heh.homeband.R;
import be.heh.homeband.entities.Evenement;
import be.heh.homeband.entities.Groupe;

/**
 * Created by christopher on 27-02-18.
 */

public class SearchEventsResultActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private List<Evenement> events = new ArrayList<Evenement>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listevents);

        events = (ArrayList<Evenement>) getIntent().getSerializableExtra("events");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewEvents);

        //définit l'agencement des cellules, ici de façon verticale, comme une ListView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //pour adapter en grille comme une RecyclerView, avec 2 cellules par ligne
        //recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        //puis créer un SearchGroupAdapter, lui fournir notre liste de villes.
        //cet adapter servira à remplir notre recyclerview
        recyclerView.setAdapter(new SearchEventsAdapter(events));
    }

}
