package presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.levelup.bibangamba.githubusers.R;

import java.util.List;

import model.GithubUsers;
import model.GithubUsersResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.GithubService;
import view.GithubUsersView;

public class GithubUsersPresenter {
    private String TAG;
    private GithubUsersView githubUsersView;
    private GithubService githubService;

    public GithubUsersPresenter(Context context, GithubUsersView view) {
        TAG = context.getString(R.string.GithubUsersPresenterTag);
        this.githubUsersView = view;
        if (this.githubService.equals(null)) {
            this.githubService = new GithubService();
        }

    }

    public void getGithubUsers() {
        githubService
                .getApi()
                .getAllUsers()
                .enqueue(new Callback<GithubUsersResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<GithubUsersResponse> call, @NonNull Response<GithubUsersResponse> response) {
                        GithubUsersResponse githubUsersResponse = response.body();
                        if (!githubUsersResponse.equals(null) && !(githubUsersResponse.getUsers()).equals(null)) {
                            List<GithubUsers> users = githubUsersResponse.getUsers();
                            githubUsersView.githubUsersHaveBeenFetchedAndAreReady(users);
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<GithubUsersResponse> call, @NonNull Throwable t) {
                        try {
                            throw new InterruptedException("Failed to retrieve GithubUsersResponse");
                        } catch (InterruptedException e) {
                            Log.e(TAG, e.toString());
                        }
                    }
                });
    }
}
