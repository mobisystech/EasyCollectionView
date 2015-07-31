package com.mobisys.android.easycollectionviewsample;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mobisys.android.easycollectionview.EasyListView;
import com.mobisys.android.easycollectionview.EasyRecyclerView;
import com.mobisys.android.easycollectionview.OnViewIdClickListener;
import com.mobisys.android.easycollectionview.ViewIdBinder;
import com.mobisys.android.easycollectionview.exceptions.ModelTypeMismatchException;
import com.mobisys.android.easycollectionviewsample.model.Movie;
import com.mobisys.android.easycollectionviewsample.model.MovieWrapper;
import com.mobisys.android.easycollectionviewsample.rest.Request;
import com.mobisys.android.easycollectionviewsample.rest.RestCallback;
import com.mobisys.android.easycollectionviewsample.rest.RestClient;

import retrofit.client.Response;

public class MovieRecyclerListActivity extends AppCompatActivity {

    private ProgressDialog mPgDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_recycler_list);
        mPgDialog = ProgressDialog.show(this, null, "Loading movies..");
        RestClient.getMovieApi(this).getMovieList(Request.API_KEY, new RestCallback<MovieWrapper>() {
            @Override
            public void failure(String restErrors, boolean networkError) {

            }

            @Override
            public void success(MovieWrapper movieWrapper, Response response) {
                showList(movieWrapper);
            }
        });
    }

    private void showList(final MovieWrapper movieWrapper){
        try {
            if (mPgDialog!=null && mPgDialog.isShowing()) mPgDialog.dismiss();
            EasyRecyclerView easyRecyclerView = (EasyRecyclerView)findViewById(R.id.movieList);
            easyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            easyRecyclerView.setArrayList(movieWrapper.getMovies());
            easyRecyclerView.bindViewId(R.id.adult, new ViewIdBinder() {
                @Override
                public void bindViewId(int position, View bindView, View rowView) {
                    if (movieWrapper.getMovies().get(position).isAdult()){
                        bindView.setVisibility(View.VISIBLE);
                    } else {
                        bindView.setVisibility(View.GONE);
                    }
                }
            });
            easyRecyclerView.setOnViewIdClickListener(R.id.btnVoteCount, new OnViewIdClickListener() {
                @Override
                public void onViewIdClickListener(int position, View clicked, View rowView) {
                    Movie movie = movieWrapper.getMovies().get(position);
                    Toast.makeText(MovieRecyclerListActivity.this, "VoteCount is " + movie.getVote_count(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (ModelTypeMismatchException e) {
            e.printStackTrace();
        }
    }
}
