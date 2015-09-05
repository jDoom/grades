package com.dominikjambor.grades;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

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
    static byte tantargyakSzama=0;
    static float javitHatar=0;
    static int ketesMin=0,ketesMax=0;
    static boolean jelszo=false;
    static String jelszoText;
    static List tantargyList = new ArrayList<String>();
    static void LoadSavedData(Context context){
        //JELSZO MI
        //JAV HAT
        //KETMIN
        //KETMAX
        //TANTARGY
        //ERTEK
        //VANMEG
        //MEG
        //FONTOS
        //DATUM
        try {

            BufferedReader inputReader = new BufferedReader(new InputStreamReader(context.openFileInput("ellenorzo")));
            Settings.jelszo = Boolean.parseBoolean(inputReader.readLine());
            if(Settings.jelszo){
                Settings.jelszoText = inputReader.readLine();
            }
            Settings.javitHatar = Float.parseFloat(inputReader.readLine());
            Settings.ketesMin = Integer.parseInt(inputReader.readLine());
            Settings.ketesMax = Integer.parseInt(inputReader.readLine());
            Settings.tantargyakSzama = Byte.parseByte(inputReader.readLine());
            for(int i=0;i<Settings.tantargyakSzama;i++){
                byte jegyszam;
                String tnev;
                tnev = inputReader.readLine();
                jegyszam = Byte.parseByte(inputReader.readLine());
                Settings.tantargyak[i] = new Tantargy(tnev,jegyszam);
                for(int n=0;n<jegyszam;n++) {
                    String  megjegyzes="semmi";
                    Boolean vanMegjegyzes, fontos;
                    Byte ertek;
                    String datum;

                    ertek = Byte.parseByte(inputReader.readLine());
                    vanMegjegyzes = Boolean.valueOf(inputReader.readLine());
                    if (vanMegjegyzes) {
                        megjegyzes = inputReader.readLine();
                    }
                    fontos = Boolean.parseBoolean(inputReader.readLine());
                    datum = inputReader.readLine();
                    Settings.tantargyak[i].jegyek[n] = new Jegy();
                    Settings.tantargyak[i].jegyek[n].setErtek(ertek);
                    Settings.tantargyak[i].jegyek[n].setVanMegjegyzes(vanMegjegyzes);
                    if(vanMegjegyzes)
                        Settings.tantargyak[i].jegyek[n].setMegjegyzes(megjegyzes);
                    Settings.tantargyak[i].jegyek[n].setFontos(fontos);
                    Settings.tantargyak[i].jegyek[n].setDatum(datum);
                }

            }


        } catch (IOException e) {

            e.printStackTrace();
            Settings.tantargyakSzama = 0;
            try {

                FileOutputStream fos = context.openFileOutput("ellenorzo", Context.MODE_PRIVATE);

                fos.write("0\n0\n0\n0\n0".getBytes());

                fos.close();

            } catch (Exception t) {

                t.printStackTrace();

            }

        }
    }
    static void SaveAll(Context context){
        try {

            FileOutputStream fos =  context.openFileOutput("ellenorzo", Context.MODE_PRIVATE);

            fos.write((String.valueOf(Settings.jelszo)+"\r\n").getBytes());
            if(Settings.jelszo){
                fos.write((Settings.jelszoText+"\r\n").getBytes());
            }
            fos.write((String.valueOf(Settings.javitHatar)+"\r\n").getBytes());
            fos.write((String.valueOf(Settings.ketesMin)+"\r\n").getBytes());
            fos.write((String.valueOf(Settings.ketesMax)+"\r\n").getBytes());
            fos.write((String.valueOf(Settings.tantargyakSzama)+"\r\n").getBytes());
            for(int i=0;i<Settings.tantargyakSzama;i++){
                fos.write((Settings.tantargyak[i].nev+"\r\n").getBytes());
                fos.write((String.valueOf(Settings.tantargyak[i].jegyekSzama)+"\r\n").getBytes());
                for(int n=0;n<Settings.tantargyak[i].jegyekSzama;n++){
                    fos.write((String.valueOf(Settings.tantargyak[i].jegyek[n].getErtek()) + "\r\n").getBytes());
                    fos.write((String.valueOf(Settings.tantargyak[i].jegyek[n].isVanMegjegyzes()) + "\r\n").getBytes());
                    if(Settings.tantargyak[i].jegyek[n].isVanMegjegyzes()){
                        fos.write((Settings.tantargyak[i].jegyek[n].getMegjegyzes()+"\r\n").getBytes());
                    }
                    fos.write((String.valueOf(Settings.tantargyak[i].jegyek[n].isFontos()) + "\r\n").getBytes());
                    fos.write((Settings.tantargyak[i].jegyek[n].getDatum() + "\r\n").getBytes());
                }
            }

            fos.close();

        } catch (Exception t) {

            t.printStackTrace();
        }
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
