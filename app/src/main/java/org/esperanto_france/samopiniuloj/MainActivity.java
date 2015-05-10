package org.esperanto_france.samopiniuloj;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
    private Button bKielLudi = null;

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

        // Set the list's click listener
        mDrawerList = (ListView) findViewById(R.id.nav_list);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener()); // C'est pour gérer les clicks sur le menu

        // fin des trucs pour le menu hamburger

        // Si on est connecté, on lance automatiquement le "ludi" fragment, sinon on lance le "eniri" fragment
        SharedPreferences pref = getApplicationContext().getSharedPreferences("SamAgordo", 0); // 0 - for private mode
        Integer uzantoId = pref.getInt("uzanto_id",0);
        String uzantoNomo = pref.getString("uzanto_nomo","");
        // TODO : placer le nom de l'utilisateur dans le menu
        if (uzantoId==0) {
            Fragment fragment = new EniriFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        } else {
            Fragment fragment = new LudiFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        }



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

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        Log.i("MainActivity","position : "+position);
        Fragment nouveauFragment = new Fragment();
        switch (position){
            case 0 : nouveauFragment = new KielLudiFragment();// kiel ludi
                    break;
            case 1 : nouveauFragment = new LudiFragment();
                    break;
            case 2 :
                    nouveauFragment = new RezultojFragment();
                    break;
            case 3 : nouveauFragment = new KontaktoFragment();
                break;
            case 4 : nouveauFragment = new FoririFragment();
                break;
            default :
                  Log.e("Main Activity ","position inconnu dans le menu hamburger");
                break;

        }
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, nouveauFragment)
                .commit();

    }


}
