package com.example.nathie.test3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper{



    public static final String DATABASE_NAME = "Markt.db";
    public static final String TABLE_NAME = "Wochenmärkte";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "TYP";
    public static final String COL_3 = "Stadt";
    public static final String COL_4 = "Adresse";
    public static final String COL_5 = "Öffnungszeiten";
    public static final String COL_6 = "Kontakt";
    public static final String COL_7 = "Angebot";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("Create Table " + TABLE_NAME + "("+COL_1 +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +COL_2+" TEXT,"+COL_3+" TEXT," +COL_4+" TEXT,"+COL_5+" TEXT,"+COL_6+" TEXT,"+COL_7+" TEXT"+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int NewVersion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String typ, String stadt, String adresse,
                              String öffnungszeiten, String kontakt, String angebot){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, typ);
        contentValues.put(COL_3, stadt);
        contentValues.put(COL_4, adresse);
        contentValues.put(COL_5, öffnungszeiten);
        contentValues.put(COL_6, kontakt);
        contentValues.put(COL_7, angebot);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean deleteWochenmarkt(int delID){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_NAME, COL_1 + "=" + delID, null) > 0 ;
    }

    public void AddData(){
        insertData( "Wochen- und Bauernmarkt",
                    "Beratzhausen",
                    "Am Parkplatz Essenbuegl, Beratzhausen",
                    "Samstag 7:00-12:00 Uhr. Falls Samstag ein Feiertag ist, dann finder der Markt am Vortag statt",
                    "Herr Braun 09493/940011",
                    "Der Markt bietet ein umfassendes Angebot an heimischen Gemuese und Obst, Wurst und Fleischwaren, Kaese und Molkereiprodukten, Honig und Honigprodukten");
        insertData( "Bauernmarkt",
                    "Donaustauf",
                    "Festplatz, Donaustauf",
                    "Freitag 13:30-16:30",
                    "Heimat- und Fremdenverkehrsverein Donaustauf e.V. Herr Mihalyi 0171/3735835",
                    "Bewusste Ernaehrung mit natuerlich erzeugten Lebensmitteln wie Brot, Kaese, Gemuese, Eier, Wurst, Fleisch, Honig, Gefluegel, Gebaeck, Marmelade, Blumen und Fruechten");
        insertData( "Wochenmarkt",
                    "Hemau",
                    "Neuer Stadtplatz, Hemau",
                    "Mittwoch 8:00-12:30",
                    "Stadt Hemau, Herr Andreas Meyer 09491/940018",
                    "Obst und Gemuese, Kartoffeln, Kaesespezialitäten,Bio-Obstsäfte, Eier, Frischfleisch, Geraeuchertes, Backwaren aus dem Holzofen, frischer und geraeucherter Fisch sowie Honig und Honigprodukte");
        insertData( "Laaberer Wochenmarkt",
                    "Laaber",
                    "Marktplatz Laaber",
                    "Samstag 7:00-12:00",
                    "Herr Schmid, VG Laaber 09498/940115",
                    "Umfassendes Angebot an heimischen Obst und Gemuese, Kaese, Brot, Wurstwaren sowie Imkereierzeugnisse");
        insertData( "Wochen- und Bauernmarkt",
                    "Neutraubling",
                    "Marktplatz vor dem BRK-Altenheim, Neutraubling",
                    "Freitag 8:00-12:30",
                    "Herr Schwarz, Stadt Neutraubling 09401/80036",
                    "Gemuese, Obst, Fleisch, Wurst, Fisch, Kaese, Honig und Honigprodukte, Blumen, Gewuerzkraeuter, Kartoffeln, Wein, Gebaeck, Floristik");
        insertData( "Wochenmarkt",
                    "Obertraubling",
                    "Piesenkofener Strasse",
                    "Freitag 14:00-17:00",
                    "Gemeinde Obertraubling 09401/96010",
                    "Backwaren, Honig, Obst, Gemuese, Kartoffeln, Eier, Kaese, saisonale Produkte");
        insertData( "Bauernmarkt",
                    "Regenstauf",
                    "An der Bruecke, 93128 Regenstauf",
                    "Mittwoch 8:00-12:00",
                    "Herr Kreisobmann, Johann Mayer 09471/1564",
                    "Der Bauernmarkt bietet ein umfassendes Angebot an heimischem Obst und Gemuese, Wurst und Fleischwaren, Biokaese, Eier und Gefluegel sowie Kaffee und Kuchen.");
        insertData( "Wochen- und Bauernmarkt",
                    "Schirling",
                    "Rathausplatz, Schirling",
                    "Donnerstag 7:30-12:30",
                    "Ernst Roth 09451/3112",
                    "Die Marktstaende bieten auf dem Rathausplatz ein breites Warenangebot an Obst, Gemuese, Honig, Fleisch- und Wurstwaren, Bio-Kaese und noch vieles mehr aus der Region an.");
        insertData( "Bauernmarkt",
                    "Tegernheim",
                    "Gewerbegebiet Nord, Tegernheim",
                    "Samstag 7:00-13:00",
                    "Gemeinde Tegernheim, Frau Luible 09403/952024",
                    "Bio-Produkte(Kaese, Eier, Wurst, Brot), frisches Obst und Gemuese, Kartoffeln, Bioaepfel, Eier, Fleisch, Wurst, Backware, Honig und Honigprodukte");
        insertData( "Regionalmarkt",
                    "Woerth a.d. Donau",
                    "Regensburger Strasse, Woerth a.d. Donau",
                    "Samstag 8:00-12:00",
                    "Frau Hohlschwandner 09482/94030 Herr Richard Schweiger 09404/1414",
                    "Grosse Vielfalt an heimischen Lebensmitteln wie Obst, Gemuese, Backwaren, Lammprodukten");
        insertData( "Wochenmarkt",
                    "Zeitlarn",
                    "Hausler Getraenkemarkt, Zeitlarn",
                    "Mittwoch 8:00-12:30",
                    "Frau Kueffner 09416969323",
                    "Saisonales Obst und Gemuese aus regionalem Anbau, saisonale Blumen, Blumenkraenze und Gestecke, Eier, Kartoffeln, Blumenstraeusse, Bio-Frischmilchprodukte wie Joghurt" +
                             "und Kaese, Bio-Fleisch- und Wurstwaren, selbstgemachte Marmelade und Sirup, Backwaren, Holzofenbrot, mediterrane Backspezialitaeten");
        insertData( "Bauern- und Wochenmarkt",
                    "Regensburg",
                    "Bismarckplatz Regensburg",
                    "Samstag 9:00-18:00",
                    "Stadt Regensburg 0941/5070",
                    "Umfangreiches Warenangebot von regionalen und saisonalen Produkten");
        insertData( "Bauernmarkt",
                    "Regensburg",
                    "Altmühlstraße 1, Parkplatz am ehemaligen Autohaus",
                    "Donnerstag 13:00-16:30",
                    "Herr Kreisobmann 09471/1564, Johann Mayer 09471/1564",
                    "Heimisches Obst und Gemüse, Fleisch, Geflügel, Wurst und FLeischwaren, Käse, Brot, Honigprodukte");
        insertData( "Bauernmarkt",
                    "Regensburg-Burgweinting",
                    "BUZ Burgweinting Friedrich-Viehbacher-Allee 3-5 Regensburg",
                    "Mittwoch 13:30-17:30",
                    "Stadt Regensburg 0941/5070",
                    "Obst und Gemuese, Wurst- und Fleischwaren, Kaese und Honig");
        insertData( "Bauernmarkt Katharinenmarkt",
                    "Regensburg",
                    "Stadtamhof Regensburg",
                    "Mittwoch 8:00-13:00",
                    "Stadt Regensburg 0941/5070",
                    "Der Bauernmarkt bietet ein umfassendes Angebot an Backwaren, Nudeln, Gemuese, Honig, Fisch, Biogemuese, Fleisch und Wurstwaren, Kartoffeln, Kaese, Olvenoel, Blumen");
        insertData( "Bauernmarkt",
                    "Regensburg",
                    "Alter Kornmarkt Regensburg",
                    "Samstag 5:00-13:00",
                    "Stadt Regensburg 0941/5070",
                    "Umfassendes Warenangebot an regionalen und saisonalen Produkten");
        insertData( "Kartoffelmarkt",
                    "Regensburg",
                    "Woehrdstrasse 48-54, Regensburg",
                    "Mittwoch und Samstag 7:00-12:00",
                    "Stadt Regensburg 0941/5070",
                    "Kartoffeln aus der Region");
        insertData( "Kumpfmuehler Markt",
                    "Regensburg",
                    "kumpfmuehler Strasse 48-50 Regensburg",
                    "Mittwoch und Samstag 6:00-12:00",
                    "Stadt Regensburg 0941/5070",
                    "Gemuese, Obst, Eier, Kartoffeln");
        insertData( "Viktualienmarkt",
                    "Regensburg",
                    "Neupfarrplatz Regensburg",
                    "Montag bis Samstag 9:00-16:00",
                    "Stadt Regensburg 0941/5070",
                    "Lebensmittelmarkt mit saisonalem Gemuese und Blumen. Wechselnde Staende mit verschiedenen Angeboten");
        }

}