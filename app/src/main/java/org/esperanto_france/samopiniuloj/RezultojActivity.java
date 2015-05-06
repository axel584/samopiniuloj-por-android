package org.esperanto_france.samopiniuloj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;


public class RezultojActivity extends ActionBarActivity {

    private ImageButton bLudintoj = null;
    private ImageButton bVortoj = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezultoj);

        // Ajout toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bLudintoj = (ImageButton) findViewById(R.id.button_ludintoj);
        bVortoj = (ImageButton) findViewById(R.id.button_vortoj);

        bLudintoj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Intent ludintojActivity = new Intent(RezultojActivity.this, LudintojActivity.class);

                // Puis on lance l'intent !
                startActivity(ludintojActivity);
            }
        });

        bVortoj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Intent vortojActivity = new Intent(RezultojActivity.this, VortojActivity.class);

                // Puis on lance l'intent !
                startActivity(vortojActivity);
            }
        });
    }


}
