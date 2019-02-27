package model;

import com.google.gson.annotations.SerializedName;

public class GithubUsers {
    @SerializedName("avatar_url")
    private String profilePicture;
    @SerializedName("login")
    private String username;
    @SerializedName("html_url")
    private String bio;
    @SerializedName("followers_url")
    private String followersUrl;
    @SerializedName("following_url")
    private String followingUrl;

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getFollowersUrl() {
        return followersUrl;
    }

    public void setFollowersUrl(String followersUrl) {
        this.followersUrl = followersUrl;
    }

    public String getFollowingUrl() {
        return followingUrl;
    }

    public void setFollowingUrl(String followingUrl) {
        this.followingUrl = followingUrl;
    }
}
