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

    @SerializedName("runtime")
    private String runtime;

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("vote_average")
    private float voteAverage;

    @SerializedName("vote_count")
    private int voteCount;

    @SerializedName("genre_ids")
    private List<Integer> genreIdsList;

    @SerializedName("genres")
    private List<GenreModel> genreModelList;

    @SerializedName("cast")
    private List<CastModel> castModelList;

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

    public List<Integer> getGenreIdsList() {
        return genreIdsList;
    }

    public void setGenreIdsList(List<Integer> genreIdsList) {
        this.genreIdsList = genreIdsList;
    }

    public List<GenreModel> getGenreModelList() {
        return genreModelList;
    }

    public void setGenreModelList(List<GenreModel> genreModelList) {
        this.genreModelList = genreModelList;
    }

    public List<CastModel> getCastModelList() {
        return castModelList;
    }

    public void setCastModelList(List<CastModel> castModelList) {
        this.castModelList = castModelList;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }
}
