package be.heh.homeband.activities.BandOnClick;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import be.heh.homeband.R;
import be.heh.homeband.entities.Album;
import be.heh.homeband.entities.Membre;
import be.heh.homeband.entities.Titre;

public class AlbumAdapter extends ArrayAdapter<Titre> {
    private final Context context;
    private final ArrayList<Titre> titresArrayList;
    SimpleDateFormat dateFormatter;

    public AlbumAdapter(Context context, ArrayList<Titre> albumArrayList) {

        super(context, R.layout.list_item_album, albumArrayList);

        this.context = context;
        this.titresArrayList = albumArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        Titre titre = titresArrayList.get(position);
        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.list_item_album, parent, false);

        // 3. Get the two text view from the rowView
        TextView labelView = (TextView) rowView.findViewById(R.id.tvTitreAlbum);



        // 4. Set the text for textView
        labelView.setText(titre.getTitre());

        // 5. retrn rowView
        return rowView;
    }
}
