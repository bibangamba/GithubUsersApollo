package view;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.levelup.bibangamba.githubusers.R;

import java.util.ArrayList;
import java.util.List;

import adapters.GithubUsersAdapter;
import model.GithubUsers;
import presenter.GithubUsersPresenter;

public class MainActivity extends AppCompatActivity implements GithubUsersView {
    public static final String LIST_STATE_KEY = "recycler_list_state";
    public static final String GITHUB_USERS = "retrieved_github_users";
    RecyclerView githubUsersRecyclerView;
    Parcelable listState;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<GithubUsers> retrievedGithubUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        githubUsersRecyclerView = findViewById(R.id.recycler_view);
        githubUsersRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        githubUsersRecyclerView.setLayoutManager(layoutManager);
        if (savedInstanceState == null) {
            GithubUsersPresenter githubUsersPresenter = new GithubUsersPresenter(this, this);
            githubUsersPresenter.getGithubUsers();
        }
    }

    @Override
    public void githubUsersHaveBeenFetchedAndAreReadyForUse(List<GithubUsers> githubUsers) {
        retrievedGithubUsers = (ArrayList<GithubUsers>) githubUsers;
        githubUsersRecyclerView.setAdapter(new GithubUsersAdapter(MainActivity.this, githubUsers));
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
        }
    }
}
