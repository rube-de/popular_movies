package de.ruf2.popularmovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.ruf2.popularmovies.R;
import de.ruf2.popularmovies.data.ReviewData;

/**
 * Created by Bernhard Ruf on 25.10.2015.
 */
public class ReviewAdapter extends ArrayAdapter<ReviewData> {

    private final Context mContext;

    public ReviewAdapter(Context context, int resource, int textViewResourceId, List<ReviewData> objects) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item_review, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        ButterKnife.bind(view);
        ReviewData review = getItem(position);
        holder.authorTextView.setText(review.getAuthor());
        holder.contentTextView.setText(review.getContent());
        return view;
    }

    static class ViewHolder {
        @Bind(R.id.list_item_review_author)
        TextView authorTextView;
        @Bind(R.id.list_item_review_content)
        TextView contentTextView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
