package be.heh.homeband.activities.ListAlbum;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import be.heh.homeband.R;
import be.heh.homeband.activities.BandOnClick.RecyclerTouchListener;
import be.heh.homeband.activities.searchgroup.SearchGroupAdapter;
import be.heh.homeband.entities.Album;
import be.heh.homeband.entities.Groupe;

public class ListAlbumResultActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private List<Album> album = new ArrayList<Album>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listalbum);

        albums = (ArrayList<Album>) getIntent().getSerializableExtra("albums");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewAlbum);

        //définit l'agencement des cellules, ici de façon verticale, comme une ListView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //pour adapter en grille comme une RecyclerView, avec 2 cellules par ligne
        //recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        //puis créer un SearchGroupAdapter, lui fournir notre liste de villes.
        //cet adapter servira à remplir notre recyclerview
        recyclerView.setAdapter(new ListAlbumAdapter(albums));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Album album = album.get(position);
                //fonction getAlbum
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
