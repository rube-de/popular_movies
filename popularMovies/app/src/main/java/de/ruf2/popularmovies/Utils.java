package de.ruf2.popularmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Bernhard Ruf on 23.08.2015.
 */
public class Utils {
    private static final String TMDB_RESULT = "results";
    private static final String TMDB_LANGUAGE = "original_language";
    private static final String TMDB_TITLE = "original_title";
    private static final String TMDB_DESCRIPTION = "overview";
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

    public static String getPosterPath(String movie) throws JSONException{
            JSONObject movieObject = new JSONObject(movie);
            String path = movieObject.getString(TMDB_POSTER_PATH);
            return path;
    }
}
