package de.ruf2.popularmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Bernhard Ruf on 23.08.2015.
 */
public class Utils {
    protected static final String TAG = "Utils";
    private static final String TMDB_RESULT = "results";
    private static final String TMDB_LANGUAGE = "original_language";
    private static final String TMDB_TITLE = "original_title";
    private static final String TMDB_OVERVIEW = "overview";
    private static final String TMDB_DATE = "release_date";
    private static final String TMDB_POSTER_PATH = "poster_path";
    private static final String TMDB_RATING = "vote_average";

    public static String[] createMovieStringArray(JSONArray moviesArray) throws JSONException{
        String[] resultStr = new String[20];
        for (int i=0; i<moviesArray.length(); i++){
            JSONObject movieObject = moviesArray.getJSONObject(i);

            resultStr[i] = movieObject.toString();
        }
        return resultStr;
    }

    public static String getPosterPath(String movie) {
        try {
            JSONObject movieObject = new JSONObject(movie);
            String path = movieObject.getString(TMDB_POSTER_PATH);
            return path;
        }catch (Exception e) {
            Log.e(TAG, "json reading failed: " + e);
        }
        return null;
    }

    public static String getTitle(String movie){
        try{
            JSONObject movieObject = new JSONObject(movie);
            String title = movieObject.getString(TMDB_TITLE);
            return title;
       }catch (JSONException e) {
            Log.e(TAG, "json reading failed: " + e);
        }
        return null;
    }
    public static String getOverview(String movie) {
        try {
            JSONObject movieObject = new JSONObject(movie);
            String overview = movieObject.getString(TMDB_OVERVIEW);
            return overview;
        }catch (JSONException e) {
            Log.e(TAG, "json reading failed: " + e);
        }
        return null;
    }

    public static String getRating(String movie) {
        try {
            JSONObject movieObject = new JSONObject(movie);
            String rating = movieObject.getString(TMDB_RATING);
            return rating;
        }catch (JSONException e) {
            Log.e(TAG, "json reading failed: " + e);
        }
        return null;
    }

    public static String getRelease(String movie) {
        try {
            JSONObject movieObject = new JSONObject(movie);
            String release = movieObject.getString(TMDB_DATE);
            return release;
        }catch (JSONException e) {
            Log.e(TAG, "json reading failed: " + e);
        }
        return null;
    }
    public static String getLanguage(String movie) {
        try {
            JSONObject movieObject = new JSONObject(movie);
            String language = movieObject.getString(TMDB_LANGUAGE);
            return language;
        }catch (JSONException e) {
            Log.e(TAG, "json reading failed: " + e);
        }
        return null;
    }
}
