package com.mateusz.grabarski.redditapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mateusz.grabarski.redditapp.model.Post;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentsActivity extends AppCompatActivity {

    private static final String KEY_SELECTED_POST = "post";

    @BindView(R.id.item_post_title_tv)
    TextView titleTv;

    @BindView(R.id.item_post_date_tv)
    TextView dateTv;

    @BindView(R.id.item_post_thumbnail_iv)
    ImageView thumbnailIv;

    @BindView(R.id.item_post_author_tv)
    TextView authorTv;

    @BindView(R.id.activity_comment_rv)
    RecyclerView commentRv;

    private Post mSelectedPost;

    public static void start(Context context, Post post) {
        Intent starter = new Intent(context, CommentsActivity.class);
        starter.putExtra(KEY_SELECTED_POST, post);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();

        if (extras != null && extras.containsKey(KEY_SELECTED_POST)) {
            mSelectedPost = (Post) extras.getSerializable(KEY_SELECTED_POST);
            loadUI();
        } else {
            finish();
        }
    }

    private void loadUI() {
        titleTv.setText(mSelectedPost.getTitle());
        dateTv.setText(mSelectedPost.getDateUpdate());
        authorTv.setText(mSelectedPost.getAuthor());

        Picasso.get()
                .load(mSelectedPost.getThumbnailURL())
                .fit()
                .centerCrop()
                .into(thumbnailIv);
    }
}
