package be.heh.homeband.activities.Groupes.Recherche;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import be.heh.homeband.R;
import be.heh.homeband.entities.Groupe;

/**
 * Created on 20-02-18.
 */

public class SearchGroupAdapter extends RecyclerView.Adapter<SearchGroupHolder> {

    List<Groupe> list;

    //ajouter un constructeur prenant en entrée une liste
    public SearchGroupAdapter(List<Groupe> list) {
        this.list = list;
    }

    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public SearchGroupHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_listgroup_card,viewGroup,false);
        return new SearchGroupHolder(view);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(SearchGroupHolder searchGroupHolder, int position) {
        Groupe myObject = list.get(position);
        searchGroupHolder.bind(myObject);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
