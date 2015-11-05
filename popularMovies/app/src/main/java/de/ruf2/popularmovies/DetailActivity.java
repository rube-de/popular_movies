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

            Bundle arguments = new Bundle();
            arguments.putParcelable(MovieGalleryFragment.EXTRA_MOVIE, getIntent().getParcelableExtra(MovieGalleryFragment.EXTRA_MOVIE));
            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(arguments);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.movie_detail_container, fragment);
            fragmentTransaction.commit();
        }
    }


}
