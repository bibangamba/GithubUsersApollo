package com.levelup.bibangamba.githubusersGraphQL.service;

import com.apollographql.apollo.ApolloClient;
import com.levelup.bibangamba.githubusersGraphQL.type.CustomType;
import com.levelup.bibangamba.githubusersGraphQL.util.URICustomTypeAdapter;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

public class ApolloClientGHUsersService {
    private static final String BASE_URL = "https://api.github.com/graphql";
    private static ApolloClient apolloClientGHUsers;

    public static ApolloClient getApolloClientGHUsersInstance() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    Request.Builder requestBuilder = originalRequest.newBuilder().method(originalRequest.method(), originalRequest.body());
                    requestBuilder.addHeader("Authorization", "bearer 534650e70d35afcd45056e307fb1d582764750c8");
                    return chain.proceed(requestBuilder.build());
                })
                .build();

        apolloClientGHUsers = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .addCustomTypeAdapter(CustomType.URI, new URICustomTypeAdapter())
                .build();

        return apolloClientGHUsers;
    }
}
