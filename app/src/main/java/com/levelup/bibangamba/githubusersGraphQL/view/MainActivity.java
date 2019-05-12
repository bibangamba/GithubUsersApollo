package com.levelup.bibangamba.githubusersGraphQL.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.Snackbar;
import android.support.test.espresso.IdlingResource;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.levelup.bibangamba.githubusersGraphQL.Get40KenyanJavaDevsQuery;
import com.levelup.bibangamba.githubusersGraphQL.R;
import com.levelup.bibangamba.githubusersGraphQL.adapters.GithubUsersEdgesAdapter;
import com.levelup.bibangamba.githubusersGraphQL.presenter.GithubUserGraphQLPresenter;
import com.levelup.bibangamba.githubusersGraphQL.presenter.GithubUsersPresenter;
import com.levelup.bibangamba.githubusersGraphQL.service.GithubService;
import com.levelup.bibangamba.githubusersGraphQL.util.CheckNetworkConnection;
import com.levelup.bibangamba.githubusersGraphQL.util.EspressoIdlingResource;

import java.util.List;

public class MainActivity extends AppCompatActivity implements GithubUsersView,
        SwipeRefreshLayout.OnRefreshListener {
    public static final String LIST_STATE_KEY = "recycler_list_state";
    public static final String GITHUB_USERS = "retrieved_github_users";
    RecyclerView githubUsersRecyclerView;
    Parcelable listState;
    RecyclerView.LayoutManager layoutManager;
    List<Get40KenyanJavaDevsQuery.Edge> retrievedEdges;
    SwipeRefreshLayout githubUsersSwipeToRefreshLayout;
    GithubUsersPresenter githubUsersPresenter;
    ProgressBar githubUsersProgressBar;
    GithubUserGraphQLPresenter githubUserGraphQLPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        githubUsersRecyclerView = findViewById(R.id.recycler_view);
        githubUsersSwipeToRefreshLayout = findViewById(R.id.github_users_swipe_refresh_layout);
        githubUsersProgressBar = findViewById(R.id.github_users_progress_bar);
        githubUsersRecyclerView.setHasFixedSize(true);

        if (this.getResources().getConfiguration().orientation == Configuration
                .ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(MainActivity.this, 2);
        } else {
            layoutManager = new GridLayoutManager(MainActivity.this, 3);
        }

        githubUsersRecyclerView.setLayoutManager(layoutManager);
        githubUsersPresenter = new GithubUsersPresenter(this, this);
        githubUserGraphQLPresenter = new GithubUserGraphQLPresenter(this,
                this, new GithubService());


//        if (savedInstanceState == null) {
        loadGithubUsers();
//        }
        githubUsersSwipeToRefreshLayout.setOnRefreshListener(this);

    }

    public void loadGithubUsers() {
        if (new CheckNetworkConnection(MainActivity.this).isNetworkAvailable()) {
            githubUserGraphQLPresenter.getGithubUsersInfo();
            EspressoIdlingResource.increment();
        } else {
            githubUsersProgressBar.setVisibility(View.GONE);
            Snackbar.make(findViewById(R.id.main_activity_constraint_layout),
                    R.string.no_internet,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.retry, new NoInternetSnackBarListener())
                    .show();
        }
    }

    @Override
    public void githubUserInformationFetchFromGraphQLComplete(
            List<Get40KenyanJavaDevsQuery.Edge> edges) {
        retrievedEdges = edges;
        runOnUiThread(() -> {
            githubUsersRecyclerView.setAdapter(new GithubUsersEdgesAdapter(this, edges));
            githubUsersRecyclerView.setVisibility(View.VISIBLE);
            githubUsersProgressBar.setVisibility(View.GONE);

            EspressoIdlingResource.decrement();
        });
    }


    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        listState = layoutManager.onSaveInstanceState();
        state.putParcelable(LIST_STATE_KEY, listState);
    }

    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        if (state != null) {
            listState = state.getParcelable(LIST_STATE_KEY);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (listState != null) {
            githubUsersRecyclerView.setAdapter(new GithubUsersEdgesAdapter(
                    MainActivity.this, retrievedEdges));
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
        loadGithubUsers();
        githubUsersSwipeToRefreshLayout.setRefreshing(false);
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }

    private class NoInternetSnackBarListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            loadGithubUsers();
        }
    }
}
