package de.ruf2.popularmovies;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import de.ruf2.popularmovies.data.MovieData;
import de.ruf2.popularmovies.logging.LifecycleLoggingActionBarActivity;


public class MainActivity extends LifecycleLoggingActionBarActivity implements MovieGalleryFragment.Callback{
    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String ORDER_BY = "orderBy";
    private boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container, new DetailFragment(), DETAILFRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
        }
        if (savedInstanceState == null) {
            MovieGalleryFragment fragment = new MovieGalleryFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_gallery, fragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        MovieGalleryFragment mgf = (MovieGalleryFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_gallery);
    }

    @Override
    public void onItemSelected(MovieData movieData) {
        if (mTwoPane) {
            Bundle args = new Bundle();
            args.putParcelable(MovieGalleryFragment.EXTRA_MOVIE, movieData);

            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment, DETAILFRAGMENT_TAG)
                    .commit();
        } else {
            Intent detailsIntent = new Intent(this, DetailActivity.class).putExtra(MovieGalleryFragment.EXTRA_MOVIE, movieData);
            startActivity(detailsIntent);
        }
    }
}
