package de.ruf2.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Bernhard Ruf on 23.08.2015.
 */
public class FetchMovieGalleryTask extends AsyncTask<String,Void,String[]> {
    private String LOG_TAG = FetchMovieGalleryTask.class.getSimpleName();
    private static final String MOVIEDB_API = "*api-key*";

    private ArrayAdapter<String> mMoviesAdapter;
    private final Context mContext;

    private URL mUrl;


    public FetchMovieGalleryTask(Context context, ArrayAdapter<String> moviesAdapter) {
        mContext = context;
        mMoviesAdapter = moviesAdapter;
    }

    @Override
    protected String[] doInBackground(String... params) {
//        if (params.length == 0) {
//            return null;
//        }
        Log.d(LOG_TAG, "execute doingbackground()");
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String moviesJsonStr = null;
        String[] movies = null;

        //TODO: use context to get sort by
        String sortBy = "popularity.desc";

        try{
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("discover")
                .appendPath("movie")
                .appendQueryParameter("sort_by", sortBy)
                .appendQueryParameter("api_key", MOVIEDB_API);

        URL url = new URL(builder.build().toString());
            mUrl = url;
            Log.d(LOG_TAG, "Url: " + url.toString());

            // Create the request to themoviedb, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            moviesJsonStr = buffer.toString();
            Log.d(LOG_TAG, moviesJsonStr);

        }catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        }finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        try {
            return getMovieDataFromJson(moviesJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error", e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String[] strings){
        super.onPostExecute(strings);
        if(strings != null){
            mMoviesAdapter.clear();
            mMoviesAdapter.addAll(strings);
        }

    }

    private String[] getMovieDataFromJson(String movieJsonStr)
            throws JSONException {

        final String TMDB_RESULT = "results";

        JSONObject movieJson = new JSONObject(movieJsonStr);
        JSONArray moviesArray = movieJson.getJSONArray(TMDB_RESULT);

        String[] resultStr = Utils.createMovieStringArray(moviesArray);

        return resultStr;
    }
}