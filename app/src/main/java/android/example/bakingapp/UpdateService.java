package android.example.bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class UpdateService extends IntentService {
    public static final String UPDATE_ACTION = "UPDATE_ACTION";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public UpdateService(String name) {
        super( name );
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (UPDATE_ACTION.equals(action)) {
                handleUpdateAction();
            }
        }

    }

    public static void ActionUpdateWidgets(Context context) {
        Intent intent = new Intent(context, UpdateService.class);
        intent.setAction("UPDATE_ACTION");
        context.startService(intent);
    }


    private void handleUpdateAction() {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
     int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingWidgetProvider.class));

       SharedPreferences sharedPreferences =getApplicationContext().getSharedPreferences("MyData", MODE_PRIVATE);
        String name = sharedPreferences.getString("name","");
        String ingredient = sharedPreferences.getString("ingredient","");



        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.baking_widget_provider);
        remoteViews.setTextViewText(R.id.appwidget_text, name);
        remoteViews.setTextViewText( R.id.ingredient_widget,ingredient );

        ComponentName thisWidget = new ComponentName(this, BakingWidgetProvider.class);

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_text);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.ingredient_widget);
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);




       BakingWidgetProvider.updateAppWidget(getApplicationContext(), appWidgetManager, appWidgetIds);

    }
}
