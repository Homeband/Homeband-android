package be.heh.homeband.activities.Groupes.Albums;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import be.heh.homeband.R;
import be.heh.homeband.entities.Album;
import be.heh.homeband.entities.Titre;

public class AlbumDetailsActivity extends AppCompatActivity {


    ListView lvAlbum;
   ArrayList<Titre> titre;
    Album album;
   ImageView ivAlbum;

   private TextView tvAlbumname;
   private TextView tvAlbumDate;

    SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_album);
       titre = (ArrayList<Titre>) getIntent().getSerializableExtra("titres");
       album = (Album) getIntent().getSerializableExtra("albums");
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

        tvAlbumname = findViewById(R.id.tvAlbumName);
        tvAlbumDate = findViewById(R.id.tvAlbumDate);

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

        tvAlbumname.setText(album.getTitre());
        tvAlbumDate.setText(dateFormatter.format(album.getDate_sortie()));
        ivAlbum = (ImageView) findViewById(R.id.ivAlbum);
        String url = "http://dev.zen-project.be/homeband/images/";
        if ( album.getImage() == ""){
            url += "no_image.png";
        }
        else{
            url += "group/" + album.getImage();
        }
        Picasso.with(this).load(url).centerCrop().fit().into(ivAlbum);
    }
}
