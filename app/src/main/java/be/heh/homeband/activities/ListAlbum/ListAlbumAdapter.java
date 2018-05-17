package be.heh.homeband.activities.ListAlbum;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import be.heh.homeband.R;
import be.heh.homeband.activities.searchgroup.SearchGroupHolder;
import be.heh.homeband.entities.Album;
import be.heh.homeband.entities.Groupe;

public class ListAlbumAdapter extends RecyclerView.Adapter<ListAlbumHolder> {
    List<Album> list;

    //ajouter un constructeur prenant en entrée une liste
    public ListAlbumAdapter(List<Album> list) {
        this.list = list;
    }

    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public ListAlbumHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_listalbum_card,viewGroup,false);
        return new ListAlbumHolder(view);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(ListAlbumHolder listAlbumHolder, int position) {
        Album myObject = list.get(position);
        listAlbumHolder.bind(myObject);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
