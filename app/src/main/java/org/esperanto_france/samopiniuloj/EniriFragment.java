package org.esperanto_france.samopiniuloj;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.esperanto_france.samopiniuloj.modelo.EniriGson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class EniriFragment  extends Fragment {

    Button btnLogin;
    Button btnAlighi;

    EditText inputEnirnomo;
    EditText inputPasvorto;
    TextView eraroEnirnomo;
    TextView eraroPasvorto;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_eniri, container, false);

        inputEnirnomo = (EditText) rootView.findViewById(R.id.enirnomo);
        inputPasvorto = (EditText) rootView.findViewById(R.id.pasvorto);
        eraroEnirnomo = (TextView) rootView.findViewById(R.id.eraro_enirnomo);
        eraroPasvorto = (TextView) rootView.findViewById(R.id.eraro_pasvorto);

        // retire le correcteur orthographique
        inputEnirnomo.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        inputPasvorto.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

        inputEnirnomo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                eraroEnirnomo.setText("");
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        inputPasvorto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                eraroPasvorto.setText("");
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        btnLogin = (Button) rootView.findViewById(R.id.btn_eniru);
        btnAlighi = (Button) rootView.findViewById(R.id.btn_aligxu);

        btnLogin.setOnClickListener(new View.OnClickListener() {


            public void onClick(View view) {
                String sEnirnomo = inputEnirnomo.getText().toString().trim();
                String sPasvorto = inputPasvorto.getText().toString().trim();
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

        btnAlighi.setOnClickListener(new View.OnClickListener() {


            public void onClick(View view) {
                // Renvoie sur le fragment "alighi"
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, new AlighiFragment())
                        .commit();
            }
        });

        return rootView;
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

//    public boolean isConnected(){
//        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        if (networkInfo != null && networkInfo.isConnected())
//            return true;
//        else
//            return false;
//    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Konektiĝas...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            // termine le sablier
            progressDialog.cancel();
            // traite la réponse
            Gson gson = new Gson();
            EniriGson eniriGson = gson.fromJson(result,EniriGson.class);
            if (eniriGson==null) {
                Toast toast = Toast.makeText(getActivity().getApplicationContext(),"Ne eblas konektiĝi",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
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
                SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("SamAgordo", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("uzanto_id", eniriGson.getId());
                editor.putString("uzanto_nomo", eniriGson.getNomo());
                editor.commit();
                // Renvoie sur le fragment "ludi"
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, new LudiFragment())
                        .commit();

            }

        }
    }

}
