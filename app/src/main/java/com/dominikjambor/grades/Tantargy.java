package com.dominikjambor.grades;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Dominik on 1/16/2015.
 */
public class Tantargy {
    int jegyekSzama;
    int felev;
    Jegy jegyek[];
    String nev;

    public Tantargy(String n, int j) {
        jegyekSzama = j;
        jegyek = new Jegy[100];
        nev = n;
    }

    double getAtlag(int tizedesJegyek, int felev) {
        if (jegyekSzama == 0) {
            return 0;
        }
        int n = 0;
        double o = 0;
        for (int i = 0; i < jegyekSzama; i++) {
            if (felev == 2 || jegyek[i].getFelev() == felev) {
                o += jegyek[i].getErtek();
                n++;
            }
        }
        if (n == 0) {
            return 0;
        }
        return round(o / n, tizedesJegyek);
    }

    static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public int[] getIntJegyek() {
        int[] j = new int[jegyekSzama];
        for (int i = 0; i < jegyekSzama; i++) {
            j[i] = jegyek[i].getErtek();
        }
        return j;
    }
}
