package be.heh.homeband.activities.Evenements.Favoris;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

import be.heh.homeband.R;
import be.heh.homeband.entities.Evenement;
import be.heh.homeband.entities.Ville;
import io.realm.Realm;

/**
 * Created on 27-02-18.
 */

public class FragmentHomeHolder extends RecyclerView.ViewHolder {
    private TextView tvEvent;
    private TextView tvEventCity;
    private TextView tvEventDate;
    private ImageView imageView;

    SimpleDateFormat dateFormatter;

    //itemView est la vue correspondante à 1 cellule
    public FragmentHomeHolder(View itemView) {
        super(itemView);

        //c'est ici que l'on fait nos findView

        tvEvent = (TextView) itemView.findViewById(R.id.textEvents);
        tvEventCity = (TextView) itemView.findViewById(R.id.tvEventCity);
        tvEventDate = (TextView) itemView.findViewById(R.id.tvEventDate);
        imageView = (ImageView) itemView.findViewById(R.id.imageEvents);
    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    public void bind(Evenement monEvent){
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Realm realm = Realm.getDefaultInstance();
        Ville ville = realm.where(Ville.class).equalTo("id_villes",monEvent.getId_adresses()).findFirst();
        tvEvent.setText(monEvent.getNom());
        if (ville != null){
            tvEventCity.setText(ville.getNom());
        }else{
            tvEventCity.setText("");
        }

        tvEventDate.setText(dateFormatter.format(monEvent.getDate_heure()));
        String url = "http://dev.zen-project.be/homeband/images/";
        if (monEvent.getIllustration().equals("")){
            url += "no_image.png";
        }
        else{
            url += "event/" + monEvent.getIllustration();
        }
        Picasso.with(imageView.getContext()).load(url).centerCrop().fit().into(imageView);
    }
}
