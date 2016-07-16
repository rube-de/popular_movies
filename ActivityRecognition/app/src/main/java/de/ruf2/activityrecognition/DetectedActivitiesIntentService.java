package de.ruf2.activityrecognition;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.ArrayList;

/**
 * Created by Bernhard Ruf on 19.06.2016.
 */
public class DetectedActivitiesIntentService extends IntentService{
    protected static final String TAG = DetectedActivitiesIntentService.class.getSimpleName();

    public DetectedActivitiesIntentService(){
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
        Intent localIntent = new Intent(Constants.BROADCAST_ACTION);
        ArrayList<DetectedActivity> probableActivities = (ArrayList<DetectedActivity>) result.getProbableActivities();
        Log.i(TAG, "activities detected");
        localIntent.putExtra(Constants.ACTIVITY_EXTRA, probableActivities);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);

    }
}
