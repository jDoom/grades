package com.dominikjambor.grades;

import android.app.backup.BackupManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik on 2/10/2015.
 */
public class Settings {
    static Tantargy tantargyak[] = new Tantargy[100];
    static float javitHatar = 0;
    static int ketesMin = 0, ketesMax = 0, tantargyakSzama = 0;
    static boolean jelszo = false;
    static String jelszoText;
    static int savedVersion;
    static List tantargyList = new ArrayList<String>();
    static boolean mainfragment_tutorial, tantargyfragment_tutorial, tinfo_tutorial;

    static void LoadSavedData(Context context) {
        //JAV HAT
        //KETMIN
        //KETMAX
        //TANTARGY
        //ERTEK
        //VANMEG
        //MEG
        //FONTOS
        //DATUM
        tantargyList = new ArrayList<String>();
        SharedPreferences prefs = context.getSharedPreferences(
                "com.dominikjambor.grades.data", Context.MODE_PRIVATE);
        mainfragment_tutorial = prefs.getBoolean("maintut", false);
        tantargyfragment_tutorial = prefs.getBoolean("tfragtut", false);
        tinfo_tutorial = prefs.getBoolean("tinfotut", false);

        savedVersion = prefs.getInt("version", 0);
        javitHatar = prefs.getFloat("javithatar", 4.0f);
        ketesMin = prefs.getInt("ketesmin", 2);
        ketesMax = prefs.getInt("ketesmax", 4);
        tantargyakSzama = prefs.getInt("tantargyakszama", 0);

        if (savedVersion == 0) {
            String tanPref = prefs.getString("tan", "");
            String[] tanPrefS = tanPref.split(System.getProperty("line.separator"));
            int p = 0;
            for (int i = 0; i < tantargyakSzama; i++) {
                tantargyak[i] = new Tantargy(tanPrefS[p], Integer.parseInt(tanPrefS[p + 1].trim()));
                tantargyak[i].felev = 0;
                p += 2;
                for (int n = 0; n < tantargyak[i].jegyekSzama; n++) {
                    tantargyak[i].jegyek[n] = new Jegy();
                    tantargyak[i].jegyek[n].setErtek(Integer.parseInt(tanPrefS[p].trim()));
                    p++;
                    tantargyak[i].jegyek[n].setVanMegjegyzes(Boolean.parseBoolean(tanPrefS[p].trim()));
                    p++;
                    if (tantargyak[i].jegyek[n].isVanMegjegyzes()) {
                        tantargyak[i].jegyek[n].setMegjegyzes(tanPrefS[p]);
                        p++;
                    }
                    tantargyak[i].jegyek[n].setFontos(Boolean.parseBoolean(tanPrefS[p].trim()));
                    p++;
                    tantargyak[i].jegyek[n].setDatum(tanPrefS[p]);
                    p++;
                    tantargyak[i].jegyek[n].setFelev(0);
                }
            }
        } else {
            if (MainActivity.verc > savedVersion) {
            }
            for (int i = 0; i < tantargyakSzama; i++) {
                tantargyak[i] = new Tantargy(prefs.getString("tannev" + String.valueOf(i), "Hibás mentés"), prefs.getInt("jegyszam" + String.valueOf(i), 0));
                tantargyak[i].felev = prefs.getInt("tanfelev" + String.valueOf(i), 0);
                for (int n = 0; n < tantargyak[i].jegyekSzama; n++) {
                    tantargyak[i].jegyek[n] = new Jegy();
                    tantargyak[i].jegyek[n].setErtek(prefs.getInt("je" + String.valueOf(i) + String.valueOf(n), 1));
                    tantargyak[i].jegyek[n].setVanMegjegyzes(prefs.getBoolean("jvm" + String.valueOf(i) + String.valueOf(n), false));
                    tantargyak[i].jegyek[n].setMegjegyzes(prefs.getString("jm" + String.valueOf(i) + String.valueOf(n), ""));
                    tantargyak[i].jegyek[n].setFontos(prefs.getBoolean("jf" + String.valueOf(i) + String.valueOf(n), false));
                    tantargyak[i].jegyek[n].setDatum(prefs.getString("jd" + String.valueOf(i) + String.valueOf(n), "1999.01.01"));
                    tantargyak[i].jegyek[n].setFelev(prefs.getInt("jfe" + String.valueOf(i) + String.valueOf(n), 0));
                }
            }
        }
        if (!mainfragment_tutorial && tantargyakSzama == 0) {
            tantargyakSzama = 1;
            tantargyak[0] = new Tantargy("Példa",1);
            tantargyak[0].felev=0;
            tantargyak[0].jegyek[0] = new Jegy();
            tantargyak[0].jegyek[0].setErtek(5);
            tantargyak[0].jegyek[0].setVanMegjegyzes(true);
            tantargyak[0].jegyek[0].setMegjegyzes("Ez egy példa jegy egy példa tantárgyban.");
            tantargyak[0].jegyek[0].setFontos(true);
            tantargyak[0].jegyek[0].setDatum("1999.01.01");
            tantargyak[0].jegyek[0].setFelev(0);

        }
        updateTantargyList();
    }

