package org.esperanto_france.samopiniuloj;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import org.esperanto_france.samopiniuloj.dao.ProponoDao;
import org.esperanto_france.samopiniuloj.dao.VortoDao;
import org.esperanto_france.samopiniuloj.modelo.Vorto;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class RezultojFragment extends Fragment {

    private ImageButton bLudintoj = null;
    private ImageButton bVortoj = null;

    int tago;
    int monato;
    int jaro;

    Vorto vorto;
    VortoDao vortoDao;
    ProponoDao proponoDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_rezultoj, container, false);


        bLudintoj = (ImageButton) getActivity().findViewById(R.id.button_ludintoj);
        bVortoj = (ImageButton) getActivity().findViewById(R.id.button_vortoj);

        Calendar c=new GregorianCalendar();
        c.add(Calendar.DATE, -1);
        tago = c.get(Calendar.DAY_OF_MONTH);
        monato = c.get(Calendar.MONTH)+1; // ils sont trop con, ils commencent à numéroter les mois à 0 jusqu'à 11..
        jaro = c.get(Calendar.YEAR);
        vortoDao = new VortoDao(getActivity().getApplicationContext());
        vortoDao.open();
        vorto = vortoDao.getVortoPerTago(tago,monato,jaro);
        proponoDao = new ProponoDao(getActivity().getApplicationContext());
        proponoDao.open();
        // Récupère les infos de l'utilisateur
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("SamAgordo", 0); // 0 - for private mode
        Integer uzantoId = pref.getInt("uzanto_id",0);
        boolean hasProponojn = proponoDao.hasRezultojn(vorto.getId(),uzantoId);
        if (!hasProponojn) {
            // TODO : télécharger les résultats avec le webservice
        }




        return rootView;
    }


}
