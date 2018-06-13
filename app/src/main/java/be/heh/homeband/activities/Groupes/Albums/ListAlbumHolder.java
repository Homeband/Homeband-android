package be.heh.homeband.activities.Groupes.Albums;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

import be.heh.homeband.R;
import be.heh.homeband.entities.Album;

public class ListAlbumHolder extends RecyclerView.ViewHolder {

    private TextView tvAlbumName;
    private TextView tvAlbumDate;
    private ImageView imgAlbum;

    SimpleDateFormat dateFormatter;

    //itemView est la vue correspondante à 1 cellule
    public ListAlbumHolder(View itemView) {
        super(itemView);

        //c'est ici que l'on fait nos findView

        tvAlbumName = (TextView) itemView.findViewById(R.id.tvAlbumName);
        tvAlbumDate = (TextView) itemView.findViewById(R.id.tvAlbumDate);
        imgAlbum = (ImageView) itemView.findViewById(R.id.imgAlbum);
    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    public void bind(Album monAlbum){
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy ");

        tvAlbumName.setText(monAlbum.getTitre());
        tvAlbumDate.setText(dateFormatter.format(monAlbum.getDate_sortie()));

        // Image
        String urlImage = "http://dev.zen-project.be/homeband/images/";
        if (monAlbum.getImage().equals("") ){
            urlImage += "no_image.png";
        }
        else{
            urlImage += "albums/" + monAlbum.getImage();
        }

        Picasso.with(imgAlbum.getContext()).load(urlImage).centerCrop().fit().into(imgAlbum);
    }
}
