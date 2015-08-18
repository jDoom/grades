package com.dominikjambor.grades;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by j-dom on 8/16/2015.
 */
public class WidgetViewsFactory implements RemoteViewsService.RemoteViewsFactory
{
    private Context context = null;
    private int appWidgetId;

    public WidgetViewsFactory(Context context, Intent intent)
    {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }


    @Override
    public int getCount()
    {
        return Settings.tantargyList.size();
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public RemoteViews getLoadingView()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RemoteViews getViewAt(int position)
    {
        Log.d("WidgetCreatingView", "WidgetCreatingView");
        RemoteViews remoteView = new RemoteViews(context.getPackageName(),
                R.layout.itemlayout_widgetlistview_tl);
        remoteView.setTextViewText(R.id.wTantargyNevText, Settings.tantargyList.get(position).toString());
        remoteView.setTextViewText(R.id.wJegySzamText, String.valueOf(Settings.tantargyak[position].jegyekSzama));
        remoteView.setTextViewText(R.id.wAtlagText, String.valueOf(Settings.tantargyak[position].getAtlag(2)));

        Bundle extras = new Bundle();
        extras.putInt("pos", position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        // Make it possible to distinguish the individual on-click
        // action of a given item
        remoteView.setOnClickFillInIntent(R.id.rowl, fillInIntent);

        return remoteView;
    }

    @Override
    public int getViewTypeCount()
    {
        // TODO Auto-generated method stub
        return 1;
    }

    @Override
    public boolean hasStableIds()
    {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        Settings.updateTantargyList();
    }

    @Override
    public void onDataSetChanged()
    {
        // TODO Auto-generated method stub
        Settings.updateTantargyList();
    }

    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
    }
}