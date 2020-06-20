package com.example.newsfeed.FragManager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.newsfeed.Adapters.Adapter;
import com.example.newsfeed.Adapters.Model;
import com.example.newsfeed.Adapters.QueryLoader;
import com.example.newsfeed.R;

import java.util.ArrayList;
import java.util.List;


public class SportsFrag extends Fragment implements LoaderManager.LoaderCallbacks<List<Model>> {

    private static final String SPORTS_API =
            "http://newsapi.org/v2/top-headlines?country=us&category=sports&apiKey=6a6f7c77766442acb20c86157a152131";
    public static final String API_KEY = "6a6f7c77766442acb20c86157a152131";

    private Adapter mAdapter;
    RecyclerView recyclerView;
    List<Model> newsList;
    private static final int SPORTS_LOADER = 2;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView emptyStateView;



    public SportsFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_main, container, false);
        setHasOptionsMenu(true);

        emptyStateView = rootView.findViewById(R.id.emptyTextView);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        swipeRefreshLayout = rootView.findViewById(R.id.swiperefresh);

        newsList = new ArrayList<>();
        mAdapter = new Adapter(getActivity(), newsList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getContext(), "News are updated every 3 hours", Toast.LENGTH_SHORT).show();
                mAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);




          if (connectivityManager != null) {
              NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
              if (networkInfo != null) {

                  LoaderManager loaderManager = getActivity().getSupportLoaderManager();
                  loaderManager.initLoader(SPORTS_LOADER, null, this);
                  swipeRefreshLayout.post(new Runnable() {
                      @Override
                      public void run() {
                          swipeRefreshLayout.setRefreshing(true);
                      }
                  });


                  if (loaderManager.restartLoader(SPORTS_LOADER, null, this).isReset()) {
                      swipeRefreshLayout.setRefreshing(false);

                  }

              } else {
                  swipeRefreshLayout.setRefreshing(true);
                  emptyStateView.setText("No Internet Connection");
                  emptyStateView.setVisibility(View.VISIBLE);

              }
          }


    return rootView;

}



    @Override
    public Loader<List<Model>> onCreateLoader(int id, Bundle args) {
        return new QueryLoader(getContext(), SPORTS_API);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Model>> loader, List<Model> newsList) {
        swipeRefreshLayout.setRefreshing(false);

        if (newsList!=null && !newsList.isEmpty()) {

            mAdapter = new Adapter(getActivity(), newsList);
            recyclerView.setAdapter(mAdapter);
        }

    }

    @Override
    public void onLoaderReset( Loader<List<Model>> loader) {
        swipeRefreshLayout.setRefreshing(false);

    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu , menu);

        MenuItem etSearch = menu.findItem(R.id.search_bar);

        android.widget.SearchView searchView = (android.widget.SearchView)etSearch.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }
}




