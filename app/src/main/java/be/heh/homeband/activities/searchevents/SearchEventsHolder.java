package be.heh.homeband.activities.searchevents;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import be.heh.homeband.R;
import be.heh.homeband.entities.Evenement;

/**
 * Created by christopher on 27-02-18.
 */

public class SearchEventsHolder extends RecyclerView.ViewHolder {
    private TextView textViewView;
    private ImageView imageView;

    //itemView est la vue correspondante à 1 cellule
    public SearchEventsHolder(View itemView) {
        super(itemView);

        //c'est ici que l'on fait nos findView

        textViewView = (TextView) itemView.findViewById(R.id.textEvents);
        imageView = (ImageView) itemView.findViewById(R.id.imageEvents);
    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    public void bind(Evenement monevent){
        textViewView.setText(monevent.getNom());
        Picasso.with(imageView.getContext()).load("http://www.tate.org.uk/art/images/work/T/T05/T05010_10.jpg").centerCrop().fit().into(imageView);
    }
}
