package org.esperanto_france.samopiniuloj;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class Datumbazo extends SQLiteOpenHelper {


    public Datumbazo(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //on créé la table à partir de la requête écrite dans la variable CREATE_BDD
        db.execSQL("CREATE TABLE IF NOT EXISTS `sam_proponoj` (  `id` INTEGER PRIMARY KEY,  `ludanto_id` INTEGER,  `vorto_id` INTEGER,  `propono` TEXT,  `vico` INTEGER,  `poento` INTEGER,  `ip` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `sam_vortoj` (  `id` INTEGER PRIMARY KEY,  `tago` INTEGER,  `monato` INTEGER,  `jaro` TEXT,  `vorto` TEXT,  `dosiero` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `sam_ludantoj` (  `id` INTEGER PRIMARY KEY,  `kromnomo` TEXT,  `retadreso` TEXT,  `pasvorto` TEXT,  `lando` TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On peut fait ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        //comme ça lorsque je change la version les id repartent de 0
        db.execSQL("DROP TABLE sam_vortoj;");
        db.execSQL("DROP TABLE sam_proponoj;");
        db.execSQL("DROP TABLE sam_ludantoj;");
        onCreate(db);
    }

}
