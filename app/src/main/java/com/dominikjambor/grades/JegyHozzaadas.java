package com.dominikjambor.grades;

import android.animation.Animator;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class JegyHozzaadas extends ActionBarActivity {
    int tanid, jegyid, felev = 0;
    boolean szerk = false, tantargyAdd = false, tnezet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.transition_dash_to_subject));
        }
        setContentView(R.layout.activity_jegyhozzaadas);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        if (getIntent().hasExtra("FROMWIDGET")) {
            Settings.LoadSavedData(this);
            Settings.updateTantargyList();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        final Button felevButton = (Button) findViewById(R.id.felevButton);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, Settings.tantargyList);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!szerk) {
                    felevButton.setText("Félév: " + String.valueOf(Settings.tantargyak[spinner.getSelectedItemPosition()].felev + 1));
                    felev = Settings.tantargyak[spinner.getSelectedItemPosition()].felev;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        final int aDelay = 150;

        final Button datumButton = (Button) findViewById(R.id.datumButton);
        final CheckBox fontosCheckBox = (CheckBox) findViewById(R.id.fontosCheckBox);
        final EditText megjegyText = (EditText) findViewById(R.id.megjegyText);

        final ImageView j1 = (ImageView) findViewById(R.id.j1);
        final ImageView j2 = (ImageView) findViewById(R.id.j2);
        final ImageView j3 = (ImageView) findViewById(R.id.j3);
        final ImageView j4 = (ImageView) findViewById(R.id.j4);
        final ImageView j5 = (ImageView) findViewById(R.id.j5);


        felevButton.setText("Félév: 1");
        felevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (felev == 1) {
                    felev = 0;
                } else {
                    felev = 1;
                }
                felevButton.setText("Félév: " + String.valueOf(felev + 1));
            }
        });

        final DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String txt = year + ".";
                if (monthOfYear + 1 < 10)
                    txt += "0";
                txt += monthOfYear + 1 + ".";
                if (dayOfMonth < 10)
                    txt += "0";
                txt += dayOfMonth;
                datumButton.setText(txt);
            }
        };

        final Calendar calendar = Calendar.getInstance();

        datumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(JegyHozzaadas.this, listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        String txt = calendar.get(Calendar.YEAR) + ".";
        if (calendar.get(Calendar.MONTH) + 1 < 10)
            txt += "0";
        txt += calendar.get(Calendar.MONTH) + 1 + ".";
        if (calendar.get(Calendar.DAY_OF_MONTH) < 10)
            txt += "0";
        txt += calendar.get(Calendar.DAY_OF_MONTH);
        datumButton.setText(txt);

        j1.setAlpha(0.3f);
        j2.setAlpha(0.3f);
        j3.setAlpha(0.3f);
        j4.setAlpha(0.3f);
        j5.setAlpha(0.3f);

        j1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlphaAnimation anim = new AlphaAnimation(j1.getAlpha(), 1.0f);
                anim.setDuration(aDelay);
                anim.setFillAfter(true);
                j1.setAlpha(1.0f);
                j1.startAnimation(anim);


                anim = new AlphaAnimation(j2.getAlpha(), 0.4f);
                anim.setDuration(aDelay);
                anim.setFillAfter(true);
                j2.setAlpha(0.4f);
                j2.startAnimation(anim);


                anim = new AlphaAnimation(j3.getAlpha(), 0.4f);
                anim.setDuration(aDelay);
                anim.setFillAfter(true);
                j3.setAlpha(0.4f);
                j3.startAnimation(anim);


                anim = new AlphaAnimation(j4.getAlpha(), 0.4f);
                anim.setDuration(aDelay);
                anim.setFillAfter(true);
                j4.setAlpha(0.4f);
                j4.startAnimation(anim);


                anim = new AlphaAnimation(j5.getAlpha(), 0.4f);
                anim.setDuration(aDelay);
                anim.setFillAfter(true);
                j5.setAlpha(0.4f);
                j5.startAnimation(anim);


            }
        });
        j2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlphaAnimation anim = new AlphaAnimation(j2.getAlpha(), 1.0f);
                anim.setDuration(aDelay);
                anim.setFillAfter(true);
                j2.setAlpha(1.0f);
                j2.startAnimation(anim);


                anim = new AlphaAnimation(j1.getAlpha(), 0.4f);
                anim.setDuration(aDelay);
                anim.setFillAfter(true);
                j1.setAlpha(0.4f);
                j1.startAnimation(anim);


                anim = new AlphaAnimation(j3.getAlpha(), 0.4f);
                anim.setDuration(aDelay);
                anim.setFillAfter(true);
                j3.setAlpha(0.4f);
                j3.startAnimation(anim);


                anim = new AlphaAnimation(j4.getAlpha(), 0.4f);
                anim.setDuration(aDelay);
                anim.setFillAfter(true);
                j4.setAlpha(0.4f);
                j4.startAnimation(anim);


                anim = new AlphaAnimation(j5.getAlpha(), 0.4f);
                anim.setDuration(aDelay);
                anim.setFillAfter(true);
                j5.setAlpha(0.4f);
                j5.startAnimation(anim);


            }
        });
        j3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlphaAnimation anim = new AlphaAnimation(j3.getAlpha(), 1.0f);
                anim.setDuration(aDelay);
                anim.setFillAfter(true);
                j3.setAlpha(1.0f);
                j3.startAnimation(anim);


                anim = new AlphaAnimation(j2.getAlpha(), 0.4f);
                anim.setDuration(aDelay);
                anim.setFillAfter(true);
                j2.setAlpha(0.4f);
                j2.startAnimation(anim);


                anim = new AlphaAnimation(j1.getAlpha(), 0.4f);
                anim.setDuration(aDelay);
                anim.setFillAfter(true);
                j1.setAlpha(0.4f);
                j1.startAnimation(anim);


                anim = new AlphaAnimation(j4.getAlpha(), 0.4f);
                anim.setDuration(aDelay);
                anim.setFillAfter(true);
                j4.setAlpha(0.4f);
                j4.startAnimation(anim);


                anim = new AlphaAnimation(j5.getAlpha(), 0.4f);
                anim.setDuration(aDelay);
                anim.setFillAfter(true);
                j5.setAlpha(0.4f);
                j5.startAnimation(anim);


            }
        });
        j4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlphaAnimation anim = new AlphaAnimation(j4.getAlpha(), 1.0f);
                anim.setDuration(aDelay);
                anim.setFillAfter(true);
                j4.setAlpha(1.0f);
                j4.startAnimation(anim);


                anim = new AlphaAnimation(j2.getAlpha(), 0.4f);
                anim.setDuration(aDelay);
                anim.setFillAfter(true);
                j2.setAlpha(0.4f);
                j2.startAnimation(anim);


                anim = new AlphaAnimation(j3.getAlpha(), 0.4f);
                anim.setDuration(aDelay);
                anim.setFillAfter(true);
                j3.setAlpha(0.4f);
                j3.startAnimation(anim);


                anim = new AlphaAnimation(j1.getAlpha(), 0.4f);
                anim.setDuration(aDelay);
                anim.setFillAfter(true);
                j1.setAlpha(0.4f);
                j1.startAnimation(anim);


                anim = new AlphaAnimation(j5.getAlpha(), 0.4f);
                anim.setDuration(aDelay);
                anim.setFillAfter(true);
                j5.setAlpha(0.4f);
                j5.startAnimation(anim);


            }
        });
        j5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlphaAnimation anim = new AlphaAnimation(j5.getAlpha(), 1.0f);
                anim.setDuration(aDelay);
                anim.setFillAfter(true);
                j5.setAlpha(1.0f);
                j5.startAnimation(anim);


                anim = new AlphaAnimation(j2.getAlpha(), 0.4f);
                anim.setDuration(aDelay);
                anim.setFillAfter(true);
                j2.setAlpha(0.4f);
                j2.startAnimation(anim);


                anim = new AlphaAnimation(j3.getAlpha(), 0.4f);
                anim.setDuration(aDelay);
                anim.setFillAfter(true);
                j3.setAlpha(0.4f);
                j3.startAnimation(anim);


                anim = new AlphaAnimation(j4.getAlpha(), 0.4f);
                anim.setDuration(aDelay);
                anim.setFillAfter(true);
                j4.setAlpha(0.4f);
                j4.startAnimation(anim);


                anim = new AlphaAnimation(j1.getAlpha(), 0.4f);
                anim.setDuration(aDelay);
                anim.setFillAfter(true);
                j1.setAlpha(0.4f);
                j1.startAnimation(anim);


            }
        });

        Button ok = (Button) findViewById(R.id.jegyHozzaAd);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (j1.getAlpha() == 1) {
                    hozzaad(spinner, datumButton, megjegyText, fontosCheckBox, 1);
                } else if (j2.getAlpha() == 1) {
                    hozzaad(spinner, datumButton, megjegyText, fontosCheckBox, 2);
                } else if (j3.getAlpha() == 1) {
                    hozzaad(spinner, datumButton, megjegyText, fontosCheckBox, 3);
                } else if (j4.getAlpha() == 1) {
                    hozzaad(spinner, datumButton, megjegyText, fontosCheckBox, 4);
                } else if (j5.getAlpha() == 1) {
                    hozzaad(spinner, datumButton, megjegyText, fontosCheckBox, 5);
                } else {
                    Toast.makeText(getApplicationContext(), "Válassz jegyet!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        ImageView del = (ImageView) findViewById(R.id.delimage);
        if (getIntent().hasExtra("TANTARGY")) {
            spinner.setSelection(getIntent().getExtras().getInt("TANTARGY"));
            tnezet = getIntent().hasExtra("TNEZET");
            felev = Settings.tantargyak[getIntent().getExtras().getInt("TANTARGY")].felev;
            felevButton.setText("Félév: " + String.valueOf(Settings.tantargyak[getIntent().getExtras().getInt("TANTARGY")].felev + 1));
            tantargyAdd = true;
        }
        if (getIntent().hasExtra("SZERKESZTES")) {
            tanid = getIntent().getExtras().getInt("TANTARGY");
            jegyid = getIntent().getExtras().getInt("JEGY");

            switch (Settings.tantargyak[tanid].jegyek[jegyid].getErtek()) {
                case 1:
                    j1.callOnClick();
                    break;
                case 2:
                    j2.callOnClick();
                    break;
                case 3:
                    j3.callOnClick();
                    break;
                case 4:
                    j4.callOnClick();
                    break;
                case 5:
                    j5.callOnClick();
                    break;
            }
            felevButton.setText("Félév: " + String.valueOf(Settings.tantargyak[tanid].jegyek[jegyid].getFelev()+1));
            felev = Settings.tantargyak[tanid].jegyek[jegyid].getFelev();
            datumButton.setText(Settings.tantargyak[tanid].jegyek[jegyid].getDatum());
            megjegyText.setText(Settings.tantargyak[tanid].jegyek[jegyid].getMegjegyzes());
            fontosCheckBox.setChecked(Settings.tantargyak[tanid].jegyek[jegyid].isFontos());
            getIntent().putExtra("SZERKESZTES", false);
            szerk = true;
            spinner.setEnabled(false);
            setTitle("Jegy szerkesztése");
            del.setVisibility(View.VISIBLE);
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    Toast.makeText(getApplicationContext(), "Jegy törölve.",
                                            Toast.LENGTH_SHORT).show();
                                    Settings.tantargyak[tanid].jegyekSzama--;
                                    for (int i = jegyid; i < Settings.tantargyak[tanid].jegyekSzama; i++) {
                                        Settings.tantargyak[tanid].jegyek[i] = Settings.tantargyak[tanid].jegyek[i + 1];
                                    }
                                    Settings.SaveAll(getApplicationContext());
                                    fragment_main.update();
                                    if (Settings.tantargyak[tanid].jegyekSzama == 0)
                                        TantargyNezetActivity.dis.finish();
                                    else
                                        TantargyNezetActivity.update();
                                    finish();
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(JegyHozzaadas.this);
                    builder.setMessage("Biztosan törlöd ezt a jegyet?").setPositiveButton("Igen", dialogClickListener)
                            .setNegativeButton("Nem", dialogClickListener).show();
                }
            });
        } else {
            del.setVisibility(View.INVISIBLE);
        }
        final View myView = findViewById(R.id.scrollView2);
        myView.post(new Runnable() {
            @Override
            public void run() {
                int cx = myView.getWidth() - 100;
                int cy = myView.getHeight() - 100;

                int finalRadius = Math.max(myView.getWidth(), myView.getHeight());

                Animator anim =
                        null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);
                    //anim.setDuration(500);
                    anim.start();
                }
            }
        });


