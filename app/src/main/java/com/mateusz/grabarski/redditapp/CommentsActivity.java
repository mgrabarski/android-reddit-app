package com.mateusz.grabarski.redditapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.mateusz.grabarski.redditapp.model.Feed;
import com.mateusz.grabarski.redditapp.model.Post;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class CommentsActivity extends AppCompatActivity {

    private static final String TAG = "CommentsActivity";

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
    private String mCurrentFeed;

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

            String[] split = mSelectedPost.getPostURL().split(FeedAPI.BASE_URL);
            mCurrentFeed = split[1];
            loadUI();
            loadComments();
        } else {
            finish();
        }
    }

    private void loadComments() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FeedAPI.BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        FeedAPI feedAPI = retrofit.create(FeedAPI.class);

        Call<Feed> call = feedAPI.getFeeds(mCurrentFeed);

        call.enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                Log.d(TAG, "onResponse: Server Response: " + response.toString());
            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                Log.e(TAG, "onFailure: Unable to retrieve RSS: " + t.getMessage());
            }
        });
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
