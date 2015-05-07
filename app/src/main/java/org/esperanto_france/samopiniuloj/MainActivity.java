package org.esperanto_france.samopiniuloj;

import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    // truc ajouté pour le menu hamburger
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] navMenuTitles;


    private Button bEniri = null;
    private Button bAlighi = null;
    private Button bAkceptejo = null;
    private Button bLudi = null;
    private Button bRezultoj = null;
    private Button bLudintoj = null;
    private Button bVortoj = null;
    private Button bKontakto = null;

    private TextView textSurtitre=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ajout toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // ajout pour le menu hamburger
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTitle = mDrawerTitle = getTitle();
        mDrawerTitle = "Menuo";

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


       // mDrawerList = (ListView) findViewById(R.id.nav_list);
       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.nav_drawer_items));
        //mDrawerList.setAdapter(adapter);


        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close)
        {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                toolbar.setTitle(mTitle);
                invalidateOptionsMenu();
                syncState();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                toolbar.setTitle(mDrawerTitle);
                invalidateOptionsMenu();
                syncState();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle); // c'est pour changer le menu hamburger en fleche
        // fin des trucs pour le menu hamburger


        bEniri = (Button) findViewById(R.id.button_enirejo);
        bAlighi = (Button) findViewById(R.id.button_alighilo);
        bAkceptejo = (Button) findViewById(R.id.button_akceptejo);
        bLudi = (Button) findViewById(R.id.button_ludo);

        bRezultoj = (Button) findViewById(R.id.button_rezultoj);
        bLudintoj = (Button) findViewById(R.id.button_ludintoj);
        bVortoj = (Button) findViewById(R.id.button_vortoj);
        bKontakto = (Button) findViewById(R.id.button_kontakto);

        textSurtitre = (TextView) findViewById(R.id.text_surtitre);

         // Récupère les informations de connection (id et nom) daans SharedPreferences
        SharedPreferences pref = getApplicationContext().getSharedPreferences("SamAgordo", 0); // 0 - for private mode
        Integer uzantoId = pref.getInt("uzanto_id",0);
        String uzantoNomo = pref.getString("uzanto_nomo","");

        if (uzantoId!=0) {
            textSurtitre.setText("Bonvenon "+uzantoNomo);
        }


        bEniri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Intent eniriActivity = new Intent(MainActivity.this, EniriActivity.class);

                // Puis on lance l'intent !
                startActivity(eniriActivity);
            }
        });

        bAlighi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Intent alighiActivity = new Intent(MainActivity.this, AlighiActivity.class);

                // Puis on lance l'intent !
                startActivity(alighiActivity);
            }
        });

        bAkceptejo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Intent akceptejoActivity = new Intent(MainActivity.this, AkceptejoActivity.class);

                // Puis on lance l'intent !
                startActivity(akceptejoActivity);
            }
        });

        bLudi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Intent ludiActivity = new Intent(MainActivity.this, LudiActivity.class);

                // Puis on lance l'intent !
                startActivity(ludiActivity);
            }
        });

        bRezultoj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Intent rezultojActivity = new Intent(MainActivity.this, RezultojActivity.class);

                // Puis on lance l'intent !
                startActivity(rezultojActivity);
            }
        });

        bLudintoj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Intent ludintojActivity = new Intent(MainActivity.this, LudintojActivity.class);

                // Puis on lance l'intent !
                startActivity(ludintojActivity);
            }
        });

        bVortoj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Intent vortojActivity = new Intent(MainActivity.this, VortojActivity.class);

                // Puis on lance l'intent !
                startActivity(vortojActivity);
            }
        });

        bKontakto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Intent kontaktoActivity = new Intent(MainActivity.this, KontaktoActivity.class);

                // Puis on lance l'intent !
                startActivity(kontaktoActivity);
            }
        });

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // This to ensure the navigation icon is displayed as
        // burger instead of arrow.
        // Call syncState() from your Activity's onPostCreate
        // to synchronize the indicator with the state of the
        // linked DrawerLayout after onRestoreInstanceState
        // has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // This method should always be called by your Activity's
        // onConfigurationChanged method.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        // This handle among other things open & close the drawer
        // when the navigation icon(burger/arrow) is clicked on.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle other action bar items...
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
