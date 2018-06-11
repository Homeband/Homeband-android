package be.heh.homeband.activities.Groupes.Recherche;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import be.heh.homeband.R;
import be.heh.homeband.entities.Groupe;
import be.heh.homeband.entities.Ville;
import io.realm.Realm;

/**
 * Created on 20-02-18.
 */

public class SearchGroupHolder extends RecyclerView.ViewHolder {
    private TextView tvGroupName;
    private TextView tvGroupCity;
    private ImageView imgGroup;

    //itemView est la vue correspondante à 1 cellule
    public SearchGroupHolder(View itemView) {
        super(itemView);

        tvGroupName = (TextView) itemView.findViewById(R.id.tvGroupName);
        tvGroupCity = (TextView) itemView.findViewById(R.id.tvGroupCity);
        imgGroup = (ImageView) itemView.findViewById(R.id.imgGroup);
    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    public void bind(Groupe monGroupe){
        Realm realm = Realm.getDefaultInstance();
        Ville ville = realm.where(Ville.class).equalTo("id_villes",monGroupe.getId_villes()).findFirst();
        tvGroupName.setText(monGroupe.getNom());
        if (ville != null){
            tvGroupCity.setText(ville.getNom());
        }else{
            tvGroupCity.setText("");
        }
        String url = "http://dev.zen-project.be/homeband/images/";
        if (monGroupe.getIllustration().equals("") ){
            url += "no_image.png";
        }
        else{
            url += "group/" + monGroupe.getIllustration();
        }
        Picasso.with(imgGroup.getContext()).load(url).centerCrop().fit().into(imgGroup);
    }
}
