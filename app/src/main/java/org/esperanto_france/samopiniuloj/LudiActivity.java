package org.esperanto_france.samopiniuloj;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.esperanto_france.samopiniuloj.dao.VortoDao;
import org.esperanto_france.samopiniuloj.modelo.NovajVortojGson;
import org.esperanto_france.samopiniuloj.modelo.Vorto;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;


public class LudiActivity extends Activity {

    TextView textBonvonon;
    VortoDao vortoDao;
    TextView tagaVorto;
    ImageView tagaBildo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ludi);

        Calendar c = Calendar.getInstance();
        int tago = c.get(Calendar.DAY_OF_MONTH);
        int monato = c.get(Calendar.MONTH)+1; // ils sont trop con, ils commencent à numéroter les mois à 0 jusqu'à 11..
        int jaro = c.get(Calendar.YEAR);

        // Récupère les infos de l'utilisateur
        SharedPreferences pref = getApplicationContext().getSharedPreferences("SamAgordo", 0); // 0 - for private mode
        Integer uzantoId = pref.getInt("uzanto_id",0);
        String uzantoNomo = pref.getString("uzanto_nomo","");
        textBonvonon = (TextView) findViewById(R.id.bonvenon_ludando);
        tagaVorto = (TextView) findViewById(R.id.taga_vorto);
        tagaBildo = (ImageView) findViewById(R.id.img_taga_vorto);

        if (uzantoId!=0) {
            textBonvonon.setText("Bonvenon "+uzantoNomo+" !"+String.valueOf(tago)+"/"+String.valueOf(monato)+"/"+String.valueOf(jaro));
        }

        // On récupère le mot du jour dans la base
        vortoDao = new VortoDao(this);
        vortoDao.open();
        Vorto vorto = vortoDao.getVortoPerTago(tago,monato,jaro);
        if (vorto==null) {
            textBonvonon.setText("mot inconnu pour "+String.valueOf(tago)+"/"+String.valueOf(monato)+"/"+String.valueOf(jaro));
            // TODO : faire l'appel au webservice
            new HttpAsyncTask().execute("http://samopiniuloj.esperanto-jeunes.org/ws/ws_getNovajVortoj.php?tago=" + tago + "&monato=" + monato+"&jaro="+jaro);
        } else {
            tagaVorto.setText(vorto.getVorto().replaceAll("<rad>", "<u>").replaceAll("</rad>","</u>"));
            Bitmap bitmap = decodeFile(new File(getCacheDir()+vorto.getDosiero()));
            tagaBildo.setImageBitmap(bitmap);

        }



    }

    private Bitmap decodeFile(File f) {
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
            return BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            Log.e("Error", "Decode File", e);
        }
        return null;
    }

    public static String GET(String urlWebservice){
        InputStream inputStream = null;
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

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, NovajVortojGson> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(
                    LudiActivity.this);
            progressDialog.setMessage("Konektiĝas...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected NovajVortojGson doInBackground(String... urls) {

            // On recupere le résultat du web service et on le renvoit
            String rezulto = GET(urls[0]);
            Gson gson = new Gson();
            NovajVortojGson novajVortojGson = gson.fromJson(rezulto,NovajVortojGson.class);

            // si on a des erreurs, on retourne le résultat
            if ("eraro".equals(novajVortojGson.getRespondo())) {
                return novajVortojGson;
            }
            // on verifie si le repertoire cache+dosiero existe
            File f = new File (getCacheDir()+"/dosiero/");
            if (!f.exists()) {
                boolean mkdir = f.mkdir();
                if (!mkdir) {
                    novajVortojGson.setRespondo("eraro");
                    novajVortojGson.setKialo("Ne eblas krei dosierujon sur via telefonilo...");
                    return novajVortojGson;
                }
            }

            // Si c'est ok, on récupère les images
            for (Vorto vorto : novajVortojGson.getVortoj()) {
                // On telecharge l'image :
                InputStream input = null;
                OutputStream output = null;
                HttpURLConnection connection = null;
                try {
                    URL url = new URL("http://samopiniuloj.esperanto-jeunes.org"+vorto.getDosiero());
                    Log.i("LudiActivity","http://samopiniuloj.esperanto-jeunes.org"+vorto.getDosiero());
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();

                    if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        novajVortojGson.setRespondo("eraro");
                        novajVortojGson.setKialo("Server returned HTTP " + connection.getResponseCode()
                                + " " + connection.getResponseMessage());
                        return novajVortojGson;
                    }
                    // this will be useful to display download percentage
                    // might be -1: server did not report the length
                    int fileLength = connection.getContentLength();
                    // download the file
                    input = connection.getInputStream();
                    output = new FileOutputStream(getCacheDir()+vorto.getDosiero());

                    byte data[] = new byte[4096];
                    long total = 0;
                    int count;
                    while ((count = input.read(data)) != -1) {
                        // allow canceling with back button
                        if (isCancelled()) {
                            input.close();
                            novajVortojGson.setRespondo("eraro");
                            novajVortojGson.setKialo("Nuligita");
                            return novajVortojGson;
                        }
                        total += count;
                        output.write(data, 0, count);
                    }
                } catch (Exception e) {
                    novajVortojGson.setRespondo("eraro");
                    novajVortojGson.setKialo(e.toString());
                    return novajVortojGson;
                } finally {
                    try {
                        if (output != null)
                            output.close();
                        if (input != null)
                            input.close();
                    } catch (IOException ignored) {
                    }

                    if (connection != null)
                        connection.disconnect();
                }
                // Une fois télécharger, on s'occupe de la base de données
                long resInsertVorto = vortoDao.insertVorto(vorto);
                Log.i("LudiActivity","resultat de l'insertion en base : "+resInsertVorto);
                Log.i("LudiActivity","tago  : "+vorto.getTago());
                Log.i("LudiActivity","monato  : "+vorto.getMonato());
                Log.i("LudiActivity","jaro  : "+vorto.getJaro());
            }
            vortoDao.close();
            return novajVortojGson;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(NovajVortojGson novajVortojGson) {
            // termine le sablier
            progressDialog.cancel();
            // traite la réponse

            if ("eraro".equals(novajVortojGson.getRespondo())) {
                Toast.makeText(getApplicationContext(), novajVortojGson.getKialo(), Toast.LENGTH_LONG);
            }

        }
    }
}
