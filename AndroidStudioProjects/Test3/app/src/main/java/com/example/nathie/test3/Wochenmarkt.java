package com.example.nathie.test3;

public class Wochenmarkt {

    int Id;
    String Typ;
    String Stadt;
    String Adresse;
    String Öffnungszeiten;
    String Kontakt;
    String Angebot;


    public Wochenmarkt(){
    }

    public Wochenmarkt(String Typ, String Stadt, String Adresse,
                       String Öffnungszeiten, String Kontakt, String Angebot){
        this.Typ = Typ;
        this.Stadt = Stadt;
        this.Adresse = Adresse;
        this.Öffnungszeiten = Öffnungszeiten;
        this.Kontakt = Kontakt;
        this.Angebot = Angebot;
    }

    public int get_id() {
        return Id;
    }

    public String getTyp() {
        return Typ;
    }

    public String getStadt() {
        return Stadt;
    }

    public String getAdresse() {
        return Adresse;
    }

    public String getÖffnungszeiten() {
        return Öffnungszeiten;
    }

    public String getKontakt() {
        return Kontakt;
    }

    public String getAngebot() {
        return Angebot;
    }

    public void set_id(int Id) {

        this.Id = Id;
    }

    public void setTyp(String typ) {
        this.Typ = typ;
    }

    public void setStadt(String stadt) {
        this.Stadt = stadt;
    }

    public void setAdresse(String adresse) {
        this.Adresse = adresse;
    }

    public void setÖffnungszeiten(String öffnungszeiten) {
        this.Öffnungszeiten = öffnungszeiten;
    }

    public void setKontakt(String kontakt) {
        this.Kontakt = kontakt;
    }

    public void setAngebot(String angebot) {
        this.Angebot = angebot;
    }
}