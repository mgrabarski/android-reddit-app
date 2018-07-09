package com.mateusz.grabarski.redditapp;

import com.mateusz.grabarski.redditapp.model.Feed;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Mateusz Grabarski on 03.07.2018.
 */
public interface FeedAPI {

    String BASE_URL = "https://www.reddit.com/";

    @GET("r/{feed_name}/.rss")
    Call<Feed> getFeeds(@Path("feed_name") String feed_name);

    @GET(".rss")
    Call<Feed> getFeeds();
}
