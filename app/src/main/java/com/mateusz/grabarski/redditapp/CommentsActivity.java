package com.mateusz.grabarski.redditapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentsActivity extends AppCompatActivity {

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

    public static void start(Context context) {
        Intent starter = new Intent(context, CommentsActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        ButterKnife.bind(this);
    }
}
