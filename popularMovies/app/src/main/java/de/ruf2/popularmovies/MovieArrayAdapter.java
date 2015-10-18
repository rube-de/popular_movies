package de.ruf2.popularmovies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.ruf2.popularmovies.utils.Utils;

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
            view.setAdjustViewBounds(true);
            view.setPadding(0,0,0,0);
        }

        String movieStr = getItem(position);
        String url = "http://image.tmdb.org/t/p/w185/" + Utils.getPosterPath(movieStr);
        //Log.d("adapter", "url: " + url);
        Picasso.with(mContext).load(url).placeholder(R.mipmap.img_placeholder).error(R.mipmap.error).into(view);

        return view;
    }
}
