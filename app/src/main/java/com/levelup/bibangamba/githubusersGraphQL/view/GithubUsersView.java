package com.levelup.bibangamba.githubusersGraphQL.view;

import java.util.List;

import com.levelup.bibangamba.githubusersGraphQL.Get40KenyanJavaDevsQuery;
import com.levelup.bibangamba.githubusersGraphQL.model.GithubUsers;

public interface GithubUsersView {
    void githubUserInformationFetchFromGraphQLComplete(List<Get40KenyanJavaDevsQuery.Edge> edges);
}
