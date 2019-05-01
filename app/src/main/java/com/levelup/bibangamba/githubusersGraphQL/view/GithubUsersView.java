package com.levelup.bibangamba.githubusersGraphQL.view;

import java.util.List;

import com.levelup.bibangamba.githubusersGraphQL.model.GithubUsers;

public interface GithubUsersView {
    void githubUsersHaveBeenFetchedAndAreReadyForUse(List<GithubUsers> githubUsers);
}
