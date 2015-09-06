package com.dominikjambor.grades;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik on 1/6/2015.
 */
public class fragment_tantargyak extends Fragment {
    static View rootView;
    static LayoutInflater inflaterr;
    static ListView list;
    static int szerkesztid=0;
    static FloatingActionButton fab;
    private static List<String> tantargyList = new ArrayList<>();
    static void createList(){
        tantargyList.clear();
        for(int i=0;i<Settings.tantargyakSzama;i++){
            tantargyList.add(Settings.tantargyak[i].nev);
        }
        populateListView();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_menu2,container , false);
        inflaterr = inflater;
        createList();
        fab =(FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.attachToListView(list);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.ShowTantargyHozzaado();
            }
        });
        return rootView;

    }
    private static void populateListView(){
        ArrayAdapter<String> adapter = new MyListAdapter(rootView.getContext());
        list =(ListView) rootView.findViewById(R.id.listView2);
        list.setAdapter(adapter);
    }

    private static class MyListAdapter extends ArrayAdapter<String>{

        public MyListAdapter(Context context) {
            super(context,R.layout.itemlayout_main,tantargyList);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = inflaterr.inflate(R.layout.itemlayout_tantargy, parent, false);
            }
            TextView tantargyTextView = (TextView) itemView.findViewById(R.id.datumTextView);
            ImageView deleteImage = (ImageView) itemView.findViewById(R.id.deleteImage);
            deleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    Toast.makeText(rootView.getContext(), Settings.tantargyak[position].nev+" törölve.",
                                            Toast.LENGTH_SHORT).show();
                                    for(int i=position;i<Settings.tantargyakSzama-1;i++)
                                    {
                                        Settings.tantargyak[i]=Settings.tantargyak[i+1];
                                    }
                                    Settings.tantargyakSzama--;
                                    Settings.tantargyList.remove(position);
                                    createList();
                                    Settings.SaveAll(getContext());
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
                    builder.setMessage("'"+Settings.tantargyak[position].nev+"' törlése?").setPositiveButton("Igen", dialogClickListener)
                            .setNegativeButton("Nem", dialogClickListener).show();
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    szerkesztid=position;
                    ShowTantargySzerkeszto();
                }
            });
            tantargyTextView.setText(Settings.tantargyak[position].nev);
            return itemView;
        }
    }
    static void ShowTantargySzerkeszto(){
        dialog_TantargySzerkeszto javDiag = new dialog_TantargySzerkeszto();
        javDiag.show(MainActivity.fmgr,"asd");
    }
}
