package com.example.moviestmdb.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostModel {
    @SerializedName("id")
    private String postId;

    @SerializedName("original_title")
    private String postTitle;

    @SerializedName("overview")
    private String postOverview;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("vote_avegare")
    private float voteAverage;

    @SerializedName("vote_count")
    private int voteCount;

    @SerializedName("gender_ids")
    private List<Integer> genderIdsList;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostOverview() {
        return postOverview;
    }

    public void setPostOverview(String postOverview) {
        this.postOverview = postOverview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public List<Integer> getGenderIdsList() {
        return genderIdsList;
    }

    public void setGenderIdsList(List<Integer> genderIdsList) {
        this.genderIdsList = genderIdsList;
    }
}
