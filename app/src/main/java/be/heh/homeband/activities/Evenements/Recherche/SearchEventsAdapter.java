package be.heh.homeband.activities.Evenements.Recherche;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import be.heh.homeband.R;
import be.heh.homeband.entities.Evenement;

/**
 * Created on 27-02-18.
 */

public class SearchEventsAdapter extends RecyclerView.Adapter<SearchEventsHolder> {

    List<Evenement> list;

    //ajouter un constructeur prenant en entrée une liste
    public SearchEventsAdapter(List<Evenement> list) {
        this.list = list;
    }

    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public SearchEventsHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_listevents_card,viewGroup,false);
        return new SearchEventsHolder(view);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(SearchEventsHolder searchEventsHolder, int position) {
        Evenement myObject = list.get(position);
        searchEventsHolder.bind(myObject);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
