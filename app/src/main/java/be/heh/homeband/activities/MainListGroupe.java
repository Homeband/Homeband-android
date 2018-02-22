package be.heh.homeband.activities;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import be.heh.homeband.R;
import be.heh.homeband.entities.Groupe;

/**
 * Created by christopher on 20-02-18.
 */

public class MainListGroupe extends AppCompatActivity{

    private RecyclerView recyclerView;

    private List<Groupe> cities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listgroup);

        //remplir la ville
        ajouterVilles();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        //définit l'agencement des cellules, ici de façon verticale, comme une ListView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //pour adapter en grille comme une RecyclerView, avec 2 cellules par ligne
        //recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        //puis créer un MyAdapter, lui fournir notre liste de villes.
        //cet adapter servira à remplir notre recyclerview
        recyclerView.setAdapter(new MyAdapter(cities));
    }

    private void ajouterVilles() {
        cities.add(new Groupe(1, "Groupe 1"));
        cities.add(new Groupe(2, "Groupe 2"));
        cities.add(new Groupe(3, "Groupe 3"));
        cities.add(new Groupe(4, "Groupe 4"));
    }
}
