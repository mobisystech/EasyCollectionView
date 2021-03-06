package com.mobisys.android.easycollectionviewsample;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mobisys.android.easycollectionview.EasyListView;
import com.mobisys.android.easycollectionview.OnViewIdClickListener;
import com.mobisys.android.easycollectionview.ViewIdBinder;
import com.mobisys.android.easycollectionview.exceptions.ModelTypeMismatchException;
import com.mobisys.android.easycollectionviewsample.model.Movie;
import com.mobisys.android.easycollectionviewsample.model.MovieWrapper;
import com.mobisys.android.easycollectionviewsample.rest.Request;
import com.mobisys.android.easycollectionviewsample.rest.RestCallback;
import com.mobisys.android.easycollectionviewsample.rest.RestClient;

import retrofit.client.Response;

public class UpcomingMovieListActivity extends AppCompatActivity {

    private ProgressDialog mPgDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_movie_list);

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
            EasyListView easyListView = (EasyListView)findViewById(R.id.movieList);
            easyListView.setArrayList(movieWrapper.getMovies());
            easyListView.bindViewId(R.id.adult, new ViewIdBinder() {
                @Override
                public void bindViewId(int position, View bindView, View rowView) {
                    if (movieWrapper.getMovies().get(position).isAdult()){
                        bindView.setVisibility(View.VISIBLE);
                    } else {
                        bindView.setVisibility(View.GONE);
                    }
                }
            });
            easyListView.setOnViewIdClickListener(R.id.btnVoteCount, new OnViewIdClickListener() {
                @Override
                public void onViewIdClickListener(int position, View bindView, View rowView) {
                    Movie movie = movieWrapper.getMovies().get(position);
                    Toast.makeText(UpcomingMovieListActivity.this, "VoteCount is "+movie.getVote_count(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (ModelTypeMismatchException e) {
            e.printStackTrace();
        }
    }
}
