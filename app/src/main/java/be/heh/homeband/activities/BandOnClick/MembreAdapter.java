package be.heh.homeband.activities.BandOnClick;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import be.heh.homeband.R;
import be.heh.homeband.entities.Membre;

public class MembreAdapter extends ArrayAdapter<Membre> {

    private final Context context;
    private final ArrayList<Membre> membresArrayList;

    public MembreAdapter(Context context, ArrayList<Membre> membresArrayList) {

        super(context, R.layout.list_item_membres, membresArrayList);

        this.context = context;
        this.membresArrayList = membresArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        Membre membre = membresArrayList.get(position);
        // 1. Create inflater 
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.list_item_membres, parent, false);

        // 3. Get the two text view from the rowView
        TextView labelView = (TextView) rowView.findViewById(R.id.tvMembre);
        TextView valueView = (TextView) rowView.findViewById(R.id.tvDateMembre);

        // 4. Set the text for textView 
        labelView.setText(membre.getNom()+" "+membre.getPrenom());
        //valueView.setText(membre.getDate_debut());

        // 5. retrn rowView
        return rowView;
    }
}
