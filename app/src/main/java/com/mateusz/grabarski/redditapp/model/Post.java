package com.mateusz.grabarski.redditapp.model;

import java.io.Serializable;

/**
 * Created by Mateusz Grabarski on 04.07.2018.
 */
public class Post implements Serializable {

    private String title;
    private String author;
    private String dateUpdate;
    private String postURL;
    private String thumbnailURL;

    public Post(String title, String author, String dateUpdate, String postURL, String thumbnailURL) {
        this.title = title;
        this.author = author;
        this.dateUpdate = dateUpdate;
        this.postURL = postURL;
        this.thumbnailURL = thumbnailURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(String dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public String getPostURL() {
        return postURL;
    }

    public void setPostURL(String postURL) {
        this.postURL = postURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", dateUpdate='" + dateUpdate + '\'' +
                ", postURL='" + postURL + '\'' +
                ", thumbnailURL='" + thumbnailURL + '\'' +
                '}';
    }
}
