package org.esperanto_france.samopiniuloj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static org.esperanto_france.samopiniuloj.R.id.button_alighilo;


public class MainActivity extends Activity {

    private Button bEniri = null;
    private Button bAlighi = null;
    private Button bAkceptejo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bEniri = (Button) findViewById(R.id.button_enirejo);
        bAlighi = (Button) findViewById(R.id.button_alighilo);
        bAkceptejo = (Button) findViewById(R.id.button_akceptejo);

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

    }


}
