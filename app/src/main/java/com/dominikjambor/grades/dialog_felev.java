package com.dominikjambor.grades;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by j-dom on 1/18/2016.
 */
public class dialog_felev extends DialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_felev, null);
        getDialog().setTitle("Félév");

        Button elsoButton = (Button) view.findViewById(R.id.elsoButton);
        Button masodikButton = (Button) view.findViewById(R.id.masodikButton);

        elsoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < Settings.tantargyakSzama; i++) {
                    Settings.tantargyak[i].felev = 0;
                }
                Toast.makeText(getActivity().getBaseContext(),"Első félév kollektívan beállítva.",Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
        masodikButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < Settings.tantargyakSzama; i++) {
                    Settings.tantargyak[i].felev = 1;
                }
                Toast.makeText(getActivity().getApplicationContext(),"Második félév kollektívan beállítva.",Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        return view;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Settings.SaveAll(getActivity().getApplicationContext());
        super.onDismiss(dialog);
    }
}