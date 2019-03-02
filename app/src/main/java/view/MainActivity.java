package view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.levelup.bibangamba.githubusers.R;

import java.util.List;

import adapters.GithubUsersAdapter;
import model.GithubUsers;
import presenter.GithubUsersPresenter;

public class MainActivity extends AppCompatActivity implements GithubUsersView {
    RecyclerView githubUsersRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        githubUsersRecyclerView = findViewById(R.id.recycler_view);
        githubUsersRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        githubUsersRecyclerView.setLayoutManager(layoutManager);
        GithubUsersPresenter githubUsersPresenter = new GithubUsersPresenter(this, this);
        githubUsersPresenter.getGithubUsers();
    }

    @Override
    public void githubUsersHaveBeenFetchedAndAreReadyForUse(List<GithubUsers> githubUsers) {
        githubUsersRecyclerView.setAdapter(new GithubUsersAdapter(MainActivity.this, githubUsers));
    }
}
