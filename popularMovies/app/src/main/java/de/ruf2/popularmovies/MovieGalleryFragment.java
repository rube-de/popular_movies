package de.ruf2.popularmovies;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
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

    private ArrayAdapter<String> mMoviesAdapter;

    public MovieGalleryFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        setHasOptionsMenu(true);

        //Take data from source and populate grid view
        mMoviesAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.grid_item_movie,
                R.id.grid_item_imageButton,
                new ArrayList<String>());

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
    public void onStart(){
        super.onStart();
        updateMovieGallery();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        //add menu options to action bar
        inflater.inflate(R.menu.moviegalleryfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //handle action bar item clicks
        switch (item.getItemId()){
            case R.id.action_refresh:
                updateMovieGallery();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateMovieGallery(){
        FetchMovieGalleryTask fetchMovieGalleryTask = new FetchMovieGalleryTask(getActivity(), mMoviesAdapter);
        fetchMovieGalleryTask.execute();
    }

}
