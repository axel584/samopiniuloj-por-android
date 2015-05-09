package org.esperanto_france.samopiniuloj;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.esperanto_france.samopiniuloj.dao.ProponoDao;
import org.esperanto_france.samopiniuloj.dao.VortoDao;
import org.esperanto_france.samopiniuloj.modelo.LudiGson;
import org.esperanto_france.samopiniuloj.modelo.NovajVortojGson;
import org.esperanto_france.samopiniuloj.modelo.Propono;
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
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.List;


public class LudiActivity extends ActionBarActivity {

    TextView textBonvonon;
    VortoDao vortoDao;
    ProponoDao proponoDao;
    TextView tagaVorto;
    ImageView tagaBildo;
    Button sendu;

    int tago;
    int monato;
    int jaro;

    // Proponoj :
    EditText prop1;
    EditText prop2;
    EditText prop3;
    EditText prop4;
    EditText prop5;
    EditText prop6;
    EditText prop7;
    EditText prop8;
    // Coches vertes :
    ImageView done1;
    ImageView done2;
    ImageView done3;
    ImageView done4;
    ImageView done5;
    ImageView done6;
    ImageView done7;
    ImageView done8;


    Vorto vorto;

    Integer uzantoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ludi);

        // Ajout toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Calendar c = Calendar.getInstance();
        tago = c.get(Calendar.DAY_OF_MONTH);
        monato = c.get(Calendar.MONTH)+1; // ils sont trop con, ils commencent à numéroter les mois à 0 jusqu'à 11..
        jaro = c.get(Calendar.YEAR);

        // on récupère les objets de l'interface
        textBonvonon = (TextView) findViewById(R.id.bonvenon_ludando);
        tagaVorto = (TextView) findViewById(R.id.taga_vorto);
        tagaBildo = (ImageView) findViewById(R.id.img_taga_vorto);
        sendu = (Button) findViewById(R.id.btn_sendu);

        prop1 = (EditText) findViewById(R.id.propono_1);
        prop2 = (EditText) findViewById(R.id.propono_2);
        prop3 = (EditText) findViewById(R.id.propono_3);
        prop4 = (EditText) findViewById(R.id.propono_4);
        prop5 = (EditText) findViewById(R.id.propono_5);
        prop6 = (EditText) findViewById(R.id.propono_6);
        prop7 = (EditText) findViewById(R.id.propono_7);
        prop8 = (EditText) findViewById(R.id.propono_8);
        done1 = (ImageView) findViewById(R.id.done_1);
        done2 = (ImageView) findViewById(R.id.done_2);
        done3 = (ImageView) findViewById(R.id.done_3);
        done4 = (ImageView) findViewById(R.id.done_4);
        done5 = (ImageView) findViewById(R.id.done_5);
        done6 = (ImageView) findViewById(R.id.done_6);
        done7 = (ImageView) findViewById(R.id.done_7);
        done8 = (ImageView) findViewById(R.id.done_8);



        // Récupère les infos de l'utilisateur
        SharedPreferences pref = getApplicationContext().getSharedPreferences("SamAgordo", 0); // 0 - for private mode
        uzantoId = pref.getInt("uzanto_id",0);
        String uzantoNomo = pref.getString("uzanto_nomo","");
        if (uzantoId==0) {
            // Aucun utilisateur, on renvoit sur la page eniri
            Intent eniriActivity = new Intent(LudiActivity.this, EniriActivity.class);
            startActivity(eniriActivity);
        } else {
            textBonvonon.setText("Bonvenon "+uzantoNomo+" !");
        }


        // On récupère le mot du jour dans la base
        vortoDao = new VortoDao(this);
        vortoDao.open();
        vorto = vortoDao.getVortoPerTago(tago,monato,jaro);
        if (vorto==null) {
            new NovajVortojAsyncTask(this).execute("http://samopiniuloj.esperanto-jeunes.org/ws/ws_getNovajVortoj.php?tago=" + tago + "&monato=" + monato+"&jaro="+jaro);
        } else {
            this.populateNovajVortoj(vorto);

        }

        proponoDao = new ProponoDao(this);
        proponoDao.open();
        Log.i("","vorto id : "+vorto.getId().toString());
        Log.i("","uzanto id : "+uzantoId.toString());
        List<Propono> endatumbazaProponoj = proponoDao.getProponoj(vorto.getId(),uzantoId);
        if (endatumbazaProponoj!=null) {
            for (Propono propono : endatumbazaProponoj) {
                switch (propono.getVico()) {
                    case 1: prop1.setText(propono.getPropono());
                            done1.setVisibility(ImageView.VISIBLE);
                        break;
                    case 2: prop2.setText(propono.getPropono());
                        done2.setVisibility(ImageView.VISIBLE);
                        break;
                    case 3: prop3.setText(propono.getPropono());
                        done3.setVisibility(ImageView.VISIBLE);
                        break;
                    case 4: prop4.setText(propono.getPropono());
                        done4.setVisibility(ImageView.VISIBLE);
                        break;
                    case 5: prop5.setText(propono.getPropono());
                        done5.setVisibility(ImageView.VISIBLE);
                        break;
                    case 6: prop6.setText(propono.getPropono());
                        done6.setVisibility(ImageView.VISIBLE);
                        break;
                    case 7: prop7.setText(propono.getPropono());
                        done7.setVisibility(ImageView.VISIBLE);
                        break;
                    case 8: prop8.setText(propono.getPropono());
                        done8.setVisibility(ImageView.VISIBLE);
                        break;
                    default : Log.e("LudiActivity","Information inconnue en base : "+propono.getVico());
                            break;

                }
            }
        }

        // gestion du bouton d'envoie des propositions
        sendu.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                    String propono1 = prop1.getText().toString();
                    String propono2 = prop2.getText().toString();
                    String propono3 = prop3.getText().toString();
                    String propono4 = prop4.getText().toString();
                    String propono5 = prop5.getText().toString();
                    String propono6 = prop6.getText().toString();
                    String propono7 = prop7.getText().toString();
                    String propono8 = prop8.getText().toString();
                    String requete = "http://samopiniuloj.esperanto-jeunes.org/ws/ws_ludo.php?ludanto_id="+uzantoId+"&vorto_id="+vorto.getId()+"&prop[0]="+propono1+"&prop[1]="+propono2+"&prop[2]="+propono3+"&prop[3]="+propono4+"&prop[4]="+propono5+"&prop[5]="+propono6+"&prop[6]="+propono7+"&prop[7]="+propono8;
                    new LudiAsyncTask(LudiActivity.this).execute(requete);
                }
        });


    }

    // méthode appelée après avoir téléchargé les images ou si on trouve une image du cours
    public void populateNovajVortoj(Vorto vorto) {
        tagaVorto.setText(vorto.getVorto().replaceAll("<rad>", "<u>").replaceAll("</rad>","</u>"));
        Bitmap bitmap = decodeFile(new File(getCacheDir()+vorto.getDosiero()));
        tagaBildo.setImageBitmap(bitmap);
    }

    // méthode appelée après avoir envoyé les propositions jouées
    public void populateLudi(LudiGson ludiGson) {
        Log.i("LudiActivity","ici on va s'occuper de mettre les coches vertes");
        for (Integer i : ludiGson.getRegistritaj()) {
            Log.i("LudiActivity",i.toString());
            switch (i) {
                case 0: done1.setVisibility(ImageView.VISIBLE);
                    break;
                case 1: done2.setVisibility(ImageView.VISIBLE);
                    break;
                case 2: done3.setVisibility(ImageView.VISIBLE);
                    break;
                case 3: done4.setVisibility(ImageView.VISIBLE);
                    break;
                case 4: done5.setVisibility(ImageView.VISIBLE);
                    break;
                case 5: done6.setVisibility(ImageView.VISIBLE);
                    break;
                case 6: done7.setVisibility(ImageView.VISIBLE);
                    break;
                case 7: done8.setVisibility(ImageView.VISIBLE);
                    break;
                default : Log.e("LudiActivity", " numéro inconu de registrita :" + String.valueOf(i));
                    break;
            }
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

    private class NovajVortojAsyncTask extends AsyncTask<String, Void, NovajVortojGson> {

        ProgressDialog progressDialog;
        private LudiActivity ludiActivity;

        private NovajVortojAsyncTask(LudiActivity ludiActivity) {
            this.ludiActivity = ludiActivity;
        }

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
                    int count;
                    while ((count = input.read(data)) != -1) {
                        // allow canceling with back button
                        if (isCancelled()) {
                            input.close();
                            novajVortojGson.setRespondo("eraro");
                            novajVortojGson.setKialo("Nuligita");
                            return novajVortojGson;
                        }
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
                Log.i("LudiActivity","resultat de l'insertion : "+resInsertVorto);
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
            } else {

                for (Vorto vorto : novajVortojGson.getVortoj()) {
                    if (vorto.getTago()==this.ludiActivity.tago && vorto.getMonato()==this.ludiActivity.monato && vorto.getJaro()==this.ludiActivity.jaro) {
                        this.ludiActivity.populateNovajVortoj(vorto);
                    }
                }

            }

        }
    }


    // Accès au webservice Ludi :
    private class LudiAsyncTask extends AsyncTask<String, Void, LudiGson> {

        ProgressDialog progressDialog;
        private LudiActivity ludiActivity;

        private LudiAsyncTask(LudiActivity ludiActivity) {
            this.ludiActivity = ludiActivity;
        }

        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(
                    LudiActivity.this);
            progressDialog.setMessage("Konektiĝas...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected LudiGson doInBackground(String... urls) {

            // On recupere le résultat du web service et on le renvoit
            String rezulto = GET(urls[0]);
            Gson gson = new Gson();
            LudiGson ludiGson = gson.fromJson(rezulto,LudiGson.class);
            // On sauvegarde en base
            int i = 0 ; // vico
            for (String propono : ludiGson.getProponoj()) {
                i++;
                if (!"".equals(propono) && propono!=null) {
                    Propono p = new Propono();
                    p.setPropono(propono);
                    p.setLudanto_id(ludiGson.getLudanto_id());
                    p.setVorto_id(ludiGson.getVorto_id());
                    p.setVico(i);
                    proponoDao.insertPropono(p);
                }
            }

            return ludiGson;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(LudiGson ludiGson) {
            // termine le sablier
            progressDialog.cancel();
            // traite la réponse

            if ("eraro".equals(ludiGson.getRespondo())) {
                // TODO : faut regarder si le problème vient de l'ensemble de la réponse ou juste d'une des propositions
                Toast.makeText(getApplicationContext(), ludiGson.getKialo(), Toast.LENGTH_LONG).show();
            } else {
                this.ludiActivity.populateLudi(ludiGson);

            }

        }
    }



}
