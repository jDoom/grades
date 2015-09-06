package com.dominikjambor.grades;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class TantargyNezetActivity extends ActionBarActivity {
    static LayoutInflater inflaterr;
    static List jegyList;
    static int tantargyid, jegyid;
    static int tid;
    static Context cx;
    static ListView jegyListView;
    static FragmentManager fmr;
    static TextView atlagTextView;
    static TextView jegySzamText;
    static TextView legutobbiJegy;
    static TextView legutobbiDatum;
    static Activity dis;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tantargy_nezet);

        dis = this;
        cx = getApplicationContext();
        fmr = getFragmentManager();
        jegyList = new ArrayList<Jegy>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tid = getIntent().getExtras().getInt("TANTARGY_ID");
        getSupportActionBar().setTitle(Settings.tantargyak[tid].nev);

        atlagTextView = (TextView) findViewById(R.id.tanViewAtlag);
        jegySzamText = (TextView) findViewById(R.id.tanViewJegySzam);
        legutobbiJegy = (TextView) findViewById(R.id.tanViewLegutobbi);
        legutobbiDatum = (TextView) findViewById(R.id.tanViewLegutobbiDatum);
        jegyListView = (ListView) findViewById(R.id.tanViewListView);
        inflaterr = getLayoutInflater();
        update();
    }

    static class MyListAdapter extends ArrayAdapter<String> {

        public MyListAdapter(Context context) {
            super(context, R.layout.itemlayout_main, jegyList);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = inflaterr.inflate(R.layout.itemlayout_tantargynezet_item, parent, false);
            }
            TextView datumText = (TextView) itemView.findViewById(R.id.datumTextView);
            datumText.setText(Settings.tantargyak[tid].jegyek[position].getDatum());
            TextView kommentText = (TextView) itemView.findViewById(R.id.kommentTextView);
            kommentText.setText(Settings.tantargyak[tid].jegyek[position].getMegjegyzes());

            ImageView img = (ImageView) itemView.findViewById(R.id.jegyImage);
            switch (Settings.tantargyak[tid].jegyek[position].getErtek()) {
                case 1:
                    img.setImageResource(R.drawable.f1);
                    break;
                case 2:
                    img.setImageResource(R.drawable.f2);
                    break;
                case 3:
                    img.setImageResource(R.drawable.f3);
                    break;
                case 4:
                    img.setImageResource(R.drawable.f4);
                    break;
                case 5:
                    img.setImageResource(R.drawable.f5);
                    break;
            }
            if (Settings.tantargyak[tid].jegyek[position].isFontos()) {
                img.setPadding(0, 0, 0, 0);
            } else {
                img.setPadding(3, 3, 3, 3);
            }
            return itemView;
        }
    }

    static void update() {
        jegyList.clear();
        for (int i = 0; i < Settings.tantargyak[tid].jegyekSzama; i++) {
            jegyList.add(Settings.tantargyak[tid].jegyek[i]);
        }
        jegyListView.setAdapter(new MyListAdapter(cx));
        jegyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tantargyid = tid;
                jegyid = position;
                showJegyDiag();
            }
        });
        if(Settings.tantargyak[tid].jegyekSzama>0) {
            atlagTextView.setText(String.valueOf(Settings.tantargyak[tid].getAtlag(2)));
            jegySzamText.setText("Jegyek száma: " + String.valueOf(Settings.tantargyak[tid].jegyekSzama));
            legutobbiJegy.setText("Legutóbbi jegy: " + String.valueOf(Settings.tantargyak[tid].jegyek[Settings.tantargyak[tid].jegyekSzama - 1].getErtek()));
            legutobbiDatum.setText("Legutóbbi dátum: " + Settings.tantargyak[tid].jegyek[Settings.tantargyak[tid].jegyekSzama - 1].getDatum());
        }
    }

    static void showJegyDiag()
    {
        dialog_JegyNezet jdiag = new dialog_JegyNezet();
        jdiag.show(fmr, "aaa");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tantargynezetmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menuhozzaad){
            Intent intent = new Intent(this, JegyHozzaadas.class);
            intent.putExtra("TANTARGY", tid);
            intent.putExtra("TNEZET", true);
            this.startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
