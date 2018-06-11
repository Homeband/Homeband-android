package be.heh.homeband.activities.Groupes.Recherche;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import be.heh.homeband.Dao.VilleDao;
import be.heh.homeband.DaoImpl.VilleDaoImpl;
import be.heh.homeband.R;
import be.heh.homeband.entities.Groupe;
import be.heh.homeband.entities.Ville;
import io.realm.Realm;

/**
 * Created on 20-02-18.
 */

public class SearchGroupHolder extends RecyclerView.ViewHolder {

    // Eléments visuels
    private TextView tvGroupName;
    private TextView tvGroupCity;
    private ImageView imgGroup;

    private VilleDao villeDao;

    // Constructeur (Binding des variables avec le XML)
    public SearchGroupHolder(View itemView) {
        super(itemView);

        tvGroupName = (TextView) itemView.findViewById(R.id.tvGroupName);
        tvGroupCity = (TextView) itemView.findViewById(R.id.tvGroupCity);
        imgGroup = (ImageView) itemView.findViewById(R.id.imgGroup);

        villeDao = new VilleDaoImpl();
    }

    // Remplis les éléments avec les valeurs de l'objets (ici le groupe)
    public void bind(Groupe monGroupe){

        Ville ville = villeDao.get(monGroupe.getId_villes());

        tvGroupName.setText(monGroupe.getNom());

        if (ville != null){
            tvGroupCity.setText(ville.getNom());
        } else{
            tvGroupCity.setText("");
        }

        // Image
        String urlImage = "http://dev.zen-project.be/homeband/images/";
        if (monGroupe.getIllustration().equals("") ){
            urlImage += "no_image.png";
        }
        else{
            urlImage += "group/" + monGroupe.getIllustration();
        }

        Picasso.with(imgGroup.getContext()).load(urlImage).centerCrop().fit().into(imgGroup);
    }
}
