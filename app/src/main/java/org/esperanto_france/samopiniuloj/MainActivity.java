package org.esperanto_france.samopiniuloj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private Button bEniri = null;
    private Button bAlighi = null;
    private Button bAkceptejo = null;
    private Button bLudi = null;
    private Button bRezultoj = null;
    private Button bLudintoj = null;
    private Button bVortoj = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bEniri = (Button) findViewById(R.id.button_enirejo);
        bAlighi = (Button) findViewById(R.id.button_alighilo);
        bAkceptejo = (Button) findViewById(R.id.button_akceptejo);
        bLudi = (Button) findViewById(R.id.button_ludo);
        bRezultoj = (Button) findViewById(R.id.button_rezultoj);
        bLudintoj = (Button) findViewById(R.id.button_ludintoj);


        bEniri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Intent eniriActivity = new Intent(MainActivity.this, EniriActivity.class);

                // Puis on lance l'intent !
                startActivity(eniriActivity);
            }
        });

        bAlighi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Intent alighiActivity = new Intent(MainActivity.this, AlighiActivity.class);

                // Puis on lance l'intent !
                startActivity(alighiActivity);
            }
        });

        bAkceptejo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Intent akceptejoActivity = new Intent(MainActivity.this, AkceptejoActivity.class);

                // Puis on lance l'intent !
                startActivity(akceptejoActivity);
            }
        });

        bLudi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Intent ludiActivity = new Intent(MainActivity.this, LudiActivity.class);

                // Puis on lance l'intent !
                startActivity(ludiActivity);
            }
        });

        bRezultoj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Intent rezultojActivity = new Intent(MainActivity.this, RezultojActivity.class);

                // Puis on lance l'intent !
                startActivity(rezultojActivity);
            }
        });

        bLudintoj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Intent ludintojActivity = new Intent(MainActivity.this, LudintojActivity.class);

                // Puis on lance l'intent !
                startActivity(ludintojActivity);
            }
        });

        bVortoj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Intent vortojActivity = new Intent(MainActivity.this, VortojActivity.class);

                // Puis on lance l'intent !
                startActivity(vortojActivity);
            }
        });

    }


}
