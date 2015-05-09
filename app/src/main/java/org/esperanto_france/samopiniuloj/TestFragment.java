package org.esperanto_france.samopiniuloj;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class TestFragment extends Fragment {

    Button bEniri;
    Button bAlighi;
    Button bAkceptejo;
    Button bLudi;
    Button bRezultoj;
    Button bLudintoj;
    Button bVortoj;
    Button bKontakto;
    Button bKielLudi;

    TextView textSurtitre;

    // le fragment pour y acceder
    private final EniriFragment eniriFragment = new EniriFragment();

    public TestFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_test, container, false);

        bEniri = (Button) rootView.findViewById(R.id.button_enirejo);
        bAlighi = (Button) rootView.findViewById(R.id.button_alighilo);
        bAkceptejo = (Button) rootView.findViewById(R.id.button_akceptejo);
        bLudi = (Button) rootView.findViewById(R.id.button_ludo);

        bRezultoj = (Button) rootView.findViewById(R.id.button_rezultoj);
        bLudintoj = (Button) rootView.findViewById(R.id.button_ludintoj);
        bVortoj = (Button) rootView.findViewById(R.id.button_vortoj);
        bKontakto = (Button) rootView.findViewById(R.id.button_kontakto);
        bKielLudi = (Button) rootView.findViewById(R.id.button_kiel_ludi);

        textSurtitre = (TextView) rootView.findViewById(R.id.text_surtitre);

        // Récupère les informations de connection (id et nom) daans SharedPreferences
        SharedPreferences pref = getActivity().getSharedPreferences("SamAgordo", 0); // 0 - for private mode
        Integer uzantoId = pref.getInt("uzanto_id",0);
        String uzantoNomo = pref.getString("uzanto_nomo","");

        if (uzantoId!=0) {
            textSurtitre.setText("Bonvenon "+uzantoNomo);
        }

        return rootView;

    }

    private void showFragment(final Fragment fragment) {
        if (fragment == null) {
            return;
        }

        // Begin a fragment transaction.
        final FragmentManager fm = getFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();
        // We can also animate the changing of fragment.
        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        // Replace current fragment by the new one.
        ft.replace(R.id.content_frame, fragment);
        // Null on the back stack to return on the previous fragment when user
        // press on back button.
        ft.addToBackStack(null);

        // Commit changes.
        ft.commit();
    }

    public void iruAlEnirejo(View v) {
        showFragment(this.eniriFragment);

    }
}
