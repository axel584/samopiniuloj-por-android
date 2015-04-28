package org.esperanto_france.samopiniuloj;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import org.esperanto_france.samopiniuloj.modelo.Lando;

import java.util.ArrayList;

public class KeyValueSpinner implements SpinnerAdapter {
    Context context;
    ArrayList<Lando> alCountry;

    public KeyValueSpinner(Context context ,ArrayList alCountry){
        this.context =context;
        this.alCountry = alCountry;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return alCountry.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return alCountry.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return 0;
    }
    //Note:-Create this two method getIDFromIndex and getIndexByID
    public String getIDFromIndex(int Index) {
        return alCountry.get(Index).getKodo();
    }

    public int getIndexByID(String ID) {
        for(int i=0;i<alCountry.size();i++) {
            if(alCountry.get(i).getKodo() == ID){
                return i;
            }
        }
        return -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView textview = (TextView) inflater.inflate(android.R.layout.simple_spinner_item, null);
        textview.setText(alCountry.get(position).getNomo());

        return textview;
    }

    @Override
    public int getViewTypeCount() {
        return android.R.layout.simple_spinner_item;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        // TODO Auto-generated method stub

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        // TODO Auto-generated method stub

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView textview = (TextView) inflater.inflate(android.R.layout.simple_spinner_item, null);
        textview.setText(alCountry.get(position).getNomo());

        return textview;
    }
}