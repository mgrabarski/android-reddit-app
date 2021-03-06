package com.mateusz.grabarski.redditapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.mateusz.grabarski.redditapp.adapters.PostsAdapter;
import com.mateusz.grabarski.redditapp.adapters.listeners.OnPostClickListener;
import com.mateusz.grabarski.redditapp.model.Feed;
import com.mateusz.grabarski.redditapp.model.Post;
import com.mateusz.grabarski.redditapp.model.childs.Entry;
import com.mateusz.grabarski.redditapp.utils.ExtractXML;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class MainActivity extends AppCompatActivity implements OnPostClickListener {

    private static final String TAG = "MainActivity";

    @BindView(R.id.activity_main_rv)
    RecyclerView mainRv;

    @BindView(R.id.activity_main_search_et)
    EditText searchEt;

    @BindView(R.id.activity_main_search_iv)
    ImageView searchIv;

    private List<Post> mPosts;
    private PostsAdapter mAdapter;
    private String mCurrentFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mainRv.setLayoutManager(new LinearLayoutManager(this));

        mPosts = new ArrayList<>();
        mAdapter = new PostsAdapter(mPosts, this);

        mainRv.setAdapter(mAdapter);

        loadFeeds();
    }

    @Override
    public void onPostClick(Post post) {
        CommentsActivity.start(this, post);
    }

    @OnClick(R.id.activity_main_search_iv)
    public void refreshFeeds() {
        String feedName = searchEt.getText().toString();
        if (feedName.equals("")) {
            loadFeeds();
        } else {
            mCurrentFeed = feedName;
            loadFeeds();
        }
    }

    private void loadFeeds() {

        mPosts.clear();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FeedAPI.BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        FeedAPI feedAPI = retrofit.create(FeedAPI.class);

        Call<Feed> call = feedAPI.getFeeds(mCurrentFeed);

        call.enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {

                if (response.body() == null) {
                    return;
                }

                List<Entry> entries = response.body().getEntrys();

                for (int i = 0; i < entries.size(); i++) {
                    ExtractXML aHrefExtractor = new ExtractXML(entries.get(i).getContent(), "<a href=");
                    List<String> postContent = aHrefExtractor.start();

                    ExtractXML srcImgExtractor = new ExtractXML(entries.get(i).getContent(), "<img src=");
                    try {
                        postContent.add(srcImgExtractor.start().get(0));
                    } catch (NullPointerException | IndexOutOfBoundsException e) {
                        postContent.add(null);
                    }

                    mPosts.add(new Post(
                            entries.get(i).getTitle(),
                            entries.get(i).getAuthor().getName(),
                            entries.get(i).getUpdated(),
                            postContent.get(0),
                            postContent.get(postContent.size() - 1)
                    ));
                }

                Log.d(TAG, "onResponse: " + mPosts.toString());

                runOnUiThread(() -> mAdapter.notifyDataSetChanged());
            }

            @Override
            public void onFailure(@NonNull Call<Feed> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
