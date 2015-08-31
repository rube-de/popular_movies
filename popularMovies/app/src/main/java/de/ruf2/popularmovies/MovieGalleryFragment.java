package de.ruf2.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created by Bernhard Ruf on 23.08.2015.
 */
public class MovieGalleryFragment extends Fragment {
    public static final String MOVIES_KEY = "moviesKey";
    public static final String RELEASE_DATE_DESC = "release_date.desc";
    public static final String VOTE_AVERAGE_DESC = "vote_average.desc";
    public static final String POPULARITY_DESC = "popularity.desc";

    private String LOG_TAG = FetchMovieGalleryTask.class.getSimpleName();
    private ArrayAdapter<String> mMoviesAdapter;
    private ArrayList<String> mListOfMovies;

    public MovieGalleryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        if(savedInstance != null){
            mListOfMovies = (ArrayList<String>) savedInstance.get(MOVIES_KEY);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        setHasOptionsMenu(true);

        if(mListOfMovies==null){
            mListOfMovies = new ArrayList<String>();
        }
        //Take data from source and populate grid view
        mMoviesAdapter = new MovieArrayAdapter(
                getActivity(),
                R.layout.grid_item_movie,
                R.id.grid_item_imageView,
                mListOfMovies);

        //get reference of grid view and attach adapter
        GridView gridView = (GridView) rootView.findViewById(R.id.gridView_movies);
        gridView.setAdapter(mMoviesAdapter);
        //set onclick listener to get to details screen
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String movie = mMoviesAdapter.getItem(position);
                Intent detailsIntent = new Intent(getActivity(), DetailActivity.class).putExtra(Intent.EXTRA_TEXT, movie);
                startActivity(detailsIntent);
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovieGallery();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //add menu options to action bar
        inflater.inflate(R.menu.moviegalleryfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle action bar item clicks
        switch (item.getItemId()) {
            case R.id.action_refresh:
                updateMovieGallery();
                return true;
            case R.id.action_sort_by_release:
                changeOrderBy(RELEASE_DATE_DESC);
                updateMovieGallery();

                return true;
            case R.id.action_sort_by_rating:
                changeOrderBy(VOTE_AVERAGE_DESC);
                updateMovieGallery();
                return true;
            case R.id.action_sort_by_popularity:
                changeOrderBy(POPULARITY_DESC);
                updateMovieGallery();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void changeOrderBy(String orderBy) {
        SharedPreferences settings = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        // reverse ordering if user selects same order
        // not in use due too much null values in json
        //        if (settings.getString(MainActivity.ORDER_BY, POPULARITY_DESC).equals(orderBy)) {
        //            orderBy = orderBy.substring(0, orderBy.length() - 5) + ".asc";
        //        }
        editor.putString(MainActivity.ORDER_BY, orderBy);
        Log.d(LOG_TAG, "order by: " + orderBy);
        editor.commit();
    }

    public void updateMovieGallery() {
        Log.d(LOG_TAG, "update gallery");
        FetchMovieGalleryTask fetchMovieGalleryTask = new FetchMovieGalleryTask(getActivity(), mMoviesAdapter);
        fetchMovieGalleryTask.execute();
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(MOVIES_KEY, mListOfMovies);
    }
}
