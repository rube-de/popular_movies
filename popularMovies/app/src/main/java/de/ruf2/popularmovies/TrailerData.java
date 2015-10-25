package de.ruf2.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Bernhard Ruf on 24.10.2015.
 */
public class TrailerData implements Parcelable{
    private static final String TRAILER_ID = "id";
    private static final String TRAILER_LANGUAGE = "iso_639_1";
    private static final String TRAILER_NAME = "name";
    private static final String TRAILER_KEY = "key";
    private static final String TRAILER_SITE = "site";
    private static final String TRAILER_SIZE = "size";
    private static final String TRAILER_TYPE = "type";

    private String id;
    private String name;
    private String key;
    private String language;
    private String site;
    private String type;
    private Integer size;

    private int mData;

    public TrailerData(String id, String name, String key, String language, String site, String type, Integer size){
        this.id = id;
        this.name = name;
        this.key = key;
        this.language = language;
        this.site = site;
        this.type = type;
        this.size = size;
    }

    protected TrailerData(Parcel in){
        mData = in.readInt();
        id = in.readString();
        name = in.readString();
        key = in.readString();
        language = in.readString();
        site = in.readString();
        type = in.readString();
        size = in.readInt();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mData);
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(key);
        dest.writeString(language);
        dest.writeString(site);
        dest.writeString(type);
        dest.writeInt(size);
    }

    public static TrailerData fromJson(JSONObject jsonObject) throws JSONException{
        return new TrailerData(
                jsonObject.getString(TRAILER_ID),
                jsonObject.getString(TRAILER_NAME),
                jsonObject.getString(TRAILER_KEY),
                jsonObject.getString(TRAILER_LANGUAGE),
                jsonObject.getString(TRAILER_SITE),
                jsonObject.getString(TRAILER_TYPE),
                jsonObject.getInt(TRAILER_SIZE)
        );
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getLanguage() {
        return language;
    }

    public String getSite() {
        return site;
    }

    public String getType() {
        return type;
    }

    public Integer getSize() {
        return size;
    }
}
