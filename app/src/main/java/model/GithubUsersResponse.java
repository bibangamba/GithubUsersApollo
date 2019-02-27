package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GithubUsersResponse {

    @SerializedName("items")
    private List<GithubUsers> users;

    public GithubUsersResponse() {
        this.users = new ArrayList<>();
    }

    public static GithubUsersResponse parseResponseJSON(String responseJSON) {
        Gson gson = new GsonBuilder().create();

        return gson.fromJson(responseJSON, GithubUsersResponse.class);
    }

    public List<GithubUsers> getUsers() {
        return users;
    }

    public void setUsers(List<GithubUsers> users) {
        this.users = users;
    }
}
