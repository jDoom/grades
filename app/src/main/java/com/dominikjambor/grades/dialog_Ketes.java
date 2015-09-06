package com.dominikjambor.grades;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

/**
 * Created by Dominik on 4/7/2015.
 */
public class dialog_Ketes extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_ketes, null);
        getDialog().setTitle("Kétes határok");

        final NumberPicker low = (NumberPicker) view.findViewById(R.id.lowEndNumberPicker);
        final NumberPicker high = (NumberPicker) view.findViewById(R.id.highEndNumberPicker);
        low.setMinValue(0);
        low.setMaxValue(6);
        low.setValue(Settings.ketesMin);
        low.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        high.setMinValue(0);
        high.setMaxValue(6);
        high.setValue(Settings.ketesMax);
        high.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        String[] t = new String[]{"x.35","x.40","x.45","x.50","x.55","x.60","x.65"};
        low.setDisplayedValues(t);
        high.setDisplayedValues(t);

        Button ok = (Button) view.findViewById(R.id.ketesOk);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings.ketesMax=high.getValue();
                Settings.ketesMin=low.getValue();
                Settings.SaveAll(MainActivity.cx);
                fragment_settings.update();
                dismiss();
            }
        });

        return view;
    }
}
