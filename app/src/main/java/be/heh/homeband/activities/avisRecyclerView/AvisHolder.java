package be.heh.homeband.activities.avisRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

import be.heh.homeband.Dao.UtilisateurDao;
import be.heh.homeband.DaoImpl.UtilisateurDaoImpl;
import be.heh.homeband.R;
import be.heh.homeband.entities.Avis;
import be.heh.homeband.entities.Groupe;
import be.heh.homeband.entities.Utilisateur;
import be.heh.homeband.entities.Ville;
import io.realm.Realm;

public class AvisHolder extends RecyclerView.ViewHolder {

    private TextView tvUtilisateur;
    private TextView tvDateAvis;
    private TextView tvComment;

    UtilisateurDao utilisateurDao;

    SimpleDateFormat dateFormatter;

    //itemView est la vue correspondante à 1 cellule
    public AvisHolder(View itemView) {
        super(itemView);

        //c'est ici que l'on fait nos findView

        tvUtilisateur = (TextView) itemView.findViewById(R.id.tvUtilisateur);
        tvDateAvis = (TextView) itemView.findViewById(R.id.tvDateAvis);
        tvComment = (TextView) itemView.findViewById(R.id.tvComment);

        utilisateurDao = new UtilisateurDaoImpl();



    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    public void bind(Avis monavis){
        Utilisateur user = utilisateurDao.get(monavis.getId_utilisateurs());

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy ");
        tvUtilisateur.setText(user.getLogin());
        tvDateAvis.setText(dateFormatter.format(monavis.getDate_ajout()));
        tvComment.setText(monavis.getCommentaire());
    }

}
