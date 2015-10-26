package de.ruf2.popularmovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.ruf2.popularmovies.R;
import de.ruf2.popularmovies.data.TrailerData;

/**
 * Created by Bernhard Ruf on 17.10.2015.
 */
public class TrailerAdapter extends ArrayAdapter<TrailerData> {
    Context mContext;


    public TrailerAdapter(Context context, int resource, int textViewResourceId, List<TrailerData> objects) {
        super(context, resource, textViewResourceId, objects);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item_trailer, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        ButterKnife.bind(view);
        TrailerData trailer = getItem(position);
        holder.iconView.setImageResource(R.mipmap.ic_play);
        holder.trailerText.setText(trailer.getName());
        return view;
    }

    static class ViewHolder {
        @Bind(R.id.list_item_trailer_icon)
        ImageView iconView;
        @Bind(R.id.list_item_trailer_name)
        TextView trailerText;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
