package com.example.moviestmdb.Models;

import com.google.gson.annotations.SerializedName;

public class GenreModel {
    @SerializedName("id")
    private int genreId;

    @SerializedName("name")
    private String genreName;

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
