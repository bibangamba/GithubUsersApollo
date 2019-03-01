package view;

import java.util.List;

import model.GithubUsers;

public interface GithubUsersView {
    void githubUsersHaveBeenFetchedAndAreReady(List<GithubUsers> githubUsers);
}
