package view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.levelup.bibangamba.githubusers.R;

import java.util.ArrayList;
import java.util.List;

import adapters.GithubUsersAdapter;
import model.GithubUsers;
import presenter.GithubUsersPresenter;

public class MainActivity extends AppCompatActivity implements GithubUsersView, SwipeRefreshLayout.OnRefreshListener {
    public static final String LIST_STATE_KEY = "recycler_list_state";
    public static final String GITHUB_USERS = "retrieved_github_users";
    RecyclerView githubUsersRecyclerView;
    Parcelable listState;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<GithubUsers> retrievedGithubUsers;
    SwipeRefreshLayout githubUsersSwipeToRefreshLayout;
    GithubUsersPresenter githubUsersPresenter;
    ProgressBar githubUsersProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        githubUsersRecyclerView = findViewById(R.id.recycler_view);
        githubUsersSwipeToRefreshLayout = findViewById(R.id.github_users_swipe_refresh_layout);
        githubUsersProgressBar = findViewById(R.id.github_users_progress_bar);
        githubUsersRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(MainActivity.this);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(MainActivity.this, 2);
        } else {
            layoutManager = new GridLayoutManager(MainActivity.this, 3);
        }

        githubUsersRecyclerView.setLayoutManager(layoutManager);
        githubUsersPresenter = new GithubUsersPresenter(this, this);
        if (savedInstanceState == null) {
            githubUsersPresenter.getGithubUsers();
        }
        githubUsersSwipeToRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void githubUsersHaveBeenFetchedAndAreReadyForUse(List<GithubUsers> githubUsers) {
        retrievedGithubUsers = (ArrayList<GithubUsers>) githubUsers;
        githubUsersRecyclerView.setAdapter(new GithubUsersAdapter(MainActivity.this, githubUsers));
        githubUsersRecyclerView.setVisibility(View.VISIBLE);
        githubUsersProgressBar.setVisibility(View.GONE);
    }


    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putParcelableArrayList(GITHUB_USERS, retrievedGithubUsers);
        listState = layoutManager.onSaveInstanceState();
        state.putParcelable(LIST_STATE_KEY, listState);
    }

    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        if (state != null) {
            retrievedGithubUsers = state.getParcelableArrayList(GITHUB_USERS);
            listState = state.getParcelable(LIST_STATE_KEY);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (listState != null) {
            githubUsersRecyclerView.setAdapter(new GithubUsersAdapter(MainActivity.this, retrievedGithubUsers));
            layoutManager.onRestoreInstanceState(listState);
            githubUsersRecyclerView.setVisibility(View.VISIBLE);
            githubUsersProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRefresh() {
        refreshGithubUsers();
    }

    private void refreshGithubUsers() {
        githubUsersPresenter.getGithubUsers();
        githubUsersSwipeToRefreshLayout.setRefreshing(false);
    }
}
