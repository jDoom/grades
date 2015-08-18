package com.dominikjambor.grades;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Dominik on 1/16/2015.
 */
public class Tantargy {
    byte jegyekSzama;
    Jegy jegyek[];
    String nev;
    public Tantargy(String n, byte j){
        jegyekSzama = j;
        jegyek = new Jegy[100];
        nev = n;
    }

    double getAtlag(int tizedesJegyek){
        if(jegyekSzama == 0){
            return 0;
        }
        double o=0;
        for(int i=0;i<jegyekSzama;i++){
            o+=jegyek[i].getErtek();
        }
        return round(o/jegyekSzama,tizedesJegyek);
    }
    static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
