package org.esperanto_france.samopiniuloj;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class KielLudiFragment extends Fragment {

    Button btnLudi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_kiel_ludi, container, false);

        btnLudi = (Button) rootView.findViewById(R.id.btn_ludu);

        btnLudi.setOnClickListener(new View.OnClickListener() {


            public void onClick(View view) {
                // Renvoie sur le fragment "ludi"
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, new LudiFragment())
                        .commit();
            }
        });

        return rootView;
    }


}