// get the center for the clipping circle

    }

    void hozzaad(Spinner spinner, Button datumButton, EditText editText, CheckBox checkBox, int jegy) {
        if (szerk) {
            Settings.tantargyak[spinner.getSelectedItemPosition()].jegyek[jegyid] = new Jegy();
            Settings.tantargyak[spinner.getSelectedItemPosition()].jegyek[jegyid].setErtek(jegy);
            Settings.tantargyak[spinner.getSelectedItemPosition()].jegyek[jegyid].setDatum(datumButton.getText().toString());
            if (!editText.getText().toString().isEmpty()) {
                Settings.tantargyak[spinner.getSelectedItemPosition()].jegyek[jegyid].setVanMegjegyzes(true);
                Settings.tantargyak[spinner.getSelectedItemPosition()].jegyek[jegyid].setMegjegyzes(editText.getText().toString());
            }
            Settings.tantargyak[spinner.getSelectedItemPosition()].jegyek[jegyid].setFontos(checkBox.isChecked());
            Settings.tantargyak[spinner.getSelectedItemPosition()].jegyek[jegyid].setFelev(felev);
        } else {
            Settings.tantargyak[spinner.getSelectedItemPosition()].jegyek[Settings.tantargyak[spinner.getSelectedItemPosition()].jegyekSzama] = new Jegy();
            Settings.tantargyak[spinner.getSelectedItemPosition()].jegyek[Settings.tantargyak[spinner.getSelectedItemPosition()].jegyekSzama].setErtek(jegy);
            Settings.tantargyak[spinner.getSelectedItemPosition()].jegyek[Settings.tantargyak[spinner.getSelectedItemPosition()].jegyekSzama].setDatum(datumButton.getText().toString());
            if (!editText.getText().toString().isEmpty()) {
                Settings.tantargyak[spinner.getSelectedItemPosition()].jegyek[Settings.tantargyak[spinner.getSelectedItemPosition()].jegyekSzama].setVanMegjegyzes(true);
                Settings.tantargyak[spinner.getSelectedItemPosition()].jegyek[Settings.tantargyak[spinner.getSelectedItemPosition()].jegyekSzama].setMegjegyzes(editText.getText().toString());
            }
            Settings.tantargyak[spinner.getSelectedItemPosition()].jegyek[Settings.tantargyak[spinner.getSelectedItemPosition()].jegyekSzama].setFontos(checkBox.isChecked());
            Settings.tantargyak[spinner.getSelectedItemPosition()].jegyek[Settings.tantargyak[spinner.getSelectedItemPosition()].jegyekSzama].setFelev(felev);
            Settings.tantargyak[spinner.getSelectedItemPosition()].jegyekSzama++;
        }
        for (int i = 0; i < Settings.tantargyak[spinner.getSelectedItemPosition()].jegyekSzama; i++)
            for (int n = 0; n < Settings.tantargyak[spinner.getSelectedItemPosition()].jegyekSzama; n++) {
                String dateString1 = Settings.tantargyak[spinner.getSelectedItemPosition()].jegyek[i].getDatum();
                String dateString2 = Settings.tantargyak[spinner.getSelectedItemPosition()].jegyek[n].getDatum();
                Date date1 = null, date2 = null;
                DateFormat format = new SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH);
                try {
                    date1 = format.parse(dateString1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    date2 = format.parse(dateString2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (date1.after(date2)) {
                    Jegy tmp = Settings.tantargyak[spinner.getSelectedItemPosition()].jegyek[i];
                    Settings.tantargyak[spinner.getSelectedItemPosition()].jegyek[i] = Settings.tantargyak[spinner.getSelectedItemPosition()].jegyek[n];
                    Settings.tantargyak[spinner.getSelectedItemPosition()].jegyek[n] = tmp;
                }
                if(Settings.tantargyak[spinner.getSelectedItemPosition()].jegyek[i].getFelev()>Settings.tantargyak[spinner.getSelectedItemPosition()].jegyek[n].getFelev()){
                    Jegy tmp = Settings.tantargyak[spinner.getSelectedItemPosition()].jegyek[i];
                    Settings.tantargyak[spinner.getSelectedItemPosition()].jegyek[i] = Settings.tantargyak[spinner.getSelectedItemPosition()].jegyek[n];
                    Settings.tantargyak[spinner.getSelectedItemPosition()].jegyek[n] = tmp;
                }
            }
        Settings.SaveAll(this);
        if (szerk) {
            TantargyNezetActivity.update();
        } else if (tantargyAdd && tnezet && !getIntent().hasExtra("FROMWIDGET")) {
            TantargyNezetActivity.update();
        }
        if (!getIntent().hasExtra("FROMWIDGET")) {
            fragment_main.update();
        }
        if (!getIntent().hasExtra("FROMWIDGET"))
            fragment_main.animView.setVisibility(View.INVISIBLE);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (!getIntent().hasExtra("FROMWIDGET"))
            fragment_main.animView.setVisibility(View.INVISIBLE);
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (!getIntent().hasExtra("FROMWIDGET"))
            fragment_main.animView.setVisibility(View.INVISIBLE);
        super.onBackPressed();
    }
}
