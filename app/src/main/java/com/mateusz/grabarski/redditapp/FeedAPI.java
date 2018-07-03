package com.mateusz.grabarski.redditapp;

import com.mateusz.grabarski.redditapp.model.Feed;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Mateusz Grabarski on 03.07.2018.
 */
public interface FeedAPI {

    String BASE_URL = "https://www.reddit.com/";

    @GET(".rss")
    Call<Feed> getFeeds();
}
