package org.esperanto_france.samopiniuloj.dao;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class Datumbazo extends SQLiteOpenHelper {

    private static final int DATUMBAZO_VERSIO = 1;
    private static final String DATUMBAZO_NOMO = "sam.db";

    public Datumbazo(Context context) {
        super(context, DATUMBAZO_NOMO, null, DATUMBAZO_VERSIO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //on créé la table à partir de la requête écrite dans la variable CREATE_BDD
        db.execSQL("CREATE TABLE IF NOT EXISTS `sam_proponoj` (  `id` INTEGER,  `ludanto_id` INTEGER,  `vorto_id` INTEGER,  `propono` TEXT,  `vico` INTEGER,  `poento` INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `sam_vortoj` (  `id` INTEGER,  `tago` INTEGER,  `monato` INTEGER,  `jaro` TEXT,  `vorto` TEXT,  `dosiero` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `sam_ludantoj` (  `id` INTEGER,  `kromnomo` TEXT,  `retadreso` TEXT,  `pasvorto` TEXT,  `lando` TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
           // Ici on ne fait rien, mais si on change des trucs dans la base de données, il faudra gérer les cas de mise à jour
    }

}
