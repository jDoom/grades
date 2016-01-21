package com.dominikjambor.grades;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

/**
 * Created by Dominik on 4/7/2015.
 */
public class dialog_atlagszamito extends DialogFragment {
    Spinner elerendo;
    Spinner szerzendo;
    CheckBox lehetKetes;
    TextView eredmeny;
    int jegyekSzama;
    int[] jegyek = new int[100];
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_atlagszamito, null);
        getDialog().setTitle("Átlagszámító");
        getDialog().setCanceledOnTouchOutside(false);
        Button okButton = (Button) view.findViewById(R.id.akOk);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        if(getArguments() != null){
            int[] jt  = getArguments().getIntArray("jegyek");
            jegyekSzama = getArguments().getInt("jegyszam");
            for(int i=0;i<jegyekSzama;i++){
                jegyek[i] = jt[i];
            }
        }
        else{
            dismiss();
            Toast.makeText(getActivity().getApplicationContext(),"Hiba",Toast.LENGTH_SHORT);
        }
        elerendo = (Spinner) view.findViewById(R.id.akElerendo);
        szerzendo = (Spinner) view.findViewById(R.id.akSzerzendo);
        lehetKetes = (CheckBox) view.findViewById(R.id.akKetes);
        eredmeny = (TextView) view.findViewById(R.id.akEredmeny);

        lehetKetes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                calculate();
            }
        });

        ArrayAdapter<String> adp= new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1, Arrays.asList("1", "2", "3","4","5"));
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        elerendo.setAdapter(adp);
        szerzendo.setAdapter(adp);
        elerendo.setSelection(4);
        szerzendo.setSelection(4);
        AdapterView.OnItemSelectedListener s = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                calculate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        elerendo.setOnItemSelectedListener(s);
        szerzendo.setOnItemSelectedListener(s);
        calculate();
        return view;
    }
    void calculate(){
        float cel = elerendo.getSelectedItemPosition()+0.5f;
        if(!lehetKetes.isChecked()){
            cel+=(Settings.ketesMax-3)*0.05f;
        }
        if(szerzendo.getSelectedItemPosition()<elerendo.getSelectedItemPosition()){
            szerzendo.setSelection(elerendo.getSelectedItemPosition());
        }
        int kell=0;
        float atlag=getAvg(0);
        boolean tulSok=false;
        while(atlag<cel){
            jegyek[jegyekSzama+kell]=szerzendo.getSelectedItemPosition()+1;
            kell++;
            atlag=getAvg(kell);
            if(jegyekSzama+kell>98){
                tulSok=true;
                break;
            }
        }
        if(!tulSok)
            eredmeny.setText(String.valueOf(kell)+" kell még ebből a jegyből, így az átlag "+String.valueOf(atlag)+" lesz.");
        else
            eredmeny.setText("Reménytelen.");
        //Log.w("Cel: "+String.valueOf(cel),"Atlag: "+String.valueOf(atlag)+"Szerzendo: "+String.valueOf(szerzendo.getSelectedItemPosition()));
    }
    float getAvg(int k){
        float o=0,a;
        for(int i=0;i<jegyekSzama+k;i++){
            o=o+jegyek[i];
        }
        o=o/(jegyekSzama+k);
        if(o>0) a = round(o,2);
        else a=0;
        //Log.w("ATLAG",String.valueOf(a));
        return a;
    }
    float round(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }
}
