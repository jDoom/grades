package com.dominikjambor.grades;

import android.app.backup.BackupManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik on 2/10/2015.
 */
public class Settings {
    static Tantargy tantargyak[] = new Tantargy[100];
    static float javitHatar=0;
    static int ketesMin=0,ketesMax=0,tantargyakSzama=0;
    static boolean jelszo=false;
    static String jelszoText;
    static List tantargyList = new ArrayList<String>();
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

        Settings.javitHatar = prefs.getFloat("javithatar", 4.0f);
        Settings.ketesMin = prefs.getInt("ketesmin", 2);
        Settings.ketesMax = prefs.getInt("ketesmax", 4);
        Settings.tantargyakSzama = prefs.getInt("tantargyakszama", 0);
        String tanPref = prefs.getString("tan", "");
        String[] tanPrefS = tanPref.split(System.getProperty("line.separator"));
        int p=0;
        for(int i=0;i<Settings.tantargyakSzama;i++){
            Settings.tantargyak[i] = new Tantargy(tanPrefS[p],Integer.parseInt(tanPrefS[p+1].trim()));
            p+=2;
            for(int n=0;n<Settings.tantargyak[i].jegyekSzama;n++){
                Settings.tantargyak[i].jegyek[n] = new Jegy();
                Settings.tantargyak[i].jegyek[n].setErtek(Integer.parseInt(tanPrefS[p].trim()));
                p++;
                Settings.tantargyak[i].jegyek[n].setVanMegjegyzes(Boolean.parseBoolean(tanPrefS[p].trim()));
                p++;
                if(Settings.tantargyak[i].jegyek[n].isVanMegjegyzes()){
                    Settings.tantargyak[i].jegyek[n].setMegjegyzes(tanPrefS[p]);
                    p++;
                }
                Settings.tantargyak[i].jegyek[n].setFontos(Boolean.parseBoolean(tanPrefS[p].trim()));
                p++;
                Settings.tantargyak[i].jegyek[n].setDatum(tanPrefS[p]);
                p++;
            }
        }
        updateTantargyList();
    }
    static void SaveAll(Context context){

        SharedPreferences prefs = context.getSharedPreferences(
                "com.dominikjambor.grades.data", Context.MODE_PRIVATE);
        prefs.edit().putFloat("javithatar", Settings.javitHatar).apply();
        prefs.edit().putInt("ketesmin", Settings.ketesMin).apply();
        prefs.edit().putInt("ketesmax", Settings.ketesMax).apply();
        prefs.edit().putInt("tantargyakszama", Settings.tantargyakSzama).apply();

        String tanPref ="";
        for(int i=0;i<Settings.tantargyakSzama;i++){
            tanPref+=Settings.tantargyak[i].nev+"\n";
            tanPref+=Settings.tantargyak[i].jegyekSzama+"\n";
            for(int n=0;n<Settings.tantargyak[i].jegyekSzama;n++){
                tanPref+=Settings.tantargyak[i].jegyek[n].getErtek()+"\n";
                tanPref+=Settings.tantargyak[i].jegyek[n].isVanMegjegyzes()+"\n";
                if(tantargyak[i].jegyek[n].isVanMegjegyzes()){
                    tanPref+=Settings.tantargyak[i].jegyek[n].getMegjegyzes()+"\n";
                }
                tanPref+=Settings.tantargyak[i].jegyek[n].isFontos()+"\n";
                tanPref+=Settings.tantargyak[i].jegyek[n].getDatum()+"\n";
            }
        }
        prefs.edit().putString("tan", tanPref).apply();
        prefs.edit().commit();
        BackupManager bm = new BackupManager(context);
        bm.dataChanged();
        Intent intent = new Intent(context, GradesWidget.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        int ids[] = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, GradesWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        context.sendBroadcast(intent);
    }
    static void updateTantargyList(){
        tantargyList.clear();
        for(int i=0;i<tantargyakSzama;i++){
            tantargyList.add(tantargyak[i].nev);
        }
    }
}
