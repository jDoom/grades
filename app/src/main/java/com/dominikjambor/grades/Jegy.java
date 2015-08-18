package com.dominikjambor.grades;

/**
 * Created by Dominik on 2/10/2015.
 */
public class Jegy {
    private int ertek;
    private String datum;
    private boolean vanMegjegyzes,fontos;
    private String megjegyzes;

    public int getErtek() {
        return ertek;
    }

    public void setErtek(int ertek) {
        this.ertek = ertek;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public boolean isVanMegjegyzes() {
        return vanMegjegyzes;
    }

    public void setVanMegjegyzes(boolean vanMegjegyzes) {
        this.vanMegjegyzes = vanMegjegyzes;
    }

    public boolean isFontos() {
        return fontos;
    }

    public void setFontos(boolean fontos) {
        this.fontos = fontos;
    }

    public String getMegjegyzes() {
        return megjegyzes;
    }

    public void setMegjegyzes(String megjegyzes) {
        this.megjegyzes = megjegyzes;
    }
}
