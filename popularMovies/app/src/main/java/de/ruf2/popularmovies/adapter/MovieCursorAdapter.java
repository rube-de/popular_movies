package de.ruf2.popularmovies.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.ruf2.popularmovies.R;
import de.ruf2.popularmovies.data.MovieColumns;

/**
 * Created by Bernhard Ruf on 31.10.2015.
 */
public class MovieCursorAdapter extends CursorAdapter {

    public MovieCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_item_movie, parent,false);
        view.setTag(new ViewHolder(view));
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder vh = (ViewHolder) view.getTag();
        String path = cursor.getString(cursor.getColumnIndex(MovieColumns.PATH));
        String url = "http://image.tmdb.org/t/p/w185/" + path;
//        Log.d("adapter", "url: " + url);
        Picasso.with(context).load(url)
                .placeholder(R.mipmap.img_placeholder)
                .error(R.mipmap.error)
                .into(vh.image);

    }

    static class ViewHolder{
        @Bind(R.id.grid_item_imageView)
        ImageView image;
        ViewHolder(View v){
            ButterKnife.bind(this,v);
        }
    }
}
