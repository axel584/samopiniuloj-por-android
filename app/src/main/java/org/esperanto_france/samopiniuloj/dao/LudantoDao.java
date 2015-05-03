package org.esperanto_france.samopiniuloj.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.esperanto_france.samopiniuloj.Datumbazo;
import org.esperanto_france.samopiniuloj.modelo.Ludanto;
import org.esperanto_france.samopiniuloj.modelo.Propono;

public class LudantoDao {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "sam.db";

    private static final String TABLE_LUDANTOJ = "sam_ludantoj";
    private static final String COL_ID = "id";
    private static final String COL_KROMNOMO = "kromnomo";
    private static final String COL_RETADRESO = "retadreso";
    private static final String COL_LANDO = "lando";


    private SQLiteDatabase bdd;

    private Datumbazo maBaseSQLite;

    public LudantoDao(Context context) {
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

    public long insertLudanto(Ludanto ludanto) {
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_KROMNOMO, ludanto.getKromnomo());
        values.put(COL_RETADRESO, ludanto.getRetadreso());
        values.put(COL_LANDO, ludanto.getLando());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_LUDANTOJ, null, values);
    }

    public Ludanto getLudanto(int id) {
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query(TABLE_LUDANTOJ, new String[]{COL_ID, COL_KROMNOMO, COL_RETADRESO, COL_LANDO}, COL_ID+ " = " + id , null, null, null, null);
        return cursorToLudanto(c);
    }

    private Ludanto cursorToLudanto(Cursor c) {
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un livre
        Ludanto ludanto = new Ludanto();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        ludanto.setId(c.getInt(0));
        ludanto.setKromnomo(c.getString(1));
        ludanto.setRetadreso(c.getString(2));
        ludanto.setLando(c.getString(3));

        //On ferme le cursor
        c.close();

        //On retourne le livre
        return ludanto;
    }

}
