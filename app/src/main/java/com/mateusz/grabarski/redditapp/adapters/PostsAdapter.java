package com.mateusz.grabarski.redditapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mateusz.grabarski.redditapp.R;
import com.mateusz.grabarski.redditapp.adapters.listeners.OnPostClickListener;
import com.mateusz.grabarski.redditapp.model.Post;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mateusz Grabarski on 09.07.2018.
 */
public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private List<Post> mPosts;
    private OnPostClickListener mListener;

    public PostsAdapter(List<Post> posts, OnPostClickListener listener) {
        this.mPosts = posts;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.populate(mPosts.get(position));
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_post_title_tv)
        TextView titleTv;

        @BindView(R.id.item_post_date_tv)
        TextView dateTv;

        @BindView(R.id.item_post_thumbnail_iv)
        ImageView thumbnailIv;

        @BindView(R.id.item_post_author_tv)
        TextView authorTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void populate(Post post) {
            titleTv.setText(post.getTitle());
            dateTv.setText(post.getDateUpdate());
            authorTv.setText(post.getAuthor());

            Picasso.get()
                    .load(post.getThumbnailURL())
                    .fit()
                    .centerCrop()
                    .into(thumbnailIv);
        }
    }
}
