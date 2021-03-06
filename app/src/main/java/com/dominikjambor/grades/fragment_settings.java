package com.dominikjambor.grades;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik on 1/6/2015.
 */
public class fragment_settings extends Fragment {
    static View rootView;
    static List settingList = new ArrayList<String>();
    static List settingValueList = new ArrayList<String>();
    static LayoutInflater inflaterr;
    static ListView settingListView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_menu3,container , false);
        inflaterr = inflater;
        settingListView = (ListView) rootView.findViewById(R.id.listView3);

        update();
        return rootView;
    }
    static class MyListAdapter extends ArrayAdapter<String>{

        public MyListAdapter(Context context) {
            super(context,R.layout.itemlayout_main,settingList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = inflaterr.inflate(R.layout.iteamlayout_settings, parent, false);
                TextView settingName = (TextView) itemView.findViewById(R.id.settingName);
                settingName.setText((String) settingList.get(position));

                TextView settingValue = (TextView) itemView.findViewById(R.id.settingValue);
                settingValue.setText((String) settingValueList.get(position));
            }
            return itemView;
        }
    }
    static void ShowJavitandoDialog(){
        dialog_Javitando javDiag = new dialog_Javitando();
        javDiag.show(MainActivity.fmgr,"asd");
    }
    static void ShowKetesDialog(){
        dialog_Ketes javDiag = new dialog_Ketes();
        javDiag.show(MainActivity.fmgr,"asd");
    }
    static void ShowUpdateDialog(){
        dialog_update udia = new dialog_update();
        udia.show(MainActivity.fmgr,"asd");
    }
    static void ShowHelpDialog(){
        dialog_help hdia = new dialog_help();
        hdia.show(MainActivity.fmgr,"asd");
    }
    static void ShowInfoDialog(){
        dialog_info idia = new dialog_info();
        idia.show(MainActivity.fmgr,"asd");
    }
    static void ShowFelevDialog(){
        dialog_felev idia = new dialog_felev();
        idia.show(MainActivity.fmgr,"asd");
    }
    static void update()
    {
        settingList.clear();
        settingValueList.clear();

        settingList.add("Javítandó határ");
        settingValueList.add(String.valueOf(Settings.javitHatar));

        settingList.add("Kétes határok");
        settingValueList.add("x."+String.valueOf(35+(Settings.ketesMin*5))+" - x."+String.valueOf(35+(Settings.ketesMax*5)));

        settingList.add("Frissítés keresése");
        settingValueList.add("Ellenőrzés");

        settingList.add("Segítség");
        settingValueList.add("Használati utasítás");

        settingList.add("Félév");
        settingValueList.add("Félév kollektív beállítása");

        settingList.add("Infó");
        settingValueList.add("Készítők és Liszenszek");


        settingListView.setAdapter(new MyListAdapter(rootView.getContext()));
        settingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        ShowJavitandoDialog();
                        break;
                    case 1:
                        ShowKetesDialog();
                        break;
                    case 2:
                        ShowUpdateDialog();
                        break;
                    case 3:
                        ShowHelpDialog();
                        break;
                    case 4:
                        ShowFelevDialog();
                        break;
                    case 5:
                        ShowInfoDialog();
                        break;

                }
            }
        });
    }
}
