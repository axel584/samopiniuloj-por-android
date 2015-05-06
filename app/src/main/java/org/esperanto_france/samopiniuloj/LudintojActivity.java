package org.esperanto_france.samopiniuloj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;


public class LudintojActivity extends ActionBarActivity {

    private ImageButton bRezultoj = null;
    private ImageButton bVortoj = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ludintoj);

        // Ajout toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bRezultoj = (ImageButton) findViewById(R.id.button_rezultoj);
        bVortoj = (ImageButton) findViewById(R.id.button_vortoj);

        bRezultoj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Intent rezultojActivity = new Intent(LudintojActivity.this, RezultojActivity.class);

                // Puis on lance l'intent !
                startActivity(rezultojActivity);
            }
        });

        bVortoj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Intent vortojActivity = new Intent(LudintojActivity.this, VortojActivity.class);

                // Puis on lance l'intent !
                startActivity(vortojActivity);
            }
        });
    }


}