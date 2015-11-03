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
public class dialog_TantargySzerkeszto extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_tantargyszerkeszto, null);
        getDialog().setTitle("Tantárgy szerkesztése");
        fragment_tantargyak.fab.hide();
        //getDialog().getWindow().setBackgroundDrawableResource(R.drawable.diag_box);
        final EditText tantargyNev = (EditText) view.findViewById(R.id.tantargyEditText);
        tantargyNev.setText(Settings.tantargyak[fragment_tantargyak.szerkesztid].nev);
        Button hozzaad = (Button) view.findViewById(R.id.tantargySzerkesztButton);
        hozzaad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nev = tantargyNev.getText().toString();
                if(nev.isEmpty()){
                    Toast.makeText(view.getContext(), "Adj nevet a tantárgynak!",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Settings.tantargyak[fragment_tantargyak.szerkesztid].nev = nev;
                    Settings.tantargyList.set(fragment_tantargyak.szerkesztid,nev);
                    fragment_tantargyak.createList();
                    Settings.SaveAll(getActivity().getApplicationContext());
                    dismiss();
                    fragment_tantargyak.fab.show();
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
                                Toast.makeText(view.getContext(), Settings.tantargyak[fragment_tantargyak.szerkesztid].nev+" jegyei törölve.",
                                        Toast.LENGTH_SHORT).show();
                                Settings.tantargyak[fragment_tantargyak.szerkesztid].jegyekSzama=0;
                                Settings.SaveAll(view.getContext());
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("'"+Settings.tantargyak[fragment_tantargyak.szerkesztid].nev+"' jegyeinek törlése?").setPositiveButton("Igen", dialogClickListener)
                        .setNegativeButton("Nem", dialogClickListener).show();
            }
        });
        Button ttorol = (Button) view.findViewById(R.id.tDelButton);
        ttorol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Toast.makeText(view.getContext(), Settings.tantargyak[fragment_tantargyak.szerkesztid].nev+" törölve.",
                                        Toast.LENGTH_SHORT).show();
                                for(int i=fragment_tantargyak.szerkesztid;i<Settings.tantargyakSzama-1;i++)
                                {
                                    Settings.tantargyak[i]=Settings.tantargyak[i+1];
                                }
                                Settings.tantargyakSzama--;
                                Settings.tantargyList.remove(fragment_tantargyak.szerkesztid);
                                fragment_tantargyak.createList();
                                Settings.SaveAll(view.getContext());
                                dismiss();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("'"+Settings.tantargyak[fragment_tantargyak.szerkesztid].nev+"' törlése?").setPositiveButton("Igen", dialogClickListener)
                        .setNegativeButton("Nem", dialogClickListener).show();
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragment_tantargyak.fab.show();
    }
}
