package org.esperanto_france.samopiniuloj.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.esperanto_france.samopiniuloj.Datumbazo;
import org.esperanto_france.samopiniuloj.modelo.Propono;
import org.esperanto_france.samopiniuloj.modelo.Vorto;

public class ProponoDao {
    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "sam.db";

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
        maBaseSQLite = new Datumbazo(context, NOM_BDD, null, VERSION_BDD);
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

    public long insertLivre(Propono propono) {
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_LUDANTO, propono.getLudanto_id().intValue());
        values.put(COL_VORTO, propono.getVorto_id().intValue());
        values.put(COL_PROPONO, propono.getPropono());
        values.put(COL_POENTO, propono.getPoento());
        values.put(COL_VICO, propono.getVico());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_PROPONOJ, null, values);
    }


    public Propono getPropono(int vorto,int ludanto,int vico) {
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query(TABLE_PROPONOJ, new String[]{COL_ID, COL_VORTO, COL_LUDANTO, COL_PROPONO, COL_POENTO, COL_VICO}, COL_VORTO+ " = " + vorto + " and "+COL_LUDANTO + "= "+ludanto+" and "+COL_VICO+" = "+vico, null, null, null, null);
        return cursorToPropono(c);
    }

    public Propono getProponoj(int vorto,int ludanto) {
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query(TABLE_PROPONOJ, new String[]{COL_ID, COL_VORTO, COL_LUDANTO, COL_PROPONO, COL_POENTO, COL_VICO}, COL_VORTO+ " = " + vorto + " and "+COL_LUDANTO + "= "+ludanto, null, null, null, null);
        return cursorToPropono(c);
    }

    //Cette méthode permet de convertir un cursor en un livre
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
        propono.setVorto_id(c.getInt(2));
        propono.setPropono(c.getString(3));
        propono.setPoento(c.getInt(4));
        propono.setVico(c.getInt(5));
        //On ferme le cursor
        c.close();

        //On retourne le livre
        return propono;
    }
}
