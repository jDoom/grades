package com.dominikjambor.grades;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link GradesWidgetConfigureActivity GradesWidgetConfigureActivity}
 */
public class GradesWidget extends AppWidgetProvider {
    public static final String TOAST_ACTION = "com.dominikjambor.grades.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.dominikjambor.grades.EXTRA_ITEM";


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            appWidgetManager.updateAppWidget(appWidgetIds[i], null);
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }

    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            GradesWidgetConfigureActivity.deleteTitlePref(context, appWidgetIds[i]);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }


    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
    private static class MyListAdapter extends ArrayAdapter<String>{

        public MyListAdapter(Context context) {
            super(context,R.layout.itemlayout_main,Settings.tantargyList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                LayoutInflater inflater = (LayoutInflater)getContext().getSystemService
                        (Context.LAYOUT_INFLATER_SERVICE);
                itemView = inflater.inflate(R.layout.itemlayout_widgetlistview_tl, parent, false);
            }
            TextView tnev = (TextView) itemView.findViewById(R.id.wTantargyNevText);
            tnev.setText(Settings.tantargyak[position].nev);


            return itemView;
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = GradesWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.grades_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);
        Settings.LoadSavedData(context);
        Settings.updateTantargyList();
        views.setTextViewText(R.id.widgetTitleText, "Tantárgyak");

        Intent intent = new Intent(context, WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        views.setRemoteAdapter(R.id.wListView, intent);

        /*Intent startApp = new Intent(context,MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context,0,startApp,0);
        views.setOnClickPendingIntent(R.id.widgetTitleText, pIntent);*/
        Intent clickIntent = new Intent(context, MainActivity.class);
        clickIntent.setAction(GradesWidget.TOAST_ACTION);
        clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        clickIntent.setData(Uri.parse(clickIntent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent clickPI = PendingIntent.getActivity(context, 0,
                clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        views.setPendingIntentTemplate(R.id.wListView, clickPI);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }
}

