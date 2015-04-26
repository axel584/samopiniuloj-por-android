package org.esperanto_france.samopiniuloj;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.esperanto_france.samopiniuloj.modelo.EniriGson;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.util.Log;

import com.google.gson.Gson;

public class EniriActivity extends Activity {

    Button btnLogin;

    EditText inputEnirnomo;
    EditText inputPasvorto;
    TextView eraroEnirnomo;
    TextView eraroPasvorto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eniri);

        inputEnirnomo = (EditText) findViewById(R.id.enirnomo);
        inputPasvorto = (EditText) findViewById(R.id.pasvorto);
        eraroEnirnomo = (TextView) findViewById(R.id.eraro_enirnomo);
        eraroPasvorto = (TextView) findViewById(R.id.eraro_pasvorto);

        inputEnirnomo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                eraroEnirnomo.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        inputPasvorto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                eraroPasvorto.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        btnLogin = (Button) findViewById(R.id.btn_eniru);

        btnLogin.setOnClickListener(new View.OnClickListener() {


            public void onClick(View view) {
                String sEnirnomo = inputEnirnomo.getText().toString();
                String sPasvorto = inputPasvorto.getText().toString();
                if (
                        (!sEnirnomo.equals("")) && (!sPasvorto.equals(""))) {
                    new HttpAsyncTask().execute("http://samopiniuloj.esperanto-jeunes.org/ws/ws_eniri.php?nomo=" + sEnirnomo + "&pasvorto=" + sPasvorto);
                } else if ((!sEnirnomo.equals(""))) {
                    eraroPasvorto.setText(R.string.eraro_pasvorto_mankas);
                } else if ((!sPasvorto.equals(""))) {
                    eraroEnirnomo.setText(R.string.eraro_enirnomo_mankas);
                } else {
                    eraroEnirnomo.setText(R.string.eraro_enirnomo_mankas);
                    eraroPasvorto.setText(R.string.eraro_pasvorto_mankas);

                }
            }


        });

    }

    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

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

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
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
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Gson gson = new Gson();
            EniriGson eniriGson = gson.fromJson(result,EniriGson.class);
            if ("eraro".equals(eniriGson.getRespondo())) {
                if (eniriGson.getEraro_id()==1) {
                    eraroEnirnomo.setText(R.string.eraro_enirnomo_nekonata);
                }
                if (eniriGson.getEraro_id()==2) { // TODO : mettre des constantes pour les codes d'erreur
                    eraroPasvorto.setText(R.string.eraro_pasvorto_malghusta);
                }
            }
            if ("ok".equals(eniriGson.getRespondo())) {
                // on enregistre dans sharedpreference l'id du joueur
                SharedPreferences pref = getApplicationContext().getSharedPreferences("SamAgordo", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("uzanto_id", eniriGson.getId());
                editor.putString("uzanto_nomo", eniriGson.getNomo());
                editor.commit();
                Intent ludiActivity = new Intent(EniriActivity.this, LudiActivity.class);
                startActivity(ludiActivity);
            }

        }
    }

}