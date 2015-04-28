package org.esperanto_france.samopiniuloj;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AlighiActivity extends Activity {

    Button btnLogin;

    EditText inputEnirnomo;
    EditText inputPasvorto;
    EditText inputRetadreso;
    TextView eraroEnirnomo;
    TextView eraroPasvorto;
    TextView eraroRetadreso;

    ProgressBar loginProgress;

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



    }
}
