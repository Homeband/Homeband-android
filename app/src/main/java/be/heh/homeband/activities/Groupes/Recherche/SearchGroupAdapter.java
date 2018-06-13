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

    // Constructeur
    public SearchGroupAdapter(List<Groupe> list) { this.list = list; }

    // Definit le  layout à utiliser et créer le ViewHolder (vide)
    // cette fonction permet de créer les viewHolder
    @Override
    public SearchGroupHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_listgroup_card,viewGroup,false);
        return new SearchGroupHolder(view);
    }

    // Donne les paramètres au ViewHolder pour se remplir
    @Override
    public void onBindViewHolder(SearchGroupHolder searchGroupHolder, int position) {
        Groupe myObject = list.get(position);
        searchGroupHolder.bind(myObject);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Groupe> list){
        this.list = list;
    }
}
