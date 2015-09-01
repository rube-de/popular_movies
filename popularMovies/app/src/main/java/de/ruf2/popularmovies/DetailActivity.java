package de.ruf2.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
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


    public static class DetailFragment extends Fragment {
        protected final String TAG = getClass().getSimpleName();
        @Bind(R.id.movie_detail_title) TextView mTitle;
        @Bind(R.id.movie_detail_release) TextView mRelease;
        @Bind(R.id.movie_detail_rating) TextView mRating;
        @Bind(R.id.movie_detail_overview) TextView mOverview;
        @Bind(R.id.movie_detail_pic) ImageView mImage;

        private ShareActionProvider mShareActionProvider;
        private static String SHARE_HASHTAG = " #nanodegree";

        private String mMovieStr;

        public DetailFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final Intent intent = getActivity().getIntent();
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            ButterKnife.bind(this, rootView);

            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                mMovieStr = intent.getStringExtra(Intent.EXTRA_TEXT);

                //set image
                String url = "http://image.tmdb.org/t/p/w500/" + Utils.getPosterPath(mMovieStr);
                Picasso.with(getActivity()).load(url).placeholder(R.mipmap.img_placeholder).error(R.mipmap.error).into(mImage);
                //set title, overview
                mTitle.setText(Utils.getTitle(mMovieStr));
                mRelease.setText(Utils.getRelease(mMovieStr));
                mRating.setText(Utils.getRating(mMovieStr));
                mOverview.setText(Utils.getOverview(mMovieStr));
            }
            return rootView;
        }
        @Override public void onDestroyView() {
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
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Movie: " + Utils.getTitle(mMovieStr) + "(" + Utils.getRelease(mMovieStr) + ")" + "|| Rating: " + Utils.getRating(mMovieStr) + " " + SHARE_HASHTAG);
            return shareIntent;
        }
    }
}
