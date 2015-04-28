package org.esperanto_france.samopiniuloj;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.esperanto_france.samopiniuloj.modelo.Lando;

import java.util.ArrayList;

public class AlighiActivity extends Activity {

    Button btnLogin;

    EditText inputEnirnomo;
    EditText inputPasvorto;
    EditText inputRetadreso;
    TextView eraroEnirnomo;
    TextView eraroPasvorto;
    TextView eraroRetadreso;

    ProgressBar loginProgress;

    Spinner landojSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alighi);

        inputEnirnomo = (EditText) findViewById(R.id.enirnomo);
        inputPasvorto = (EditText) findViewById(R.id.pasvorto);
        inputRetadreso = (EditText) findViewById(R.id.retadreso);
        eraroEnirnomo = (TextView) findViewById(R.id.eraro_enirnomo);
        eraroPasvorto = (TextView) findViewById(R.id.eraro_pasvorto);
        eraroRetadreso = (TextView) findViewById(R.id.eraro_retadreso);

        landojSpinner = (Spinner) findViewById(R.id.landoj_spinner);

        ArrayList alCountry = new ArrayList();
        alCountry.add(new Lando("FR", "Francio"));
        alCountry.add(new Lando("PL", "Pollando"));
        alCountry.add(new Lando("US", "Usono"));

        KeyValueSpinner adapter = new KeyValueSpinner(AlighiActivity.this, alCountry);
        landojSpinner.setAdapter(adapter);


    }
}
