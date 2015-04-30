package org.esperanto_france.samopiniuloj;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.esperanto_france.samopiniuloj.dao.VortoDao;
import org.esperanto_france.samopiniuloj.modelo.Vorto;

import java.util.Calendar;
import java.util.Date;


public class LudiActivity extends Activity {

    TextView textBonvonon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ludi);

        Calendar c = Calendar.getInstance();
        int tago = c.get(Calendar.DAY_OF_MONTH);
        int monato = c.get(Calendar.MONTH);
        int jaro = c.get(Calendar.YEAR);

        Log.i("LudiActivity","dans le log");
        Log.i("LudiActivity", String.valueOf(c.get(Calendar.DAY_OF_MONTH)));
        Log.i("LudiActivity",String.valueOf(c.get(Calendar.MONTH)));

        // Récupère les infos de l'utilisateur
        SharedPreferences pref = getApplicationContext().getSharedPreferences("SamAgordo", 0); // 0 - for private mode
        Integer uzantoId = pref.getInt("uzanto_id",0);
        String uzantoNomo = pref.getString("uzanto_nomo","");
        textBonvonon = (TextView) findViewById(R.id.bonvenon_ludando);
        if (uzantoId!=0) {
            textBonvonon.setText("Bonvenon "+uzantoNomo+" !"+String.valueOf(tago)+"/"+String.valueOf(monato)+"/"+String.valueOf(jaro));
        }

        // On récupère le mot du jour dans la base
        VortoDao vortoDao = new VortoDao(this);
        vortoDao.open();
        Vorto vorto = vortoDao.getVortoPerTago(tago,monato,jaro);

        if (vorto==null) {
            textBonvonon.setText("mot inconnu pour "+String.valueOf(tago)+"/"+String.valueOf(monato)+"/"+String.valueOf(jaro));
            // TODO : faire l'appel au webservice
        }


    }



}
