package com.example.newsfeed.Adapters;

import android.content.Context;


import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class QueryLoader extends AsyncTaskLoader<List<Model>> {

    private String mURL;

    public QueryLoader(Context context, String url) {
        super(context);
        mURL = url;
    }

    @Override

    protected void onStartLoading() {
        forceLoad();
    }


    @Override
    public List<Model> loadInBackground() {
        if (mURL==null) {
            return null;
        }

        List<Model> model = Query.fetchNews(getContext(), mURL);
        return model;
    }

}
