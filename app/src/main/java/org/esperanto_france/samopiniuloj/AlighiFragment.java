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
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import org.esperanto_france.samopiniuloj.modelo.AlighiGson;
import org.esperanto_france.samopiniuloj.modelo.Lando;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class AlighiFragment extends Fragment {

    Button btnLogin;
    Button btnEniri;

    EditText inputEnirnomo;
    EditText inputPasvorto;
    EditText inputRetadreso;
    TextView eraroEnirnomo;
    TextView eraroPasvorto;
    TextView eraroRetadreso;
    TextView eraroLando;

    Spinner landojSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_alighi, container, false);


        inputEnirnomo = (EditText) rootView.findViewById(R.id.enirnomo);
        inputPasvorto = (EditText) rootView.findViewById(R.id.pasvorto);
        inputRetadreso = (EditText) rootView.findViewById(R.id.retadreso);
        eraroEnirnomo = (TextView) rootView.findViewById(R.id.eraro_enirnomo);
        eraroPasvorto = (TextView) rootView.findViewById(R.id.eraro_pasvorto);
        eraroRetadreso = (TextView) rootView.findViewById(R.id.eraro_retadreso);
        eraroLando = (TextView) rootView.findViewById(R.id.eraro_lando);

        landojSpinner = (Spinner) rootView.findViewById(R.id.landoj_spinner);

        ArrayList<Lando> alCountry = new ArrayList<>();
        alCountry.add(new Lando("","-- Elektu landon --"));
        alCountry.add(new Lando("AD","Andoro"));
        alCountry.add(new Lando("AE","Unuiĝintaj Arabaj Emirlandoj"));
        alCountry.add(new Lando("AF","Afganujo"));
        alCountry.add(new Lando("AG","Antigvo-Barbudo"));
        alCountry.add(new Lando("AI","Angvilo"));
        alCountry.add(new Lando("AL","Albanujo"));
        alCountry.add(new Lando("AM","Armenujo"));
        alCountry.add(new Lando("AN","Nederlandaj Antiloj"));
        alCountry.add(new Lando("AO","Angolo"));
        alCountry.add(new Lando("AQ","Antarkto"));
        alCountry.add(new Lando("AR","Argentino"));
        alCountry.add(new Lando("AS","Usona Samoo"));
        alCountry.add(new Lando("AT","Aŭstrujo"));
        alCountry.add(new Lando("AU","Aŭstralio"));
        alCountry.add(new Lando("AW","Arubo"));
        alCountry.add(new Lando("AX","Alando"));
        alCountry.add(new Lando("AZ","Azerbajĝano"));
        alCountry.add(new Lando("BA","Bosnujo kaj Hercegovino"));
        alCountry.add(new Lando("BB","Barbado"));
        alCountry.add(new Lando("BD","Bangladeŝo"));
        alCountry.add(new Lando("BE","Belgujo"));
        alCountry.add(new Lando("BF","Burkino"));
        alCountry.add(new Lando("BG","Bulgarujo"));
        alCountry.add(new Lando("BH","Barejno"));
        alCountry.add(new Lando("BI","Burundo"));
        alCountry.add(new Lando("BJ","Benino"));
        alCountry.add(new Lando("BM","Bermudoj"));
        alCountry.add(new Lando("BN","Brunejo"));
        alCountry.add(new Lando("BO","Bolivio"));
        alCountry.add(new Lando("BR","Brazilo"));
        alCountry.add(new Lando("BS","Bahamoj"));
        alCountry.add(new Lando("BT","Butano"));
        alCountry.add(new Lando("BV","Buvetinsulo"));
        alCountry.add(new Lando("BW","Bocvano"));
        alCountry.add(new Lando("BY","Belorusujo"));
        alCountry.add(new Lando("BZ","Belizo"));
        alCountry.add(new Lando("CA","Kanado"));
        alCountry.add(new Lando("CC","Kokosinsuloj"));
        alCountry.add(new Lando("CD","Kongo (Kinŝasa)"));
        alCountry.add(new Lando("CF","Centr-Afrika Respubliko"));
        alCountry.add(new Lando("CG","Kongo (Brazavila)"));
        alCountry.add(new Lando("CH","Svislando"));
        alCountry.add(new Lando("CI","Ebur-Bordo"));
        alCountry.add(new Lando("CK","Kukinsuloj"));
        alCountry.add(new Lando("CL","Ĉilio"));
        alCountry.add(new Lando("CM","Kameruno"));
        alCountry.add(new Lando("CN","Ĉinujo"));
        alCountry.add(new Lando("CO","Kolombio"));
        alCountry.add(new Lando("CR","Kostariko"));
        alCountry.add(new Lando("CS","Serbujo kaj Montenegro"));
        alCountry.add(new Lando("CU","Kubo"));
        alCountry.add(new Lando("CV","Kabo-Verdo"));
        alCountry.add(new Lando("CX","Kristnaskinsulo"));
        alCountry.add(new Lando("CY","Kipro"));
        alCountry.add(new Lando("CZ","Ĉeĥujo"));
        alCountry.add(new Lando("DE","Germanujo"));
        alCountry.add(new Lando("DJ","Ĝibutijo"));
        alCountry.add(new Lando("DK","Danio"));
        alCountry.add(new Lando("DM","Dominiko"));
        alCountry.add(new Lando("DO","Domingo"));
        alCountry.add(new Lando("DZ","Alĝerio"));
        alCountry.add(new Lando("EC","Ekvadoro"));
        alCountry.add(new Lando("EE","Estonujo"));
        alCountry.add(new Lando("EG","Egiptujo"));
        alCountry.add(new Lando("EH","Okcidenta Saharo"));
        alCountry.add(new Lando("ER","Eritreo"));
        alCountry.add(new Lando("ES","Hispanujo"));
        alCountry.add(new Lando("ET","Etiopujo"));
        alCountry.add(new Lando("FI","Finnlando"));
        alCountry.add(new Lando("FJ","Fiĝioj"));
        alCountry.add(new Lando("FK","Falklandoj"));
        alCountry.add(new Lando("FM","Mikronezio"));
        alCountry.add(new Lando("FO","Ferooj"));
        alCountry.add(new Lando("FR","Francujo"));
        alCountry.add(new Lando("GA","Gabono"));
        alCountry.add(new Lando("GB","Britujo"));
        alCountry.add(new Lando("GD","Grenado"));
        alCountry.add(new Lando("GE","Kartvelujo"));
        alCountry.add(new Lando("GF","Franca Gviano"));
        alCountry.add(new Lando("GH","Ganao"));
        alCountry.add(new Lando("GI","Ĝibraltaro"));
        alCountry.add(new Lando("GL","Gronlando"));
        alCountry.add(new Lando("GM","Gambio"));
        alCountry.add(new Lando("GN","Gvineo"));
        alCountry.add(new Lando("GP","Gvadelupo"));
        alCountry.add(new Lando("GQ","Ekvatora Gvineo"));
        alCountry.add(new Lando("GR","Grekujo"));
        alCountry.add(new Lando("GS","Sud-Georgio kaj Sud-Sandviĉinsuloj"));
        alCountry.add(new Lando("GT","Gvatemalo"));
        alCountry.add(new Lando("GU","Gvamo"));
        alCountry.add(new Lando("GW","Gvineo Bisaŭa"));
        alCountry.add(new Lando("GY","Gujano"));
        alCountry.add(new Lando("HK","Honkongo"));
        alCountry.add(new Lando("HM","Herda kaj Maldonaldaj Insuloj"));
        alCountry.add(new Lando("HN","Honduro"));
        alCountry.add(new Lando("HR","Kroatujo"));
        alCountry.add(new Lando("HT","Haitio"));
        alCountry.add(new Lando("HU","Hungarujo"));
        alCountry.add(new Lando("ID","Indonezio"));
        alCountry.add(new Lando("IE","Irlando"));
        alCountry.add(new Lando("IL","Israelo"));
        alCountry.add(new Lando("IN","Barato"));
        alCountry.add(new Lando("IO","Brita Hindoceana Teritorio"));
        alCountry.add(new Lando("IQ","Irako"));
        alCountry.add(new Lando("IR","Irano"));
        alCountry.add(new Lando("IS","Islando"));
        alCountry.add(new Lando("IT","Italujo"));
        alCountry.add(new Lando("JM","Jamajko"));
        alCountry.add(new Lando("JO","Jordanio"));
        alCountry.add(new Lando("JP","Japanujo"));
        alCountry.add(new Lando("KE","Kenjo"));
        alCountry.add(new Lando("KG","Kirgizujo"));
        alCountry.add(new Lando("KH","Kamboĝo"));
        alCountry.add(new Lando("KI","Kiribato"));
        alCountry.add(new Lando("KM","Komoroj"));
        alCountry.add(new Lando("KN","Sent-Kristofo kaj Neviso"));
        alCountry.add(new Lando("KP","Nord-Koreujo"));
        alCountry.add(new Lando("KR","Sud-Koreujo"));
        alCountry.add(new Lando("KW","Kuvajto"));
        alCountry.add(new Lando("KY","Kejmanoj"));
        alCountry.add(new Lando("KZ","Kazaĥujo"));
        alCountry.add(new Lando("LA","Laoso"));
        alCountry.add(new Lando("LB","Libano"));
        alCountry.add(new Lando("LC","Sent-Lucio"));
        alCountry.add(new Lando("LI","Liĥtenŝtejno"));
        alCountry.add(new Lando("LK","Sri-Lanko"));
        alCountry.add(new Lando("LR","Liberio"));
        alCountry.add(new Lando("LS","Lesoto"));
        alCountry.add(new Lando("LT","Litovujo"));
        alCountry.add(new Lando("LU","Luksemburgo"));
        alCountry.add(new Lando("LV","Latvujo"));
        alCountry.add(new Lando("LY","Libio"));
        alCountry.add(new Lando("MA","Maroko"));
        alCountry.add(new Lando("MC","Monako"));
        alCountry.add(new Lando("MD","Moldavujo"));
        alCountry.add(new Lando("MG","Madagaskaro"));
        alCountry.add(new Lando("MH","Marŝaloj"));
        alCountry.add(new Lando("MK","Makedonujo"));
        alCountry.add(new Lando("ML","Malio"));
        alCountry.add(new Lando("MM","Birmo"));
        alCountry.add(new Lando("MN","Mongolujo"));
        alCountry.add(new Lando("MO","Makao"));
        alCountry.add(new Lando("MP","Nord-Marianoj"));
        alCountry.add(new Lando("MQ","Martiniko"));
        alCountry.add(new Lando("MS","Moncerato"));
        alCountry.add(new Lando("MT","Malto"));
        alCountry.add(new Lando("MU","Maŭricio"));
        alCountry.add(new Lando("MV","Maldivoj"));
        alCountry.add(new Lando("MW","Malavio"));
        alCountry.add(new Lando("MX","Meksiko"));
        alCountry.add(new Lando("MY","Malajzio"));
        alCountry.add(new Lando("MZ","Mozambiko"));
        alCountry.add(new Lando("NA","Namibio"));
        alCountry.add(new Lando("NC","Nov-Kaledonio"));
        alCountry.add(new Lando("NE","Niĝero"));
        alCountry.add(new Lando("NF","Nordolkinsulo"));
        alCountry.add(new Lando("NG","Niĝerio"));
        alCountry.add(new Lando("NI","Nikaragvo"));
        alCountry.add(new Lando("NL","Nederlando"));
        alCountry.add(new Lando("NO","Norvegujo"));
        alCountry.add(new Lando("NP","Nepalo"));
        alCountry.add(new Lando("NR","Nauro"));
        alCountry.add(new Lando("NU","Niuo"));
        alCountry.add(new Lando("NZ","Nov-Zelando"));
        alCountry.add(new Lando("OM","Omano"));
        alCountry.add(new Lando("PA","Panamo"));
        alCountry.add(new Lando("PE","Peruo"));
        alCountry.add(new Lando("PF","Franca Polinezio"));
        alCountry.add(new Lando("PG","Papuo-Nov-Guineo"));
        alCountry.add(new Lando("PH","Filipinoj"));
        alCountry.add(new Lando("PK","Pakistano"));
        alCountry.add(new Lando("PL","Pollando"));
        alCountry.add(new Lando("PM","Sent-Piero kaj Mikelono"));
        alCountry.add(new Lando("PN","Pitkarna Insulo"));
        alCountry.add(new Lando("PR","Puerto Riko"));
        alCountry.add(new Lando("PS","Palestinaj Teritorioj"));
        alCountry.add(new Lando("PT","Portugalujo"));
        alCountry.add(new Lando("PW","Palaŭo"));
        alCountry.add(new Lando("PY","Paragvajo"));
        alCountry.add(new Lando("QA","Kataro"));
        alCountry.add(new Lando("RE","Reunio"));
        alCountry.add(new Lando("RO","Rumanujo"));
        alCountry.add(new Lando("RU","Rusujo"));
        alCountry.add(new Lando("RW","Ruando"));
        alCountry.add(new Lando("SA","Saŭda Arabujo"));
        alCountry.add(new Lando("SB","Salomonoj"));
        alCountry.add(new Lando("SC","Sejŝeloj"));
        alCountry.add(new Lando("SD","Sudano"));
        alCountry.add(new Lando("SE","Svedujo"));
        alCountry.add(new Lando("SG","Singapuro"));
        alCountry.add(new Lando("SH","Sent-Heleno"));
        alCountry.add(new Lando("SI","Slovenujo"));
        alCountry.add(new Lando("SJ","Svalbardo kaj Jan-Majen-insulo"));
        alCountry.add(new Lando("SK","Slovakujo"));
        alCountry.add(new Lando("SM","San-Marino"));
        alCountry.add(new Lando("SN","Senegalo"));
        alCountry.add(new Lando("SO","Somalujo"));
        alCountry.add(new Lando("SR","Surinamo"));
        alCountry.add(new Lando("ST","Sao-Tomeo kaj Principeo"));
        alCountry.add(new Lando("SV","Salvadoro"));
        alCountry.add(new Lando("SY","Sirio"));
        alCountry.add(new Lando("SZ","Svazilando"));
        alCountry.add(new Lando("TC","Turkoj kaj Kajkoj"));
        alCountry.add(new Lando("TD","Ĉado"));
        alCountry.add(new Lando("TF","Francaj Sudaj Teritorioj"));
        alCountry.add(new Lando("TG","Togo"));
        alCountry.add(new Lando("TH","Tajlando"));
        alCountry.add(new Lando("TJ","Taĝikujo"));
        alCountry.add(new Lando("TK","Tokelao"));
        alCountry.add(new Lando("TM","Turkmenujo"));
        alCountry.add(new Lando("TN","Tunizio"));
        alCountry.add(new Lando("TO","Tongo"));
        alCountry.add(new Lando("TP","Orienta Timoro"));
        alCountry.add(new Lando("TR","Turkujo"));
        alCountry.add(new Lando("TT","Trinidado kaj Tobago"));
        alCountry.add(new Lando("TV","Tuvalo"));
        alCountry.add(new Lando("TW","Tajvano"));
        alCountry.add(new Lando("TZ","Tanzanio"));
        alCountry.add(new Lando("UA","Ukrainujo"));
        alCountry.add(new Lando("UG","Ugando"));
        alCountry.add(new Lando("UM","Usonaj malgrandaj insuloj"));
        alCountry.add(new Lando("US","Usono"));
        alCountry.add(new Lando("UY","Urugvajo"));
        alCountry.add(new Lando("UZ","Uzbekujo"));
        alCountry.add(new Lando("VA","Vatikano"));
        alCountry.add(new Lando("VC","Sent-Vincento kaj la Grenadinoj"));
        alCountry.add(new Lando("VE","Venezuelo"));
        alCountry.add(new Lando("VG","Britaj Virgulininsuloj"));
        alCountry.add(new Lando("VI","Usonaj Virgulininsuloj"));
        alCountry.add(new Lando("VN","Vjetnamujo"));
        alCountry.add(new Lando("VU","Vanuatuo"));
        alCountry.add(new Lando("WF","Valiso kaj Futuno"));
        alCountry.add(new Lando("WS","Samoo"));
        alCountry.add(new Lando("YE","Jemeno"));
        alCountry.add(new Lando("YT","Majoto"));
        alCountry.add(new Lando("ZA","Sud Afriko"));
        alCountry.add(new Lando("ZM","Zambio"));
        alCountry.add(new Lando("ZW","Zimbabvo"));

        KeyValueSpinner adapter = new KeyValueSpinner(rootView.getContext(), alCountry);
        landojSpinner.setAdapter(adapter);

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

        inputRetadreso.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                eraroRetadreso.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnLogin = (Button) rootView.findViewById(R.id.btn_aligxu);
        btnEniri = (Button) rootView.findViewById(R.id.btn_eniru);

        btnLogin.setOnClickListener(new View.OnClickListener() {


            public void onClick(View view) {
                String sEnirnomo = inputEnirnomo.getText().toString();
                String sPasvorto = inputPasvorto.getText().toString();
                String sRetadreso = inputRetadreso.getText().toString();
                String sLando = ((Lando)landojSpinner.getSelectedItem()).getKodo();
                //Log.i("AlighiActivity","pays : "+idLando);

                if (sEnirnomo.equals("")) {
                    eraroEnirnomo.setText(R.string.eraro_enirnomo_mankas);
                }
                if (sPasvorto.equals("")) {
                    eraroPasvorto.setText(R.string.eraro_pasvorto_mankas);
                }
                if (sRetadreso.equals("")){
                    eraroRetadreso.setText(R.string.eraro_retadreso_mankas);
                }
                if (sLando.equals("")){
                    eraroLando.setText(R.string.eraro_lando_mankas);
                }

                if (!sEnirnomo.equals("") && !sPasvorto.equals("") && !sRetadreso.equals("") && !sLando.equals("")) {
                    new HttpAsyncTask().execute("http://samopiniuloj.esperanto-jeunes.org/ws/ws_alighilo.php?nomo=" + sEnirnomo + "&pasvorto=" + sPasvorto + "&retadreso=" + sRetadreso + "&lando=" + sLando);
                }
            }


        });

        btnEniri.setOnClickListener(new View.OnClickListener() {


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
        String line;
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

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
            AlighiGson aligiGson = gson.fromJson(result,AlighiGson.class);
            if ("eraro".equals(aligiGson.getRespondo())) {
                if (aligiGson.getEraro_id()==1) {
                    eraroEnirnomo.setText(R.string.eraro_enirnomo_uzita);
                }
                if (aligiGson.getEraro_id()==2) { // TODO : mettre des constantes pour les codes d'erreur
                    eraroRetadreso.setText(R.string.eraro_retadreso_uzita);
                }
            }
            if ("ok".equals(aligiGson.getRespondo())) {
                // on enregistre dans sharedpreference l'id du joueur
                SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("SamAgordo", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("uzanto_id", aligiGson.getId());
                editor.putString("uzanto_nomo", aligiGson.getNomo());
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
