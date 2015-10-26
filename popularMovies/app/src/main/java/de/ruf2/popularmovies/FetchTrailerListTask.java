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
import java.util.ArrayList;
import java.util.List;

import de.ruf2.popularmovies.data.MovieData;
import de.ruf2.popularmovies.data.TrailerData;
import de.ruf2.popularmovies.utils.Utils;

/**
 * Created by Bernhard Ruf on 24.10.2015.
 */
public class FetchTrailerListTask extends AsyncTask<MovieData,Void,List<TrailerData>>{
    private final String LOG_TAG = FetchTrailerListTask.class.getSimpleName();
    private ArrayAdapter<TrailerData> mTrailerAdapter;
    private final Context mContext;

    public FetchTrailerListTask(Context mContext, ArrayAdapter<TrailerData> mTrailerAdapter) {
        this.mTrailerAdapter = mTrailerAdapter;
        this.mContext = mContext;
    }

    @Override
    protected List<TrailerData> doInBackground(MovieData... params) {
        Log.d(LOG_TAG, "execute doingbackground()");
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String trailerJsonStr = null;
        MovieData movieDate = params[0];
        try {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("api.themoviedb.org")
                    .appendPath("3")
                    .appendPath("movie")
                    .appendPath(movieDate.getId())
                    .appendPath("videos")
                    .appendQueryParameter("api_key", Utils.getMoviedbApiKey());

            URL url = new URL(builder.build().toString());
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
            trailerJsonStr = buffer.toString();
            Log.d(LOG_TAG, trailerJsonStr);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        } finally {
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
            return getTrailerDataFromJson(trailerJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error", e);
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(List<TrailerData> strings) {
        super.onPostExecute(strings);
        if (strings != null) {
            mTrailerAdapter.clear();
            mTrailerAdapter.addAll(strings);
        }
    }

    private List<TrailerData> getTrailerDataFromJson(String trailerJsonStr)
            throws JSONException {

        final String TMDB_RESULT = "results";

        JSONObject trailerJson = new JSONObject(trailerJsonStr);
        JSONArray trailerArray = trailerJson.getJSONArray(TMDB_RESULT);

        List<TrailerData> trailerList = new ArrayList<>();
        for (int i=0; i<trailerArray.length(); i++){
            JSONObject trailerObject = trailerArray.getJSONObject(i);
            trailerList.add(TrailerData.fromJson(trailerObject));
        }
        return trailerList;
    }
}
