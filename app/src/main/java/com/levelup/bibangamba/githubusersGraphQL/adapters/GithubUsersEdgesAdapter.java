package com.levelup.bibangamba.githubusersGraphQL.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.levelup.bibangamba.githubusersGraphQL.Get40KenyanJavaDevsQuery;
import com.levelup.bibangamba.githubusersGraphQL.R;
import com.levelup.bibangamba.githubusersGraphQL.model.GithubUsers;
import com.levelup.bibangamba.githubusersGraphQL.view.DetailActivity;

import java.util.List;

public class GithubUsersEdgesAdapter extends RecyclerView.Adapter<GithubUsersEdgesAdapter.GithubUserViewHolder> {
    private Context context;
    private List<Get40KenyanJavaDevsQuery.Edge> githubUsers;
    private String GITHUB_USER_DETAILS;

    public GithubUsersEdgesAdapter(Context context, List<Get40KenyanJavaDevsQuery.Edge> listOfGithubUsers) {
        this.context = context;
        this.githubUsers = listOfGithubUsers;
        GITHUB_USER_DETAILS = context.getString(R.string.github_user_details);
    }


    @NonNull
    @Override
    public GithubUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View githubUserListItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.github_user_grid_item, parent, false);
        return new GithubUserViewHolder(githubUserListItem);
    }

    @Override
    public void onBindViewHolder(@NonNull GithubUserViewHolder githubUserViewHolder, int position) {
        Get40KenyanJavaDevsQuery.AsUser githubUserNode = (Get40KenyanJavaDevsQuery.AsUser)
                githubUsers.get(position).node();
        if (githubUserNode!=null){
            GithubUsers githubUser = new GithubUsers();
            githubUser.setUsername(githubUserNode.login());
            githubUser.setProfilePicture(githubUserNode.avatarUrl());
            githubUser.setFollowers(githubUserNode.followers().toString());
            githubUser.setFollowing(githubUserNode.following().toString());
            githubUser.setRepos(githubUserNode.repositories().toString());
            githubUser.setCompany(githubUserNode.company());

            githubUserViewHolder.githubUsernameTextView.setText(githubUser.getUsername());
            Glide
                    .with(context)
                    .load(githubUserNode.avatarUrl())
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .into(githubUserViewHolder.profilePictureImageView);

            githubUserViewHolder.itemView.setOnClickListener(v -> {
                Intent startDetailActivityIntent = new Intent(context, DetailActivity.class);
                startDetailActivityIntent.putExtra(GITHUB_USER_DETAILS, githubUser);
                context.startActivity(startDetailActivityIntent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return githubUsers.size();
    }

    static class GithubUserViewHolder extends RecyclerView.ViewHolder {
        TextView githubUsernameTextView;
        ImageView profilePictureImageView;

        GithubUserViewHolder(View itemView) {
            super(itemView);
            this.githubUsernameTextView = itemView.findViewById(R.id.githubUsernameTextView);
            this.profilePictureImageView = itemView.findViewById(R.id.profilePictureImageView);
        }
    }
}
