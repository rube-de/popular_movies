package de.ruf2.popularmovies;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import de.ruf2.popularmovies.adapter.MovieArrayAdapter;
import de.ruf2.popularmovies.data.MovieColumns;
import de.ruf2.popularmovies.data.MovieData;
import de.ruf2.popularmovies.data.MoviesProvider;

/**
 * Created by Bernhard Ruf on 23.08.2015.
 */
public class MovieGalleryFragment extends Fragment  implements LoaderManager.LoaderCallbacks<Cursor>{
    public static final String MOVIES_KEY = "moviesKey";
    public static final String EXTRA_MOVIE = "movie";
    public static final String RELEASE_DATE_DESC = "release_date.desc";
    public static final String VOTE_AVERAGE_DESC = "vote_average.desc";
    public static final String POPULARITY_DESC = "popularity.desc";
    private static final int CURSOR_LOADER_ID = 0;

    private String TAG = FetchMovieGalleryTask.class.getSimpleName();
    private ArrayAdapter<MovieData> mMoviesAdapter;
    private ArrayList<MovieData> mListOfMovies;
    private ArrayList<MovieData> mListOfFavMovies;

    @Bind(R.id.gridView_movies)
    GridView mGridView;
    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        public void onItemSelected(MovieData movieData);
    }

    public MovieGalleryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        if (savedInstance != null) {
            mListOfMovies = (ArrayList<MovieData>) savedInstance.get(MOVIES_KEY);
        }
        getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);

        if (mListOfMovies == null) {
            mListOfMovies = new ArrayList<>();
        }
        //Take data from source and populate grid view
        mMoviesAdapter = new MovieArrayAdapter(
                getActivity(),
                R.layout.grid_item_movie,
                R.id.grid_item_imageView,
                mListOfMovies);

        //get reference of grid view and attach adapter
        mGridView.setAdapter(mMoviesAdapter);
        //set onclick listener to get to details screen
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieData movie = mMoviesAdapter.getItem(position);
                ((Callback)getActivity()).onItemSelected(movie);
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mMoviesAdapter.isEmpty()) {
            updateMovieGallery();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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
            case R.id.action_get_favorites:
                mMoviesAdapter.clear();
                mMoviesAdapter.addAll(mListOfFavMovies);
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
        Log.d(TAG, "order by: " + orderBy);
        editor.commit();
    }

    public void updateMovieGallery() {
        Log.d(TAG, "update gallery");
        FetchMovieGalleryTask fetchMovieGalleryTask = new FetchMovieGalleryTask(getActivity(), mMoviesAdapter);
        fetchMovieGalleryTask.execute();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(MOVIES_KEY, mListOfMovies);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args){
        return new CursorLoader(getActivity(), MoviesProvider.Movies.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor){
        mListOfFavMovies = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
//            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                int idIndex = cursor.getInt(cursor.getColumnIndex(MovieColumns._ID));
                String movieId = cursor.getString(cursor.getColumnIndex(MovieColumns.ID));
                String title = cursor.getString(cursor.getColumnIndex(MovieColumns.TITLE));
                String description = cursor.getString(cursor.getColumnIndex(MovieColumns.DESCRIPTION));
                String date = cursor.getString(cursor.getColumnIndex(MovieColumns.RELEASE_DATE));
                String rating = cursor.getString(cursor.getColumnIndex(MovieColumns.RATING));
                String lang = cursor.getString(cursor.getColumnIndex(MovieColumns.LANGUAGE));
                String path = cursor.getString(cursor.getColumnIndex(MovieColumns.PATH));
                MovieData data = new MovieData(movieId, title, description, date, rating, lang, path);
                mListOfFavMovies.add(data);
            }
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader){
    }
}
