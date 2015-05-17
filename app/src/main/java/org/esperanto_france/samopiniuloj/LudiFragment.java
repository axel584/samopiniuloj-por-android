package org.esperanto_france.samopiniuloj;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Selection;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class LudiFragment extends Fragment {

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
    // Croix rouge
    ImageView malbone1;
    ImageView malbone2;
    ImageView malbone3;
    ImageView malbone4;
    ImageView malbone5;
    ImageView malbone6;
    ImageView malbone7;
    ImageView malbone8;


    Vorto vorto;

    Integer uzantoId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_ludi, container, false);


        Calendar c = Calendar.getInstance();
        tago = c.get(Calendar.DAY_OF_MONTH);
        monato = c.get(Calendar.MONTH)+1; // ils sont trop con, ils commencent à numéroter les mois à 0 jusqu'à 11..
        jaro = c.get(Calendar.YEAR);

        // on récupère les objets de l'interface
        textBonvonon = (TextView) rootView.findViewById(R.id.bonvenon_ludando);
        tagaVorto = (TextView) rootView.findViewById(R.id.taga_vorto);
        tagaBildo = (ImageView) rootView.findViewById(R.id.img_taga_vorto);
        sendu = (Button) rootView.findViewById(R.id.btn_sendu);

        prop1 = (EditText) rootView.findViewById(R.id.propono_1);
        prop2 = (EditText) rootView.findViewById(R.id.propono_2);
        prop3 = (EditText)rootView. findViewById(R.id.propono_3);
        prop4 = (EditText) rootView.findViewById(R.id.propono_4);
        prop5 = (EditText) rootView.findViewById(R.id.propono_5);
        prop6 = (EditText) rootView.findViewById(R.id.propono_6);
        prop7 = (EditText) rootView.findViewById(R.id.propono_7);
        prop8 = (EditText) rootView.findViewById(R.id.propono_8);
        done1 = (ImageView) rootView.findViewById(R.id.done_1);
        done2 = (ImageView) rootView.findViewById(R.id.done_2);
        done3 = (ImageView) rootView.findViewById(R.id.done_3);
        done4 = (ImageView) rootView.findViewById(R.id.done_4);
        done5 = (ImageView) rootView.findViewById(R.id.done_5);
        done6 = (ImageView) rootView.findViewById(R.id.done_6);
        done7 = (ImageView) rootView.findViewById(R.id.done_7);
        done8 = (ImageView) rootView.findViewById(R.id.done_8);
        malbone1 = (ImageView) rootView.findViewById(R.id.malbone_1);
        malbone2 = (ImageView) rootView.findViewById(R.id.malbone_2);
        malbone3 = (ImageView) rootView.findViewById(R.id.malbone_3);
        malbone4 = (ImageView) rootView.findViewById(R.id.malbone_4);
        malbone5 = (ImageView) rootView.findViewById(R.id.malbone_5);
        malbone6 = (ImageView) rootView.findViewById(R.id.malbone_6);
        malbone7 = (ImageView) rootView.findViewById(R.id.malbone_7);
        malbone8 = (ImageView) rootView.findViewById(R.id.malbone_8);



        // Récupère les infos de l'utilisateur
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("SamAgordo", 0); // 0 - for private mode
        uzantoId = pref.getInt("uzanto_id",0);
        String uzantoNomo = pref.getString("uzanto_nomo","");
        if (uzantoId==0) {
            // Renvoie sur le fragment "alighi"
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new EniriFragment())
                    .commit();
        } else {
            textBonvonon.setText("Bonvenon "+uzantoNomo+" !");
        }


        // On récupère le mot du jour dans la base
        vortoDao = new VortoDao(getActivity().getApplicationContext());
        vortoDao.open();
        vorto = vortoDao.getVortoPerTago(tago,monato,jaro);
        if (vorto==null) {
            new NovajVortojAsyncTask(this).execute("http://samopiniuloj.esperanto-jeunes.org/ws/ws_getNovajVortoj.php?tago=" + tago + "&monato=" + monato+"&jaro="+jaro);
        } else {
            this.populateNovajVortoj(vorto);
        }



        // on fait en sorte que si on change le contenu des propositions, la coche verte disparaisse
        prop1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                done1.setVisibility(ImageView.INVISIBLE);
                malbone1.setVisibility(ImageView.GONE);
                String propositionUtf8 = TextUtilsEo.x2utf(charSequence.toString());
                if (!propositionUtf8.equals(charSequence.toString())) {
                    prop1.setText(propositionUtf8);
                    Selection.setSelection(prop1.getText(), propositionUtf8.length()); // on positionne le curseur à la fin de la chaine
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        prop2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                done2.setVisibility(ImageView.INVISIBLE);
                malbone2.setVisibility(ImageView.GONE);
                String propositionUtf8 = TextUtilsEo.x2utf(charSequence.toString());
                if (!propositionUtf8.equals(charSequence.toString())) {
                    prop2.setText(propositionUtf8);
                    Selection.setSelection(prop2.getText(), propositionUtf8.length()); // on positionne le curseur à la fin de la chaine
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        prop3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                done3.setVisibility(ImageView.INVISIBLE);
                malbone3.setVisibility(ImageView.GONE);
                String propositionUtf8 = TextUtilsEo.x2utf(charSequence.toString());
                if (!propositionUtf8.equals(charSequence.toString())) {
                    prop3.setText(propositionUtf8);
                    Selection.setSelection(prop3.getText(), propositionUtf8.length()); // on positionne le curseur à la fin de la chaine
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        prop4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                done4.setVisibility(ImageView.INVISIBLE);
                malbone4.setVisibility(ImageView.GONE);
                String propositionUtf8 = TextUtilsEo.x2utf(charSequence.toString());
                if (!propositionUtf8.equals(charSequence.toString())) {
                    prop4.setText(propositionUtf8);
                    Selection.setSelection(prop4.getText(), propositionUtf8.length()); // on positionne le curseur à la fin de la chaine
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        prop5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                done5.setVisibility(ImageView.INVISIBLE);
                malbone5.setVisibility(ImageView.GONE);
                String propositionUtf8 = TextUtilsEo.x2utf(charSequence.toString());
                if (!propositionUtf8.equals(charSequence.toString())) {
                    prop5.setText(propositionUtf8);
                    Selection.setSelection(prop5.getText(), propositionUtf8.length()); // on positionne le curseur à la fin de la chaine
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        prop6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                done6.setVisibility(ImageView.INVISIBLE);
                malbone6.setVisibility(ImageView.GONE);
                String propositionUtf8 = TextUtilsEo.x2utf(charSequence.toString());
                if (!propositionUtf8.equals(charSequence.toString())) {
                    prop6.setText(propositionUtf8);
                    Selection.setSelection(prop6.getText(), propositionUtf8.length()); // on positionne le curseur à la fin de la chaine
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        prop7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                done7.setVisibility(ImageView.INVISIBLE);
                malbone7.setVisibility(ImageView.GONE);
                String propositionUtf8 = TextUtilsEo.x2utf(charSequence.toString());
                if (!propositionUtf8.equals(charSequence.toString())) {
                    prop7.setText(propositionUtf8);
                    Selection.setSelection(prop7.getText(), propositionUtf8.length()); // on positionne le curseur à la fin de la chaine
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        prop8.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                done8.setVisibility(ImageView.INVISIBLE);
                malbone8.setVisibility(ImageView.GONE);
                String propositionUtf8 = TextUtilsEo.x2utf(charSequence.toString());
                if (!propositionUtf8.equals(charSequence.toString())) {
                    prop8.setText(propositionUtf8);
                    Selection.setSelection(prop8.getText(), propositionUtf8.length()); // on positionne le curseur à la fin de la chaine
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        // fait les conversions avec les x
        //setOnFocusChangeListener(prop1);
//        setOnFocusChangeListener(prop2);
//        setOnFocusChangeListener(prop3);
//        setOnFocusChangeListener(prop4);
//        setOnFocusChangeListener(prop5);
//        setOnFocusChangeListener(prop6);
//        setOnFocusChangeListener(prop7);
//        setOnFocusChangeListener(prop8);

        // retire le correcteur orthographique
        prop1.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        prop2.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        prop3.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        prop4.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        prop5.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        prop6.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        prop7.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        prop8.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

        // gestion du bouton d'envoie des propositions
        sendu.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String propono1 = TextUtilsEo.utf2x(prop1.getText().toString());
                String propono2 = TextUtilsEo.utf2x(prop2.getText().toString());
                String propono3 = TextUtilsEo.utf2x(prop3.getText().toString());
                String propono4 = TextUtilsEo.utf2x(prop4.getText().toString());
                String propono5 = TextUtilsEo.utf2x(prop5.getText().toString());
                String propono6 = TextUtilsEo.utf2x(prop6.getText().toString());
                String propono7 = TextUtilsEo.utf2x(prop7.getText().toString());
                String propono8 = TextUtilsEo.utf2x(prop8.getText().toString());
                String requete = "http://samopiniuloj.esperanto-jeunes.org/ws/ws_ludo.php?ludanto_id=" + uzantoId + "&vorto_id=" + vorto.getId() + "&prop[0]=" + propono1 + "&prop[1]=" + propono2 + "&prop[2]=" + propono3 + "&prop[3]=" + propono4 + "&prop[4]=" + propono5 + "&prop[5]=" + propono6 + "&prop[6]=" + propono7 + "&prop[7]=" + propono8;
                new LudiAsyncTask(LudiFragment.this).execute(requete);
            }
        });

        return rootView;
    }

    // méthode appelée après avoir téléchargé les images ou si on trouve une image du cours
    public void populateNovajVortoj(Vorto vorto) {
        Log.i("LudiFragment","vorto Populate Novaj Vortoj : date : "+vorto.getTago()+"/"+vorto.getMonato()+"/"+vorto.getJaro());
        tagaVorto.setText(Html.fromHtml(vorto.getVorto()));
        Bitmap bitmap = decodeFile(new File(getActivity().getCacheDir()+vorto.getDosiero()));
        tagaBildo.setImageBitmap(bitmap);
        this.vorto = vorto;
        // récupère les propositions
        proponoDao = new ProponoDao(getActivity().getApplicationContext());
        proponoDao.open();
        List<Propono> endatumbazaProponoj = proponoDao.getProponoj(vorto.getId(),uzantoId);
        if (endatumbazaProponoj!=null) {
            for (Propono propono : endatumbazaProponoj) {
                switch (propono.getVico()) {
                    case 1: prop1.setText(TextUtilsEo.x2utf(propono.getPropono()));
                        done1.setVisibility(ImageView.VISIBLE);
                        malbone1.setVisibility(ImageView.GONE);
                        break;
                    case 2: prop2.setText(TextUtilsEo.x2utf(propono.getPropono()));
                        done2.setVisibility(ImageView.VISIBLE);
                        malbone2.setVisibility(ImageView.GONE);
                        break;
                    case 3: prop3.setText(TextUtilsEo.x2utf(propono.getPropono()));
                        done3.setVisibility(ImageView.VISIBLE);
                        malbone3.setVisibility(ImageView.GONE);
                        break;
                    case 4: prop4.setText(TextUtilsEo.x2utf(propono.getPropono()));
                        done4.setVisibility(ImageView.VISIBLE);
                        malbone4.setVisibility(ImageView.GONE);
                        break;
                    case 5: prop5.setText(TextUtilsEo.x2utf(propono.getPropono()));
                        done5.setVisibility(ImageView.VISIBLE);
                        malbone5.setVisibility(ImageView.GONE);
                        break;
                    case 6: prop6.setText(TextUtilsEo.x2utf(propono.getPropono()));
                        done6.setVisibility(ImageView.VISIBLE);
                        malbone6.setVisibility(ImageView.GONE);
                        break;
                    case 7: prop7.setText(TextUtilsEo.x2utf(propono.getPropono()));
                        done7.setVisibility(ImageView.VISIBLE);
                        malbone7.setVisibility(ImageView.GONE);
                        break;
                    case 8: prop8.setText(TextUtilsEo.x2utf(propono.getPropono()));
                        done8.setVisibility(ImageView.VISIBLE);
                        break;
                    default : Log.e("LudiActivity","Information inconnue en base : "+propono.getVico());
                        break;

                }
            }
        }
    }

    // méthode appelée après avoir envoyé les propositions jouées
    public void populateLudi(LudiGson ludiGson) {
        for (Integer i : ludiGson.getRegistritaj()) {
            switch (i) {
                case 0: done1.setVisibility(ImageView.VISIBLE);
                    malbone1.setVisibility(ImageView.GONE);
                    break;
                case 1: done2.setVisibility(ImageView.VISIBLE);
                    malbone2.setVisibility(ImageView.GONE);
                    break;
                case 2: done3.setVisibility(ImageView.VISIBLE);
                    malbone3.setVisibility(ImageView.GONE);
                    break;
                case 3: done4.setVisibility(ImageView.VISIBLE);
                    malbone4.setVisibility(ImageView.GONE);
                    break;
                case 4: done5.setVisibility(ImageView.VISIBLE);
                    malbone5.setVisibility(ImageView.GONE);
                    break;
                case 5: done6.setVisibility(ImageView.VISIBLE);
                    malbone6.setVisibility(ImageView.GONE);
                    break;
                case 6: done7.setVisibility(ImageView.VISIBLE);
                    malbone7.setVisibility(ImageView.GONE);
                    break;
                case 7: done8.setVisibility(ImageView.VISIBLE);
                    malbone8.setVisibility(ImageView.GONE);
                    break;
                default : Log.e("LudiActivity", " numéro inconu de registrita :" + String.valueOf(i));
                    break;
            }
        }
        // On verifie si certaines valeurs sont en erreur
        if (ludiGson.getEraraj_proponoj()!=null) {
            for (int eraraProponoId : ludiGson.getEraraj_proponoj()) {
                switch (eraraProponoId) {
                    case (0):
                        done1.setVisibility(View.GONE);
                        malbone1.setVisibility(View.VISIBLE);
                        break;
                    case (1):
                        done2.setVisibility(View.GONE);
                        malbone2.setVisibility(View.VISIBLE);
                        break;
                    case (2):
                        done3.setVisibility(View.GONE);
                        malbone3.setVisibility(View.VISIBLE);
                        break;
                    case (3):
                        done4.setVisibility(View.GONE);
                        malbone4.setVisibility(View.VISIBLE);
                        break;
                    case (4):
                        done5.setVisibility(View.GONE);
                        malbone5.setVisibility(View.VISIBLE);
                        break;
                    case (5):
                        done6.setVisibility(View.GONE);
                        malbone6.setVisibility(View.VISIBLE);
                        break;
                    case (6):
                        done7.setVisibility(View.GONE);
                        malbone7.setVisibility(View.VISIBLE);
                        break;
                    case (7):
                        done8.setVisibility(View.GONE);
                        malbone8.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }
        // on fait des toasts avec les erreurs trouvées
        if (ludiGson.getKialoj()!=null) {
            for (String erreur : ludiGson.getKialoj()) {
                Toast.makeText(getActivity().getApplicationContext(), erreur, Toast.LENGTH_LONG).show();
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


    private class NovajVortojAsyncTask extends AsyncTask<String, Void, NovajVortojGson> {

        ProgressDialog progressDialog;
        private LudiFragment ludiFragment;

        private NovajVortojAsyncTask(LudiFragment ludiFragment) {
            this.ludiFragment = ludiFragment;
        }

        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(getActivity());
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
            File f = new File (getActivity().getCacheDir()+"/dosiero/");
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
                // on met le souligné :
                vorto.setVorto(vorto.getVorto().replaceAll("<rad>", "<u>").replaceAll("</rad>", "</u>"));
                // On telecharge l'image :
                InputStream input = null;
                OutputStream output = null;
                HttpURLConnection connection = null;
                try {
                    URL url = new URL("http://samopiniuloj.esperanto-jeunes.org"+vorto.getDosiero());
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
                    output = new FileOutputStream(getActivity().getCacheDir()+vorto.getDosiero());

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
                Toast.makeText(getActivity().getApplicationContext(), novajVortojGson.getKialo(), Toast.LENGTH_LONG);
            } else {

                for (Vorto vorto : novajVortojGson.getVortoj()) {
                    if (vorto.getTago()==this.ludiFragment.tago && vorto.getMonato()==this.ludiFragment.monato && vorto.getJaro()==this.ludiFragment.jaro) {
                        this.ludiFragment.populateNovajVortoj(vorto);
                    }
                }

            }

        }
    }


    // Accès au webservice Ludi :
    private class LudiAsyncTask extends AsyncTask<String, Void, LudiGson> {

        ProgressDialog progressDialog;
        private LudiFragment ludiFragment;

        private LudiAsyncTask(LudiFragment ludiFragment) {
            this.ludiFragment = ludiFragment;
        }

        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Konektiĝas...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected LudiGson doInBackground(String... urls) {

            // On recupere le résultat du web service et on le renvoit
            String rezulto = GET(urls[0]);
            // on verifie si on a un résultat, dans le cas contraire, c'est qu'on n'est pas connecté
            if (rezulto==null || "".equals(rezulto)) {
                LudiGson ludiGson = new LudiGson();
                ludiGson.setRespondo("eraro");
                ludiGson.setKialo("Ne eblas konektiĝi");
                return ludiGson;
            }
            Gson gson = new Gson();
            LudiGson ludiGson = gson.fromJson(rezulto,LudiGson.class);
            // On sauvegarde en base
            int i = 0 ; // vico
            for (String propono : ludiGson.getProponoj()) {
                if (ludiGson.getEraraj_proponoj()!=null) {
                    if (ludiGson.getEraraj_proponoj().length > 0 && Arrays.binarySearch(ludiGson.getEraraj_proponoj(), i) >= 0) { // on a trouvé cette proposition dans la liste des propositions en erreur, on ne sauvegarde pas en base
                        i++; // on incremente pour qu'au prochain tour il ait la valeur suivante
                        continue;
                    }
                }
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
                Toast.makeText(getActivity().getApplicationContext(), ludiGson.getKialo(), Toast.LENGTH_LONG).show();
            } else {
                this.ludiFragment.populateLudi(ludiGson);

            }

        }
    }



}
