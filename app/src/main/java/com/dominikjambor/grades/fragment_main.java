package com.dominikjambor.grades;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

/**
 * Created by Dominik on 1/6/2015.
 */
public class fragment_main extends Fragment {
    static View rootView;
    static LayoutInflater inflaterr;
    static ListView list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.layout_menu1,container , false);
        inflaterr = inflater;
        FloatingActionButton fab =(FloatingActionButton) rootView.findViewById(R.id.fab);
        list = (ListView) rootView.findViewById(R.id.listView);
        update();
        fab.attachToListView(list);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Settings.tantargyakSzama>0){
                    Intent intent = new Intent(getActivity(), JegyHozzaadas.class);
                    getActivity().startActivity(intent);
                }
                else Toast.makeText(getActivity(), "Nincsenek tantárgyak!",
                        Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;

    }
    private static void populateListView(){
        ArrayAdapter<String> adapter = new MyListAdapter(rootView.getContext());
        list.setAdapter(adapter);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int p = position;
                Context cx = list.getContext();
                Intent intent = new Intent(cx, JegyHozzaadas.class);
                intent.putExtra("TANTARGY", p);
                cx.startActivity(intent);
                return true;
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int p = position;
                Context cx = list.getContext();
                if(Settings.tantargyak[p].jegyekSzama>0) {

                    Intent intent = new Intent(cx, TantargyNezetActivity.class);
                    intent.putExtra("TANTARGY_ID", p);
                    if( Build.VERSION.SDK_INT>=21 )
                        cx.startActivity(intent,ActivityOptions.makeSceneTransitionAnimation((Activity)cx).toBundle());
                    else
                        cx.startActivity(intent);
                }
                else{
                    Toast.makeText(cx,"Nincs jegyed ebből a tantárgyból!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private static class MyListAdapter extends ArrayAdapter<String>{

        public MyListAdapter(Context context) {
            super(context,R.layout.itemlayout_main,Settings.tantargyList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = inflaterr.inflate(R.layout.itemlayout_main, parent, false);
            }
            TextView tantargyText = (TextView) itemView.findViewById(R.id.textView);
            tantargyText.setText(Settings.tantargyak[position].nev);
            TextView atlagText = (TextView) itemView.findViewById(R.id.atlagText);
            atlagText.setText(String.valueOf(Settings.tantargyak[position].getAtlag(2)));
            TextView jegySzamText = (TextView) itemView.findViewById(R.id.jegySzamText);
            jegySzamText.setText(String.valueOf(Settings.tantargyak[position].jegyekSzama));
            ImageView img = (ImageView) itemView.findViewById(R.id.messageImage);
            img.setVisibility(View.INVISIBLE);
            if((float)Settings.tantargyak[position].getAtlag(2)%1 >= (float)(0.35f+Settings.ketesMin*0.05)%1
                    &&
                    (float)Settings.tantargyak[position].getAtlag(2)%1 <= (float)(0.35f+Settings.ketesMax*0.05)%1){
                img.setVisibility(View.VISIBLE);
                img.setImageResource(R.drawable.sure);
            }
            if(Settings.tantargyak[position].getAtlag(2)<=Settings.javitHatar&&Settings.tantargyak[position].getAtlag(2)>0)
            {
                img.setVisibility(View.VISIBLE);
                img.setImageResource(R.drawable.warning );
            }
            return itemView;
        }
    }
    public static void update(){
        Settings.updateTantargyList();
        populateListView();
        double o=0;
        double oszto=0;
        for(int i=0;i<Settings.tantargyakSzama;i++){
            long a = Math.round(Settings.tantargyak[i].getAtlag(2));
            if(a!=0){
                o+=a;
                oszto++;
            }

        }
        double osszatlag;
        if(oszto>0){
            osszatlag = o/oszto;
        }
        else osszatlag=0;
        TextView osszAtlagText = (TextView) rootView.findViewById(R.id.osszAtlagText);
        if(Settings.tantargyakSzama>0) osszAtlagText.setText(String.valueOf(Tantargy.round(osszatlag,2)));
        else  osszAtlagText.setText("N/A");
        int javsz=0,ket=0;
        for(int i=0;i<Settings.tantargyakSzama;i++){
            if(Settings.tantargyak[i].getAtlag(2)<=Settings.javitHatar&&Settings.tantargyak[i].getAtlag(2)>0)
                javsz++;
            if((float)Settings.tantargyak[i].getAtlag(2)%1 >= (float)(0.35f+Settings.ketesMin*0.05)%1
                    &&
                    (float)Settings.tantargyak[i].getAtlag(2)%1 <= (float)(0.35f+Settings.ketesMax*0.05)%1){
                ket++;
            }
        }
        TextView javKell = (TextView) rootView.findViewById(R.id.javitasText);
        javKell.setText(String.valueOf(javsz));
        TextView ketesek = (TextView) rootView.findViewById(R.id.ketesekText);
        ketesek.setText(String.valueOf(ket));
    }




}
