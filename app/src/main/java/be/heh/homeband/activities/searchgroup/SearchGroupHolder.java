package be.heh.homeband.activities.searchgroup;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import be.heh.homeband.R;
import be.heh.homeband.entities.Groupe;

/**
 * Created by christopher on 20-02-18.
 */

public class SearchGroupHolder extends RecyclerView.ViewHolder {
    private TextView tvGroupName;
    private TextView tvGroupCity;
    private ImageView imgGroup;

    //itemView est la vue correspondante à 1 cellule
    public SearchGroupHolder(View itemView) {
        super(itemView);

        //c'est ici que l'on fait nos findView

        tvGroupName = (TextView) itemView.findViewById(R.id.tvGroupName);
        tvGroupCity = (TextView) itemView.findViewById(R.id.tvGroupCity);
        imgGroup = (ImageView) itemView.findViewById(R.id.imgGroup);
    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    public void bind(Groupe monGroupe){
        tvGroupName.setText(monGroupe.getNom());
        tvGroupCity.setText(("La Louviere"));
        Picasso.with(imgGroup.getContext()).load("http://www.tate.org.uk/art/images/work/T/T05/T05010_10.jpg").centerCrop().fit().into(imgGroup);
    }
}
