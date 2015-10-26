package de.ruf2.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Bernhard Ruf on 25.10.2015.
 */
public class ReviewData implements Parcelable{
    private static final String REVIEW_ID = "id";
    private static final String REVIEW_AUTHOR = "author";
    private static final String REVIEW_CONTENT= "content";
    private static final String REVIEW_URL = "url";

    private String id;
    private String author;
    private String content;
    private String url;

    public ReviewData(String id, String author, String content, String url){
        this.id = id;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    protected ReviewData(Parcel in){
        id = in.readString();
        author = in.readString();
        content = in.readString();
        url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(author);
        dest.writeString(content);
        dest.writeString(url);
    }

    public static ReviewData fromJson(JSONObject jsonObject) throws JSONException{
        return new ReviewData(
                jsonObject.getString(REVIEW_ID),
                jsonObject.getString(REVIEW_AUTHOR),
                jsonObject.getString(REVIEW_CONTENT),
                jsonObject.getString(REVIEW_URL)
        );
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }
}
