package be.heh.homeband.activities.avisRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import be.heh.homeband.R;
import be.heh.homeband.activities.searchgroup.SearchGroupHolder;
import be.heh.homeband.entities.Avis;
import be.heh.homeband.entities.Groupe;

public class AvisAdapter extends RecyclerView.Adapter<AvisHolder> {
    List<Avis> list;

    //ajouter un constructeur prenant en entrée une liste
    public AvisAdapter(List<Avis> list) {
        this.list = list;
    }

    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public AvisHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_listavis_card,viewGroup,false);
        return new AvisHolder(view);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(AvisHolder avisHolder, int position) {
        Avis myObject = list.get(position);
        avisHolder.bind(myObject);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
