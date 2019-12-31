package android.example.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.example.bakingapp.model.Ingredient;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.drm.DrmStore.Action.DEFAULT;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString( R.string.appwidget_text );
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews( context.getPackageName(), R.layout.baking_widget_provider );


        UpdateService.ActionUpdateWidgets(context);



        SharedPreferences sharedPreferences =context.getSharedPreferences("MyData", MODE_PRIVATE);
        String name = sharedPreferences.getString("name","");
        String ingredient = sharedPreferences.getString("ingredient","");


        Intent intent =new Intent( context,MainActivity.class );

        PendingIntent pendingIntent=PendingIntent.getActivity( context,0,intent,0 );
        views.setOnClickPendingIntent( R.id.appwidget_text,pendingIntent );
        views.setOnClickPendingIntent( R.id.ingredient_widget,pendingIntent );





        views.setTextViewText( R.id.appwidget_text, name);
        views.setTextViewText( R.id.ingredient_widget,ingredient );



        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget( appWidgetId, views );
    }

    @Override
    public  void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget( context, appWidgetManager, appWidgetId );
            UpdateService.ActionUpdateWidgets( context );

        }


    }


    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {


        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

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

}

