package com.example.moviestmdb.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CastModel {

    @SerializedName("cast")
    private List<CastModel> castResult;

    @SerializedName("original_name")
    private String name;

    @SerializedName("character")
    private String characterName;

    @SerializedName("profile_path")
    private String profilePath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public List<CastModel> getCastResult() {
        return castResult;
    }

    public void setCastResult(List<CastModel> castResult) {
        this.castResult = castResult;
    }
}
