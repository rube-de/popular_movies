package de.ruf2.popularmovies;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bernhard Ruf on 18.10.2015.
 */
public class DetailFragment extends Fragment {
    protected final String TAG = getClass().getSimpleName();
    public static final String TRAILER_KEY = "trailer";

    @Bind(R.id.movie_detail_title)
    TextView mTitle;
    @Bind(R.id.movie_detail_release)
    TextView mRelease;
    @Bind(R.id.movie_detail_rating)
    TextView mRating;
    @Bind(R.id.movie_detail_overview)
    TextView mOverview;
    @Bind(R.id.movie_detail_pic)
    ImageView mImage;
    @Bind(R.id.listview_trailers)
    ListView mTrailersListView;

    private ShareActionProvider mShareActionProvider;
    private static String SHARE_HASHTAG = " #nanodegree";

    private MovieData mMovie;
    private ArrayAdapter<TrailerData> mTrailerAdapter;
    private ArrayList<TrailerData> mListOfTrailers;

    public DetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        if (savedInstance != null) {
            mListOfTrailers = (ArrayList<TrailerData>) savedInstance.get(TRAILER_KEY);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Intent intent = getActivity().getIntent();
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, rootView);

        if (intent != null && intent.hasExtra(MovieGalleryFragment.EXTRA_MOVIE)) {
            mMovie = intent.getParcelableExtra(MovieGalleryFragment.EXTRA_MOVIE);

            //set image
            String url = "http://image.tmdb.org/t/p/w500/" + mMovie.getPath();
            Picasso.with(getActivity()).load(url).placeholder(R.mipmap.img_placeholder).error(R.mipmap.error).into(mImage);
            //set title, overview
            mTitle.setText(mMovie.getTitle());
            mRelease.setText(mMovie.getReleaseDate());
            mRating.setText(mMovie.getVotingAverage());
            mOverview.setText(mMovie.getDescription());

            if (mListOfTrailers == null){
                mListOfTrailers = new ArrayList<>();
            }
            mTrailerAdapter = new TrailerAdapter(
                    getActivity(),
                    R.layout.list_item_trailer,
                    R.id.listview_trailers,
                    mListOfTrailers);

            if (mTrailerAdapter.isEmpty()) {
                updateTrailerList(mMovie);
            }
            mTrailersListView.setAdapter(mTrailerAdapter);
            mTrailersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TrailerData trailer = mTrailerAdapter.getItem(position);
                    try{
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailer.getKey()));
                        startActivity(intent);
                    }catch (ActivityNotFoundException ex){
                        Intent intent=new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://www.youtube.com/watch?v="+trailer.getKey()));
                        startActivity(intent);
                    }
                }
            });
        }
            return rootView;
        }
    @Override
    public void onStart() {
        super.onStart();

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_detail, menu);

        MenuItem shareItem = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider)
                MenuItemCompat.getActionProvider(shareItem);

        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(createShareMovieIntent());
        }
    }

    private Intent createShareMovieIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Movie: " + mMovie.getTitle() + "(" + mMovie.getReleaseDate() + ")" + "|| Rating: " + mMovie.getVotingAverage() + " " + SHARE_HASHTAG);
        return shareIntent;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(TRAILER_KEY, mListOfTrailers);
    }

    private void updateTrailerList(MovieData movieData) {
        Log.d(TAG, "update trailers");
        FetchTrailerListTask fetchTrailerListTask = new FetchTrailerListTask(getActivity(),mTrailerAdapter);
        fetchTrailerListTask.execute(movieData);

    }
}
