package com.dominikjambor.grades;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by j-dom on 8/16/2015.
 */
public class WidgetService extends RemoteViewsService
{

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent)
    {
        return (new WidgetViewsFactory(this.getApplicationContext(), intent));
    }
}