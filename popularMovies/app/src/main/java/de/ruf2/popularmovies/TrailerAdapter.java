package de.ruf2.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;

/**
 * Created by Bernhard Ruf on 17.10.2015.
 */
public class TrailerAdapter extends CursorAdapter {

    public TrailerAdapter(Context context, Cursor c, int flags){
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view=  LayoutInflater.from(context).inflate(R.layout.list_item_trailer, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        holder.trailerIconView.setImageResource(R.mipmap.ic_play);
        holder.trailerNameView.setText("Trailer 1");

    }

    public static class ViewHolder {
        @Bind(R.id.list_item_trailer_icon) ImageView trailerIconView;
        @Bind(R.id.list_item_trailer_name) TextView trailerNameView;

        public ViewHolder(View view) {
        }
    }
}
