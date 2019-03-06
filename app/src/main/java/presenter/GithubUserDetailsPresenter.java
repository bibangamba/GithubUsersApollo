package presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.levelup.bibangamba.githubusers.R;

import model.GithubUsers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.GithubService;
import view.GithubUserDetailsView;

public class GithubUserDetailsPresenter {
    private Context context;
    private String TAG;
    private GithubUserDetailsView githubUserDetailsView;
    private GithubService githubService;

    public GithubUserDetailsPresenter(Context context, GithubUserDetailsView view) {
        this.context = context;
        TAG = context.getString(R.string.GithubUserDetailsPresenterTag);
        this.githubUserDetailsView = view;
        this.githubService = new GithubService();
    }

    public void getGithubUserInfo(String username) {
        githubService
                .getApi()
                .getUserInformation(username)
                .enqueue((new Callback<GithubUsers>() {
                    @Override
                    public void onResponse(@NonNull Call<GithubUsers> call, @NonNull Response<GithubUsers> response) {
                        GithubUsers githubUser = response.body();
                        assert githubUser != null;
                        githubUserDetailsView.githubUserInformationFetchComplete(githubUser);
                    }

                    @Override
                    public void onFailure(@NonNull Call<GithubUsers> call, @NonNull Throwable t) {
                        try {
                            throw new InterruptedException(context.getString(R.string.error_message_when_retrieving_data_from_api));
                        } catch (InterruptedException e) {
                            Log.e(TAG, e.toString());
                        }
                    }
                }));
    }

}
