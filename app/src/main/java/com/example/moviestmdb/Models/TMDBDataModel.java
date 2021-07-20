package com.example.moviestmdb.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TMDBDataModel {
    @SerializedName("page")
    private int requestPage;

    @SerializedName("results")
    private List<PostModel> results;

    public int getRequestPage() {
        return requestPage;
    }

    public void setRequestPage(int requestPage) {
        this.requestPage = requestPage;
    }

    public List<PostModel> getResults() {
        return results;
    }

    public void setResults(List<PostModel> results) {
        this.results = results;
    }
}
