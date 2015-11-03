package com.dominikjambor.grades;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;


/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link GradesWidgetConfigureActivity GradesWidgetConfigureActivity}
 */
public class GradesWidget extends AppWidgetProvider {
    public static final String TOAST_ACTION = "com.dominikjambor.grades.TOAST_ACTION";


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            GradesWidgetConfigureActivity.deleteTitlePref(context, appWidgetIds[i],"theme");
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

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        int theme = Integer.parseInt(GradesWidgetConfigureActivity.loadTitlePref(context, appWidgetId,"theme"));
        // Construct the RemoteViews object
        RemoteViews views;
        if(theme==1){
            views = new RemoteViews(context.getPackageName(), R.layout.grades_widget_light);
        }
        else{
            views = new RemoteViews(context.getPackageName(), R.layout.grades_widget_dark);
        }

        //Settings.LoadSavedData(context);
        //Settings.updateTantargyList();
        Intent intent = new Intent(context, WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));


        views.setRemoteAdapter(R.id.wListView, intent);
        views.setTextViewText(R.id.widgetTitleText,"Tantárgyak");

        Intent startApp = new Intent(context,MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context,0,startApp,0);
        views.setOnClickPendingIntent(R.id.widgetTitleText, pIntent);
        int action = Integer.parseInt(GradesWidgetConfigureActivity.loadTitlePref(context, appWidgetId,"action"));
        Intent clickIntent;
        if(action==1){
            clickIntent = new Intent(context, JegyHozzaadas.class);
        }
        else{
            clickIntent = new Intent(context, MainActivity.class);
        }
        clickIntent.setAction(GradesWidget.TOAST_ACTION);
        clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        clickIntent.setData(Uri.parse(clickIntent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent clickPI = PendingIntent.getActivity(context, 0,
                clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        views.setPendingIntentTemplate(R.id.wListView, clickPI);

        // Instruct the widget manager to update the widget
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.wListView);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}

