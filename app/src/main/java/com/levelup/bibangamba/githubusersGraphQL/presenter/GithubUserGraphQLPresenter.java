package com.levelup.bibangamba.githubusersGraphQL.presenter;

import android.content.Context;
import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.ResponseField;
import com.apollographql.apollo.api.ResponseFieldMarshaller;
import com.apollographql.apollo.api.ResponseWriter;
import com.apollographql.apollo.exception.ApolloException;
import com.levelup.bibangamba.githubusersGraphQL.Get40KenyanJavaDevsQuery;
import com.levelup.bibangamba.githubusersGraphQL.R;
import com.levelup.bibangamba.githubusersGraphQL.service.ApolloClientGHUsersService;
import com.levelup.bibangamba.githubusersGraphQL.service.GithubService;
import com.levelup.bibangamba.githubusersGraphQL.view.GithubUsersView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class GithubUserGraphQLPresenter {
    private Context context;
    private String TAG;
    private GithubUsersView githubUsersView;
    private GithubService githubService;

    public GithubUserGraphQLPresenter(Context context, GithubUsersView view, GithubService githubService) {
        this.context = context;
        TAG = context.getString(R.string.GithubUserDetailsPresenterTag);
        this.githubUsersView = view;
        this.githubService = githubService;
    }

    public void getGithubUsersInfo() {
        ApolloClientGHUsersService.getApolloClientGHUsersInstance()
                .query(Get40KenyanJavaDevsQuery.builder().build())
                .enqueue(new ApolloCall.Callback<Get40KenyanJavaDevsQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull com.apollographql.apollo.api.
                            Response<Get40KenyanJavaDevsQuery.Data> response) {
                        if (response.data() != null) {
                            githubUsersView.githubUserInformationFetchFromGraphQLComplete(
                                    response.data().search().edges());
                            Get40KenyanJavaDevsQuery.AsUser node =
                                    (Get40KenyanJavaDevsQuery.AsUser)
                                            Objects.requireNonNull(response.data().search()
                                                    .edges()).get(0).node();

                            Log.d("debugging gql",
                                    "################################# logged data: " +
                                            (node.login()));
                            Log.d("debugging gql",
                                    "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ logged data: " +
                                            (node.avatarUrl()));
                        }
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        Log.d("debugging gql",
                                "################################# no data for logging. " +
                                        "request failed. Error: " + e.getMessage());
                        e.printStackTrace();
                    }
                });
    }

}
