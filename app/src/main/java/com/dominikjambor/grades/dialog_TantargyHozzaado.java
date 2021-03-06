package com.dominikjambor.grades;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Dominik on 1/31/2015.
 */
public class dialog_TantargyHozzaado extends DialogFragment{
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_tantargyhozzaadas, null);
        getDialog().setTitle("Új tantárgy hozzáadása");
       // getDialog().getWindow().setBackgroundDrawableResource(R.drawable.diag_box);

        final EditText tantargyNev = (EditText) view.findViewById(R.id.tantargyEditText);
        Button hozzaad = (Button) view.findViewById(R.id.tantargyHozzaadButton);
        fragment_tantargyak.fab.hide();
        hozzaad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nev = tantargyNev.getText().toString();
                if(nev.isEmpty()){
                    Toast.makeText(view.getContext(), "Adj nevet a tantárgynak!",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Settings.tantargyak[Settings.tantargyakSzama] = new Tantargy(nev, 0);
                    Settings.tantargyakSzama++;
                    Settings.tantargyList.add(nev);
                    fragment_tantargyak.createList();
                    dismiss();
                    Settings.SaveAll(MainActivity.cx);
                    fragment_tantargyak.fab.show();
                }
            }
        });
        return view;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        fragment_tantargyak.fab.show();
        super.onDismiss(dialog);
    }
}
