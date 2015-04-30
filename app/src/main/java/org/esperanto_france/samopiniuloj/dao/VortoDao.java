package org.esperanto_france.samopiniuloj.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.esperanto_france.samopiniuloj.Datumbazo;
import org.esperanto_france.samopiniuloj.modelo.Vorto;

public class VortoDao {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "sam.db";

    private static final String TABLE_VORTOJ = "sam_vortoj";
    private static final String COL_ID = "id";
    private static final String COL_TAGO = "tago";
    private static final String COL_MONATO = "monato";
    private static final String COL_JARO = "jaro";
    private static final String COL_VORTO = "vorto";
    private static final String COL_DOSIERO = "dosiero";


    private SQLiteDatabase bdd;

    private Datumbazo maBaseSQLite;

    public VortoDao(Context context) {
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

    public long insertLivre(Vorto vorto) {
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_TAGO, vorto.getTago().intValue());
        values.put(COL_MONATO, vorto.getMonato().intValue());
        values.put(COL_JARO, vorto.getJaro());
        values.put(COL_VORTO, vorto.getVorto());
        values.put(COL_DOSIERO, vorto.getDosiero());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_VORTOJ, null, values);
    }


    public Vorto getVortoPerTago(int tago,int monato,int jaro) {
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query(TABLE_VORTOJ, new String[]{COL_ID, COL_TAGO, COL_MONATO, COL_JARO, COL_VORTO, COL_DOSIERO}, COL_TAGO + " = " + tago + " and "+COL_MONATO + "= "+monato+" and "+COL_JARO+" = "+jaro, null, null, null, null);
        return cursorToVorto(c);
    }

    public Vorto getVortoPerId(int id) {
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query(TABLE_VORTOJ, new String[]{COL_ID, COL_TAGO, COL_MONATO, COL_JARO, COL_VORTO, COL_DOSIERO}, COL_ID + " =" +id, null, null, null, null);
        return cursorToVorto(c);
    }

    //Cette méthode permet de convertir un cursor en un livre
    private Vorto cursorToVorto(Cursor c) {
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un livre
        Vorto vorto = new Vorto();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        vorto.setId(c.getInt(0));
        vorto.setTago(c.getInt(1));
        vorto.setMonato(c.getInt(2));
        vorto.setMonato(c.getInt(3));
        vorto.setVorto(c.getString(4));
        vorto.setDosiero(c.getString(5));
        //On ferme le cursor
        c.close();

        //On retourne le livre
        return vorto;
    }
}