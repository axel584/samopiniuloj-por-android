package org.esperanto_france.samopiniuloj;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.gson.Gson;

import org.esperanto_france.samopiniuloj.dao.LudantoDao;
import org.esperanto_france.samopiniuloj.dao.ProponoDao;
import org.esperanto_france.samopiniuloj.dao.VortoDao;
import org.esperanto_france.samopiniuloj.modelo.Ludanto;
import org.esperanto_france.samopiniuloj.modelo.NovajVortojGson;
import org.esperanto_france.samopiniuloj.modelo.Propono;
import org.esperanto_france.samopiniuloj.modelo.RezultojGson;
import org.esperanto_france.samopiniuloj.modelo.Vorto;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class RezultojFragment extends Fragment {


    private FragmentTabHost tabHost;

    private ImageButton bLudintoj = null;
    private ImageButton bVortoj = null;

    int tago;
    int monato;
    int jaro;

    Vorto vorto;
    VortoDao vortoDao;
    ProponoDao proponoDao;
    LudantoDao ludantoDao;



    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_rezultoj, container, false);


        bLudintoj = (ImageButton) getActivity().findViewById(R.id.button_ludintoj);
        bVortoj = (ImageButton) getActivity().findViewById(R.id.button_vortoj);

        // affichage des onglets :
        Fragment tabMiaRezultoFragment = new TabMiaRezultoFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.rezultoj_frame, tabMiaRezultoFragment).commit();

        // calcul de la date pour récupérer les résultats
        Calendar c=new GregorianCalendar();
        c.add(Calendar.DATE, -1);
        tago = c.get(Calendar.DAY_OF_MONTH);
        monato = c.get(Calendar.MONTH)+1; // ils sont trop con, ils commencent à numéroter les mois à 0 jusqu'à 11..
        jaro = c.get(Calendar.YEAR);
        vortoDao = new VortoDao(getActivity().getApplicationContext());
        vortoDao.open();
        Log.i("RezultojFragment","tago : "+tago+":"+monato+":"+jaro);
        vorto = vortoDao.getVortoPerTago(tago,monato,jaro);
        proponoDao = new ProponoDao(getActivity().getApplicationContext());
        proponoDao.open();
        ludantoDao = new LudantoDao(getActivity().getApplicationContext());
        ludantoDao.open();
        // Récupère les infos de l'utilisateur
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("SamAgordo", 0); // 0 - for private mode
        Integer uzantoId = pref.getInt("uzanto_id",0);
        //Log.i("RezultojFragment","vorto"+vorto);
        //Log.i("RezultojFragment","vorto id "+vorto.getId());
        //Log.i("RezultojFragment","uzanto id "+uzantoId);
        //boolean hasProponojn = proponoDao.hasRezultojn(vorto.getId(),uzantoId);
        //if (!hasProponojn) {
            String requete = "http://samopiniuloj.esperanto-jeunes.org/ws/ws_getRezulto.php?tago=" + tago + "&monato=" + monato + "&jaro=" + jaro;
            new GetProponojAsyncTask(RezultojFragment.this).execute(requete);
        //}




        return rootView;
    }

    public static String GET(String urlWebservice){
        InputStream inputStream;
        String result = "";
        try {

            URL url = new URL(urlWebservice);
            URLConnection urlConnection = url.openConnection();
            inputStream = new BufferedInputStream(urlConnection.getInputStream());

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    private class GetProponojAsyncTask extends AsyncTask<String, Void, RezultojGson> {

        ProgressDialog progressDialog;
        private RezultojFragment rezultojFragment;


        private GetProponojAsyncTask(RezultojFragment rezultojFragment) {
            this.rezultojFragment = rezultojFragment;
        }

        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Konektiĝas...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected RezultojGson doInBackground(String... urls) {

            // On recupere le résultat du web service et on le renvoit
            String rezulto = GET(urls[0]);
            Log.i("RezultojFragmetn","respondo du WS "+rezulto);
            Gson gson = new Gson();
            RezultojGson rezultojGson = gson.fromJson(rezulto,RezultojGson.class);

            // si on a des erreurs, on retourne le résultat
            if ("eraro".equals(rezultojGson.getRespondo())) {
                return rezultojGson;
            }


            // Si c'est ok, on stocke en base
            for (Ludanto ludanto : rezultojGson.getLudantoj()) {
                Log.i("RezultojFragment","Kromnomo : "+ludanto.getKromnomo());
                // Une fois télécharger, on s'occupe de la base de données
               // long resInsertVorto = vortoDao.insertVorto(vorto);
                ludantoDao.insertLudanto(ludanto);
            }
            for (Propono propono : rezultojGson.getProponoj()) {
                Log.i("RezultojFragment","Propono : "+propono.getPropono());
                proponoDao.insertPropono(propono);
                // Une fois télécharger, on s'occupe de la base de données
                // long resInsertVorto = vortoDao.insertVorto(vorto);
            }
            //vortoDao.close();
            //return novajVortojGson;
            return rezultojGson;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(RezultojGson rezultojGson) {
            // termine le sablier
            progressDialog.cancel();
            // traite la réponse

            if ("eraro".equals(rezultojGson.getRespondo())) {
                Toast.makeText(getActivity().getApplicationContext(), rezultojGson.getKialo(), Toast.LENGTH_LONG);
            } else {

/*                for (Vorto vorto : novajVortojGson.getVortoj()) {
                    if (vorto.getTago()==this.ludiFragment.tago && vorto.getMonato()==this.ludiFragment.monato && vorto.getJaro()==this.ludiFragment.jaro) {
                        this.ludiFragment.populateNovajVortoj(vorto);
                    }
                }*/

            }

        }
    }

}
