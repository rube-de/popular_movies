package de.ruf2.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Bernhard Ruf on 23.08.2015.
 */
public class MovieArrayAdapter extends ArrayAdapter<String> {
    Context mContext;

    public MovieArrayAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
        super(context, resource, textViewResourceId, objects);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView view = (ImageView) convertView;
        if (view == null) {
            view = new ImageView(mContext);
        }
        String movieStr = getItem(position);
        String url = "http://image.tmdb.org/t/p/w185/" + Utils.getPosterPath(movieStr);
        Log.d("adapter", "url: " + url);
        Picasso.with(mContext).load(url).into(view);

        return view;
    }
}
