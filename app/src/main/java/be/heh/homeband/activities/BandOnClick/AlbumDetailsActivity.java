package be.heh.homeband.activities.BandOnClick;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

import be.heh.homeband.R;
import be.heh.homeband.entities.Album;
import be.heh.homeband.entities.Membre;
import be.heh.homeband.entities.Titre;

public class AlbumDetailsActivity extends AppCompatActivity {


    ListView lvAlbum;
   ArrayList<Titre> titre;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_album);
       titre = (ArrayList<Titre>) getIntent().getSerializableExtra("titres");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       init();
    }

    @Override
    public boolean onSupportNavigateUp(){

        this.finish();
        return true;
    }

    public void init(){
        lvAlbum = (ListView) findViewById(R.id.lvAlbum);
        AlbumAdapter adapter = new AlbumAdapter(getApplicationContext(),titre);
        lvAlbum.setAdapter(adapter);
    }
}
