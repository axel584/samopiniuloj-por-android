package org.esperanto_france.samopiniuloj.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.esperanto_france.samopiniuloj.modelo.Propono;

import java.util.ArrayList;
import java.util.List;

public class ProponoDao {

    private static final String TABLE_PROPONOJ = "sam_proponoj";
    private static final String COL_ID = "id";
    private static final String COL_LUDANTO = "ludanto_id";
    private static final String COL_VORTO = "vorto_id";
    private static final String COL_PROPONO = "propono";
    private static final String COL_POENTO = "poento";
    private static final String COL_VICO = "vico";


    private SQLiteDatabase bdd;

    private Datumbazo maBaseSQLite;

    public ProponoDao(Context context) {
        //On créer la BDD et sa table
        maBaseSQLite = new Datumbazo(context);
    }

    public void open() {
        //on ouvre la BDD en écriture
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close() {
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD() {
        return bdd;
    }

    public long insertPropono(Propono propono) {
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_LUDANTO, propono.getLudanto_id().intValue());
        values.put(COL_VORTO, propono.getVorto_id().intValue());
        values.put(COL_PROPONO, propono.getPropono());
        values.put(COL_POENTO, propono.getPoento());
        values.put(COL_VICO, propono.getVico());
        // On verifie si l'objet est déjà en base :
        Propono jamEnDatumbazo = getPropono(propono.getVorto_id().intValue(),propono.getLudanto_id().intValue(),propono.getVico());
        if (jamEnDatumbazo==null) {
            //on insère l'objet dans la BDD via le ContentValues
            return bdd.insert(TABLE_PROPONOJ, null, values);
        }else {
            return bdd.update(TABLE_PROPONOJ,values,"id="+jamEnDatumbazo.getId().toString(),null);
        }
    }

    /**
     * Kontrolas chu ni jam elshutis (kaj registris en la datumbazon) rezultojn por tiu tago
     * @param vorto
     * @param ludanto
     * @return boolean
     */
    public boolean hasRezultojn(int vorto,int ludanto) {
        Log.i("DAO","vorto : "+vorto);
        Log.i("DAO","ludanto : "+ludanto);
        Cursor c = bdd.query(TABLE_PROPONOJ, new String[]{COL_ID, COL_VORTO, COL_LUDANTO, COL_PROPONO, COL_POENTO, COL_VICO}, COL_VORTO+ " = ? and "+COL_LUDANTO + "!= ?", new String[] { String.valueOf(vorto),String.valueOf(ludanto)}, null, null, null);
        return c.getCount() != 0;
    }

    public Propono getPropono(int vorto,int ludanto,int vico) {
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query(TABLE_PROPONOJ, new String[]{COL_ID, COL_VORTO, COL_LUDANTO, COL_PROPONO, COL_POENTO, COL_VICO}, COL_VORTO+ " = ? and "+COL_LUDANTO + "= ? and "+COL_VICO+" = ?", new String[] { String.valueOf(vorto),String.valueOf(ludanto),String.valueOf(vico) }, null, null, null);
        return cursorToPropono(c);
    }

    public List<Propono> getProponoj(int vorto,int ludanto) {
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query(TABLE_PROPONOJ, new String[]{COL_ID, COL_VORTO, COL_LUDANTO, COL_PROPONO, COL_POENTO, COL_VICO}, COL_VORTO+ " = ? and "+COL_LUDANTO + "= ?", new String[] { String.valueOf(vorto),String.valueOf(ludanto) }, null, null, null);
        return cursorToProponoj(c);
    }

    private Propono cursorToPropono(Cursor c) {
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un livre
        Propono propono = new Propono();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        propono.setId(c.getInt(0));
        propono.setVorto_id(c.getInt(1));
        propono.setLudanto_id(c.getInt(2));
        propono.setPropono(c.getString(3));
        propono.setPoento(c.getInt(4));
        propono.setVico(c.getInt(5));
        //On ferme le cursor
        c.close();

        //On retourne le livre
        return propono;
    }

    private List<Propono> cursorToProponoj(Cursor c) {
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;


        //On créé le tableau de réponses
        List<Propono> proponoj = new ArrayList<Propono>();
        //Sinon on se place sur le premier élément
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            Propono propono = new Propono();
            //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
            propono.setId(c.getInt(0));
            propono.setVorto_id(c.getInt(1));
            propono.setLudanto_id(c.getInt(2));
            propono.setPropono(c.getString(3));
            propono.setPoento(c.getInt(4));
            propono.setVico(c.getInt(5));
            // une fois créé on l'ajoute à la liste résultat
            proponoj.add(propono);
            c.moveToNext();
        }
        c.close();

        //On retourne le tableau de proposition
        return proponoj;
    }

}
