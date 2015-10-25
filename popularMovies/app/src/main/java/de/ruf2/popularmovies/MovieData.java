package de.ruf2.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Bernhard Ruf on 23.08.2015.
 */
public class MovieData implements Parcelable {
    private static final String TMDB_ID = "id";
    private static final String TMDB_LANGUAGE = "original_language";
    private static final String TMDB_TITLE = "original_title";
    private static final String TMDB_OVERVIEW = "overview";
    private static final String TMDB_DATE = "release_date";
    private static final String TMDB_POSTER_PATH = "poster_path";
    private static final String TMDB_RATING = "vote_average";

    private String id;
    private String title;
    private String description;
    private String releaseDate;
    private String votingAverage;
    private String language;
    private String path;

    private int mData;

    public MovieData(String id, String title, String description, String releaseDate, String votingAverage, String language, String path) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.votingAverage = votingAverage;
        this.language = language;
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getVotingAverage() {
        return votingAverage;
    }

    public void setVotingAverage(String votingAverage) {
        this.votingAverage = votingAverage;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getId() {
        return id;
    }

    protected MovieData(Parcel in) {
        mData = in.readInt();
        id = in.readString();
        title = in.readString();
        description = in.readString();
        releaseDate = in.readString();
        votingAverage = in.readString();
        language = in.readString();
        path = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mData);
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(releaseDate);
        dest.writeString(votingAverage);
        dest.writeString(language);
        dest.writeString(path);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MovieData> CREATOR = new Parcelable.Creator<MovieData>() {
        @Override
        public MovieData createFromParcel(Parcel in) {
            return new MovieData(in);
        }

        @Override
        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };

    public static MovieData fromJson(JSONObject jsonObject) throws JSONException {
        return new MovieData(
                jsonObject.getString(TMDB_ID),
                jsonObject.getString(TMDB_TITLE),
                jsonObject.getString(TMDB_OVERVIEW),
                jsonObject.getString(TMDB_DATE),
                jsonObject.getString(TMDB_RATING),
                jsonObject.getString(TMDB_LANGUAGE),
                jsonObject.getString(TMDB_POSTER_PATH)
                );
    }
}