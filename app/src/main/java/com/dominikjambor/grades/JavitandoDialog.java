package com.dominikjambor.grades;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

/**
 * Created by Dominik on 1/24/2015.
 */
public class JavitandoDialog extends DialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_javitando, null);
        getDialog().setTitle("Javítandó határ");
        final NumberPicker numberPicker = (NumberPicker) view.findViewById(R.id.javitandoPicker);
        Button ok =(Button) view.findViewById(R.id.diagOk);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float d = numberPicker.getValue();
                Settings.javitHatar=(d/10)+1;
                menu3_fragment.update();
                dismiss();
                MainActivity.SaveAll();
            }
        });
        String values[] = new String[50];

        for(int i=10;i<60;i++){
            values[i-10]=String.valueOf((float) i/10);
        }
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(40);
        numberPicker.setValue(40);
        numberPicker.setActivated(false);
        numberPicker.setDisplayedValues(values);
        numberPicker.setWrapSelectorWheel(false);
        return view;
    }
}
