package yohan.jkskingdom.com.jokesterskingdom;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by Yohan on 12/07/2018.
 */

//THIS WIDGET ACTIVITY WILL RETRIEVE THE LAST JOKE FROM THE FIRESTORE DATABASE AND PUT IN IN THE WIDGET TEXTVIEW

public class WidgetActivity extends AppCompatActivity {

    private AppWidgetManager widgetManager;
    private RemoteViews views;
    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseFirestore = FirebaseFirestore.getInstance();

        widgetManager = AppWidgetManager.getInstance(this);
        views = new RemoteViews(this.getPackageName(), R.layout.widget_provider);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        //RETRIEVE THE JOKE DATA TO THE WIDGET TEXTVIEW


        String last_joke_retreive = getIntent().getStringExtra("EXTRA_ID");

        views.setTextViewText(R.id.widget_joke,String.valueOf(last_joke_retreive));

        widgetManager.updateAppWidget(mAppWidgetId, views);
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();





    }



}
