package com.dominikjambor.grades;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Dominik on 4/7/2015.
 */
public class dialog_jegyszimulator extends DialogFragment {

    int[] jegyek = new int[200];
    int jegyekSzama=0;
    float atlag=0f;
    TextView atlagText,jegyekText;
    String jstring = "Jegyek: ";
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_jegyszimulator, null);
        getDialog().setTitle("Jegyszimulátor");
        getDialog().setCanceledOnTouchOutside(false);
        Button vissza = (Button) view.findViewById(R.id.jegyszimulatorBack);
        vissza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        atlagText =(TextView) view.findViewById(R.id.jszAtlag);
        jegyekText=(TextView) view.findViewById(R.id.jszJegyek);

        if(getArguments() != null){
            int[] jt  = getArguments().getIntArray("jegyek");
            jegyekSzama = getArguments().getInt("jegyszam");
            for(int i=0;i<jegyekSzama;i++){
                jegyek[i] = jt[i];
            }
            update();
        }
        Button b1 = (Button)view.findViewById(R.id.jsz1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addJegy(1);
                update();
            }
        });
        Button b2 = (Button)view.findViewById(R.id.jsz2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addJegy(2);
                update();
            }
        });
        Button b3 = (Button)view.findViewById(R.id.jsz3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addJegy(3);
                update();
            }
        });
        Button b4 = (Button)view.findViewById(R.id.jsz4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addJegy(4);
                update();
            }
        });
        Button b5 = (Button)view.findViewById(R.id.jsz5);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addJegy(5);
                update();
            }
        });
        Button bv = (Button)view.findViewById(R.id.jszt);
        bv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(jegyekSzama>0)
                    jegyekSzama--;
                update();
            }
        });

        return view;
    }
    void update(){
        float o=0;
        int j1=0,j2=0,j3=0,j4=0,j5=0;
        jstring = "Jegyek: ";
        for(int i=0;i<jegyekSzama;i++){
            jstring+=String.valueOf(jegyek[i])+" ";
            o=o+jegyek[i];
        }
        o=o/jegyekSzama;
        if(o>0) atlag = round(o,2);
        else atlag=0;
        atlagText.setText("Átlag: "+String.valueOf(atlag));
        jegyekText.setText(jstring);

    }
    float round(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }
    void addJegy(int value){
        if(jegyekSzama<199) {
            jegyek[jegyekSzama] = value;
            jegyekSzama++;
        }
    }
    }
