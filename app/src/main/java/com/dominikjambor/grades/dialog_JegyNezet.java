package com.dominikjambor.grades;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Dominik on 1/31/2015.
 */
public class dialog_JegyNezet extends DialogFragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_jegyinfo, null);
        getDialog().setTitle("Jegy információk");
        TextView tanText = (TextView) view.findViewById(R.id.jegyViewTantargyText);
        tanText.setText("Tantárgy: "+Settings.tantargyak[TantargyNezetActivity.tantargyid].nev);
        TextView datumText = (TextView) view.findViewById(R.id.jegyViewDatumText);
        datumText.setText("Dátum: "+Settings.tantargyak[TantargyNezetActivity.tantargyid].jegyek[TantargyNezetActivity.jegyid].getDatum());
        TextView fontosText = (TextView) view.findViewById(R.id.jegyViewFontosText);
        if(Settings.tantargyak[TantargyNezetActivity.tantargyid].jegyek[TantargyNezetActivity.jegyid].isFontos())
        {
            fontosText.setText("Fontos: Igen");
        }
        else
        {
            fontosText.setText("Fontos: Nem");
        }
        if(Settings.tantargyak[TantargyNezetActivity.tantargyid].jegyek[TantargyNezetActivity.jegyid].isVanMegjegyzes())
        {
            TextView megjegyText = (TextView) view.findViewById(R.id.jegyViewKommentText);
            megjegyText.setText(Settings.tantargyak[TantargyNezetActivity.tantargyid].jegyek[TantargyNezetActivity.jegyid].getMegjegyzes());
        }
        ImageView img = (ImageView) view.findViewById(R.id.jegyViewJegyImage);
        switch (Settings.tantargyak[TantargyNezetActivity.tantargyid].jegyek[TantargyNezetActivity.jegyid].getErtek()) {
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
        Button okButton = (Button) view.findViewById(R.id.jegyViewOkButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        Button szerkButton = (Button) view.findViewById(R.id.jegyViewSzerkButton);
        szerkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Intent intent = new Intent(view.getContext(), JegyHozzaadas.class);
                intent.putExtra("SZERKESZTES", true);
                intent.putExtra("TANTARGY", TantargyNezetActivity.tantargyid);
                intent.putExtra("JEGY", TantargyNezetActivity.jegyid);
                view.getContext().startActivity(intent);
            }
        });
        return view;
    }
}