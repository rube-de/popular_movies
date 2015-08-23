package de.ruf2.popularmovies.logging;

/**
 * Created by Bernhard Ruf on 23.08.2015.
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * This abstract class extends the Activity class and overrides
 * lifecycle callbacks for logging various lifecycle events.
 */
public abstract class LifecycleLoggingActivity extends Activity {

    protected final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            Log.d(TAG,
                    "onCreate(): activity re-created");

        } else {
            Log.d(TAG,
                    "onCreate(): activity created anew");
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG,
                "onStart() - the activity is about to become visible");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG,
                "onResume() - the activity has become visible (it is now \"resumed\")");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG,
                "onPause() - another activity is taking focus (this activity is about to be \"paused\")");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG,
                "onStop() - the activity is no longer visible (it is now \"stopped\")");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG,
                "onRestart() - the activity is about to be restarted()");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG,
                "onDestroy() - the activity is about to be destroyed");
    }
}
