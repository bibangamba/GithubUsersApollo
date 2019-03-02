package view;

import java.util.List;

import model.GithubUsers;

public interface GithubUsersView {
    void githubUsersHaveBeenFetchedAndAreReadyForUse(List<GithubUsers> githubUsers);
}
