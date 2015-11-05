package de.ruf2.popularmovies;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.ruf2.popularmovies.adapter.ReviewAdapter;
import de.ruf2.popularmovies.adapter.TrailerAdapter;
import de.ruf2.popularmovies.data.MovieColumns;
import de.ruf2.popularmovies.data.MovieData;
import de.ruf2.popularmovies.data.MoviesProvider;
import de.ruf2.popularmovies.data.ReviewData;
import de.ruf2.popularmovies.data.TrailerData;

/**
 * Created by Bernhard Ruf on 18.10.2015.
 */
public class DetailFragment extends Fragment {
    protected final String TAG = getClass().getSimpleName();
    public static final String TRAILER_KEY = "trailer";
    public static final String REVIEW_KEY = "review";
    static final String DETAIL_DATA = "DATA";


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
    @Bind(R.id.listview_reviews)
    ListView mReviewListView;
    @Bind(R.id.button_favorites)
    Button mButtonAdd;

    private ShareActionProvider mShareActionProvider;
    private static String SHARE_HASHTAG = " #nanodegree";

    private MovieData mMovie;
    private ArrayAdapter<TrailerData> mTrailerAdapter;
    private ArrayList<TrailerData> mListOfTrailers;
    private ArrayAdapter<ReviewData> mReviewAdapter;
    private ArrayList<ReviewData> mListOfReviews;


    public DetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        if (savedInstance != null) {
            mListOfTrailers = (ArrayList<TrailerData>) savedInstance.get(TRAILER_KEY);
            mListOfReviews = (ArrayList<ReviewData>) savedInstance.get(REVIEW_KEY);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, rootView);

        if(arguments != null){
            mMovie = arguments.getParcelable(MovieGalleryFragment.EXTRA_MOVIE);

            //set image
            String url = "http://image.tmdb.org/t/p/w342/" + mMovie.getPath();
            Picasso.with(getActivity()).load(Uri.parse(url)).placeholder(R.mipmap.img_placeholder).error(R.mipmap.error).into(mImage);
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
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailer.getKey()));
                        startActivity(intent);
                    } catch (ActivityNotFoundException ex) {
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey()));
                        startActivity(intent);
                    }
                }
            });

            if (mListOfReviews == null){
                mListOfReviews = new ArrayList<>();
            }
            mReviewAdapter = new ReviewAdapter(
                    getActivity(),
                    R.layout.list_item_review,
                    R.id.listview_reviews,
                    mListOfReviews);

            if (mReviewAdapter.isEmpty()) {
                updateReviewList(mMovie);
            }
            mReviewListView.setAdapter(mReviewAdapter);
            mReviewListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ReviewData review = mReviewAdapter.getItem(position);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(review.getUrl()));
                    startActivity(intent);
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
        if (mMovie != null)
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
    private void updateReviewList(MovieData movieData) {
        Log.d(TAG, "update trailers");
        FetchReviewListTask fetchReviewListTask = new FetchReviewListTask(getActivity(),mReviewAdapter);
        fetchReviewListTask.execute(movieData);

    }
    @OnClick(R.id.button_favorites)
    public void onClickFavorites(View view) {
        String selection = MovieColumns.ID + " = ?";
        String[] selectionArgs = {""};
        selectionArgs[0] = mMovie.getId();
        Cursor cursor = getActivity().getContentResolver().query(
                MoviesProvider.Movies.CONTENT_URI,
                null,
                selection,
                selectionArgs,
                null);
        if(cursor.getCount()<1) {
            mButtonAdd.setText(R.string.remove_from_favs);

            ContentValues cv = new ContentValues();
            cv.put(MovieColumns.ID, mMovie.getId());
            cv.put(MovieColumns.TITLE, mMovie.getTitle());
            cv.put(MovieColumns.DESCRIPTION, mMovie.getDescription());
            cv.put(MovieColumns.RELEASE_DATE, mMovie.getReleaseDate());
            cv.put(MovieColumns.RATING, mMovie.getVotingAverage());
            cv.put(MovieColumns.LANGUAGE, mMovie.getLanguage());
            cv.put(MovieColumns.PATH, mMovie.getPath());
            Uri url = getActivity().getContentResolver().insert(MoviesProvider.Movies.CONTENT_URI, cv);
            Toast t = Toast.makeText(getActivity(), "Movie added to favorites", Toast.LENGTH_SHORT);
            t.show();
            Log.d(TAG, mMovie.getTitle() + " added to favs (" + url + ")");
        } else {
            mButtonAdd.setText(R.string.add_to_favs);
            cursor.moveToFirst();
            long _id = cursor.getLong(cursor.getColumnIndex(MovieColumns._ID));
            getActivity().getContentResolver().delete(MoviesProvider.Movies.withId(_id), null,null);
            Toast t = Toast.makeText(getActivity(), "Movie removed from favorites", Toast.LENGTH_SHORT);
            t.show();
            Log.d(TAG, mMovie.getTitle() + " removed from favs");
        }
    }
}
