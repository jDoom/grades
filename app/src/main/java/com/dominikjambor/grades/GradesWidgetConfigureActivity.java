package com.dominikjambor.grades;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * The configuration screen for the {@link GradesWidget GradesWidget} AppWidget.
 */
public class GradesWidgetConfigureActivity extends Activity {

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private static final String PREFS_NAME = "com.dominikjambor.grades.GradesWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";

    public GradesWidgetConfigureActivity() {
        super();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.grades_widget_configure);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        final Spinner themeSpinner = (Spinner)findViewById(R.id.wcThemelist);
        final Spinner actionSpinner = (Spinner)findViewById(R.id.wcActionlist);
        Button okButton = (Button)findViewById(R.id.wcOkButton);

        ArrayAdapter<String> ta = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item);
        ta.add("Sötét");
        ta.add("Világos");
        themeSpinner.setAdapter(ta);

        ArrayAdapter<String> aa = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item);
        aa.add("Tantárgy megnyitása");
        aa.add("Jegy hozzáadása");
        actionSpinner.setAdapter(aa);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Context context = GradesWidgetConfigureActivity.this;
                saveTitlePref(context, mAppWidgetId,"theme",String.valueOf(themeSpinner.getSelectedItemPosition()));
                saveTitlePref(context,mAppWidgetId,"action",String.valueOf(actionSpinner.getSelectedItemPosition()));

                // It is the responsibility of the configuration activity to update the app widget
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                GradesWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

                // Make sure we pass back the original appWidgetId
                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                setResult(RESULT_OK, resultValue);
                finish();
            }
        });
    }

    // Write the prefix to the SharedPreferences object for this widget
    static void saveTitlePref(Context context, int appWidgetId,String prefname, String text) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId+prefname, text);
        prefs.commit();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static String loadTitlePref(Context context, int appWidgetId,String prefname) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String titleValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId+prefname, null);
        if (titleValue != null) {
            return titleValue;
        } else {
            return "0";
        }
    }

    static void deleteTitlePref(Context context, int appWidgetId,String prefname) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId+prefname);
        prefs.commit();
    }
}

