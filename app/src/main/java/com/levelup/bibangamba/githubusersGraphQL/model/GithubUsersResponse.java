package com.levelup.bibangamba.githubusersGraphQL.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GithubUsersResponse {

    @SerializedName("items")
    private List<GithubUsers> users;

    public GithubUsersResponse() {
        this.users = new ArrayList<>();
    }

    public List<GithubUsers> getUsers() {
        return users;
    }

}
