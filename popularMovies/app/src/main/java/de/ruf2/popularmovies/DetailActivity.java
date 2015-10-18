package de.ruf2.popularmovies;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import de.ruf2.popularmovies.logging.LifecycleLoggingActionBarActivity;

/**
 * Created by Bernhard Ruf on 23.08.2015.
 */
public class DetailActivity extends LifecycleLoggingActionBarActivity {
    protected final String TAG = getClass().getSimpleName();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            DetailFragment fragment = new DetailFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.detail_container, fragment);
            fragmentTransaction.commit();
        }
    }


}
