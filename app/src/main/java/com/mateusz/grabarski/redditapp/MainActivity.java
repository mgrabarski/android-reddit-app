package com.mateusz.grabarski.redditapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mateusz.grabarski.redditapp.model.Feed;
import com.mateusz.grabarski.redditapp.model.childs.Entry;
import com.mateusz.grabarski.redditapp.utils.ExtractXML;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FeedAPI.BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        FeedAPI feedAPI = retrofit.create(FeedAPI.class);

        Call<Feed> call = feedAPI.getFeeds();

        call.enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {

                List<Entry> entries = response.body().getEntrys();

                for (Entry entry : entries) {
                    ExtractXML aHrefExtractor = new ExtractXML(entry.getContent(), "<a href=");
                    List<String> postContent = aHrefExtractor.start();

                    ExtractXML srcImgExtractor = new ExtractXML(entry.getContent(), "<img src=");
                    try {
                        postContent.add(srcImgExtractor.start().get(0));
                    } catch (NullPointerException | IndexOutOfBoundsException e) {
                        postContent.add(null);
                    }

                    Log.d(TAG, "onResponse: " + postContent.toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Feed> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
