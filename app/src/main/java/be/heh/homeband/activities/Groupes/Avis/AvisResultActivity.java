package be.heh.homeband.activities.Groupes.Avis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import be.heh.homeband.R;
import be.heh.homeband.app.RecyclerTouchListener;
import be.heh.homeband.entities.Avis;
import be.heh.homeband.entities.Groupe;

public class AvisResultActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private List<Avis> avis = new ArrayList<Avis>();
    Groupe groupe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listgroup);

        setTitle("Avis");

        avis = (ArrayList<Avis>) getIntent().getSerializableExtra("comments");
        groupe = (Groupe) getIntent().getSerializableExtra("group");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewGroup);

        if (avis.isEmpty()) {
            setContentView(R.layout.activity_list_group_empty);
        }
        else {
        }

        //définit l'agencement des cellules, ici de façon verticale, comme une ListView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //pour adapter en grille comme une RecyclerView, avec 2 cellules par ligne
        //recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        recyclerView.setAdapter(new AvisAdapter(avis));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp(){

        this.finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.avis_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent (getApplicationContext(),AddAvis.class);
                intent.putExtra("group",groupe);
                startActivity(intent);


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