    static void SaveAll(Context context) {

        SharedPreferences prefs = context.getSharedPreferences(
                "com.dominikjambor.grades.data", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.clear();
        edit.putInt("version", 1);
        edit.putBoolean("maintut",mainfragment_tutorial);
        edit.putBoolean("tfragtut",tantargyfragment_tutorial);
        edit.putBoolean("tinfotut",tinfo_tutorial);

        edit.putFloat("javithatar", Settings.javitHatar).apply();
        edit.putInt("ketesmin", Settings.ketesMin).apply();
        edit.putInt("ketesmax", Settings.ketesMax).apply();
        edit.putInt("tantargyakszama", Settings.tantargyakSzama).apply();
        for (int i = 0; i < tantargyakSzama; i++) {
            edit.putString("tannev" + String.valueOf(i), tantargyak[i].nev);
            edit.putInt("jegyszam" + String.valueOf(i), tantargyak[i].jegyekSzama);
            edit.putInt("tanfelev" + String.valueOf(i), tantargyak[i].felev);
            for (int n = 0; n < tantargyak[i].jegyekSzama; n++) {
                edit.putInt("je" + String.valueOf(i) + String.valueOf(n), tantargyak[i].jegyek[n].getErtek());
                edit.putBoolean("jvm" + String.valueOf(i) + String.valueOf(n), tantargyak[i].jegyek[n].isVanMegjegyzes());
                edit.putString("jm" + String.valueOf(i) + String.valueOf(n), tantargyak[i].jegyek[n].getMegjegyzes());
                edit.putBoolean("jf" + String.valueOf(i) + String.valueOf(n), tantargyak[i].jegyek[n].isFontos());
                edit.putString("jd" + String.valueOf(i) + String.valueOf(n), tantargyak[i].jegyek[n].getDatum());
                edit.putInt("jfe" + String.valueOf(i) + String.valueOf(n), tantargyak[i].jegyek[n].getFelev());
            }
        }
        edit.commit();

        BackupManager bm = new BackupManager(context);
        bm.dataChanged();
        Intent intent = new Intent(context, GradesWidget.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        int ids[] = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, GradesWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        context.sendBroadcast(intent);
        /*prefs.edit().putFloat("javithatar", Settings.javitHatar).apply();
        prefs.edit().putInt("ketesmin", Settings.ketesMin).apply();
        prefs.edit().putInt("ketesmax", Settings.ketesMax).apply();
        prefs.edit().putInt("tantargyakszama", Settings.tantargyakSzama).apply();

        String tanPref = "";
        for (int i = 0; i < Settings.tantargyakSzama; i++) {
            tanPref += Settings.tantargyak[i].nev + "\n";
            tanPref += Settings.tantargyak[i].jegyekSzama + "\n";
            for (int n = 0; n < Settings.tantargyak[i].jegyekSzama; n++) {
                tanPref += Settings.tantargyak[i].jegyek[n].getErtek() + "\n";
                tanPref += Settings.tantargyak[i].jegyek[n].isVanMegjegyzes() + "\n";
                if (tantargyak[i].jegyek[n].isVanMegjegyzes()) {
                    tanPref += Settings.tantargyak[i].jegyek[n].getMegjegyzes() + "\n";
                }
                tanPref += Settings.tantargyak[i].jegyek[n].isFontos() + "\n";
                tanPref += Settings.tantargyak[i].jegyek[n].getDatum() + "\n";
            }
        }
        prefs.edit().putString("tan", tanPref).apply();

        */
    }

    static void updateTantargyList() {
        tantargyList.clear();
        for (int i = 0; i < tantargyakSzama; i++) {
            tantargyList.add(tantargyak[i].nev);
        }
    }
}
