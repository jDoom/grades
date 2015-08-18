package com.dominikjambor.grades;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Dominik on 1/31/2015.
 */
public class TantargySzerkesztoDialog extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_tantargyszerkeszto, null);
        getDialog().setTitle("Tantárgy szerkesztése");
        menu2_fragment.fab.hide();
        //getDialog().getWindow().setBackgroundDrawableResource(R.drawable.diag_box);
        final EditText tantargyNev = (EditText) view.findViewById(R.id.tantargyEditText);
        tantargyNev.setText(Settings.tantargyak[menu2_fragment.szerkesztid].nev);
        Button hozzaad = (Button) view.findViewById(R.id.tantargyHozzaadButton);
        hozzaad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nev = tantargyNev.getText().toString();
                if(nev.isEmpty()){
                    Toast.makeText(view.getContext(), "Adj nevet a tantárgynak!",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Settings.tantargyak[menu2_fragment.szerkesztid].nev = nev;
                    Settings.tantargyList.set(menu2_fragment.szerkesztid,nev);
                    menu2_fragment.createList();
                    dismiss();
                    menu2_fragment.fab.show();
                    MainActivity.SaveAll();
                }
            }
        });
        Button torol = (Button) view.findViewById(R.id.jegytorol);
        torol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Toast.makeText(view.getContext(), Settings.tantargyak[menu2_fragment.szerkesztid].nev+" jegyei törölve.",
                                        Toast.LENGTH_SHORT).show();
                                Settings.tantargyak[menu2_fragment.szerkesztid].jegyekSzama=0;
                                MainActivity.SaveAll();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("'"+Settings.tantargyak[menu2_fragment.szerkesztid].nev+"' jegyeinek törlése?").setPositiveButton("Igen", dialogClickListener)
                        .setNegativeButton("Nem", dialogClickListener).show();
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        menu2_fragment.fab.show();
    }
}
