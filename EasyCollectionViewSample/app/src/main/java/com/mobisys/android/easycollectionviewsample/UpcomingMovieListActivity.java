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
                public void bindViewId(View view, int position) {
                    if (movieWrapper.getMovies().get(position).isAdult()){
                        view.setVisibility(View.VISIBLE);
                    } else {
                        view.setVisibility(View.GONE);
                    }
                }
            });
            easyListView.setOnViewIdClickListener(R.id.btnVoteCount, new OnViewIdClickListener() {
                @Override
                public void onViewIdClickListener(View view, int position) {
                    Movie movie = movieWrapper.getMovies().get(position);
                    Toast.makeText(UpcomingMovieListActivity.this, "VoteCount is "+movie.getVote_count(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (ModelTypeMismatchException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_upcoming_movie_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
