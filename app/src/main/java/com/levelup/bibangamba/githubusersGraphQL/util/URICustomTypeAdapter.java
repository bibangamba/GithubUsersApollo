package com.levelup.bibangamba.githubusersGraphQL.util;

import android.net.Uri;

import com.apollographql.apollo.response.CustomTypeAdapter;
import com.apollographql.apollo.response.CustomTypeValue;

import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

//TODO remove this once you understand this class. but for now, let's experiment
public class URICustomTypeAdapter implements CustomTypeAdapter<Uri> {
    String url = "";
    @Override
    public Uri decode(@NotNull CustomTypeValue value) {
        url = value.toString();
        return Uri.parse(value.toString());
    }

    @NotNull
    @Override
    public CustomTypeValue encode(@NotNull Uri value) {
        try {
            return new CustomTypeValue.GraphQLString(URLEncoder.encode(value.toString(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @NotNull
    @Override
    public String toString() {
        return url;
    }
}
