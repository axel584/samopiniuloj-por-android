package org.esperanto_france.samopiniuloj;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class RezultojFragment extends Fragment {

    private ImageButton bLudintoj = null;
    private ImageButton bVortoj = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_rezultoj, container, false);


        bLudintoj = (ImageButton) getActivity().findViewById(R.id.button_ludintoj);
        bVortoj = (ImageButton) getActivity().findViewById(R.id.button_vortoj);


        return rootView;
    }


}
