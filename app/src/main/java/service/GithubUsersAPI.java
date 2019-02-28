package service;

import model.GithubUsersResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GithubUsersAPI {
    @GET("search/users?q=type:User+location:Nairobi+language:JAVA")
    Call<GithubUsersResponse> getAllUsers();
}
