package com.redsparkdev.moviestalker.utilities;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.redsparkdev.moviestalker.MovieDetailActivity;
import com.redsparkdev.moviestalker.storageObjects.ReviewInfo;
import com.redsparkdev.moviestalker.storageObjects.TrailerInfo;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Red on 22/05/2017.
 */

public class FetchMovieReviews implements LoaderManager.LoaderCallbacks<ReviewInfo[]>{
    private final static String TAG = FetchMovieReviews.class.getSimpleName().toString();

    //AppCompatActivity is a super class of MovieDerailActivity;
    private final AppCompatActivity callerClass;
    private final MovieDetailActivity movieDetailActivity;
    public FetchMovieReviews(AppCompatActivity callerClass){
        this.callerClass = callerClass;
        this.movieDetailActivity = (MovieDetailActivity)callerClass;
    }

    @Override
    //Bundle args should contain id of the movie
    public Loader<ReviewInfo[]> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<ReviewInfo[]>(callerClass) {


            @Override
            public void onStartLoading(){
                //no id, so nothing to do
                if (args == null) {
                    return;
                }
                //Clear the list so we don't have duplicates
                movieDetailActivity.getMovieInfoRefrance().clearReviewList();
                //TODO create a loading indicator for data

                //TODO Not sure why forceLoad() in needed.Find out
                forceLoad();
            }

            @Override
            public ReviewInfo [] loadInBackground() {
                //gets the id and stores in in a string
                String id = args.getString(NetworkUtil.ExtraData.ID_KEY);

                //requests a URL for the id
                URL reviewListUrl = NetworkUtil.buildTrailerListUrl(id);
                try{
                    //gets the raw json
                    String jsonReviewListResponse = NetworkUtil.getResponseFromHttpUrl(reviewListUrl);
                    Log.v(TAG, jsonReviewListResponse);

                    //uses the MoviedbJsonUtil to sort and store the json data in a ReviewInfo object
                    ReviewInfo [] reviews = MoviedbJsonUtil.getReviewObjects(jsonReviewListResponse);
                    return reviews;
                }catch(IOException e){
                    e.printStackTrace();
                }
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ReviewInfo[]> loader, ReviewInfo[] data) {


        if(data != null){

            Log.v(TAG, data.toString());
            //set the review to the appropriate movie.
            movieDetailActivity.getMovieInfoRefrance().setReviews(data);

            //displayed the trailers
            movieDetailActivity.loadReviews();
        }else{
            movieDetailActivity.showError();

        }
    }

    @Override
    public void onLoaderReset(Loader<ReviewInfo[]> loader) {

    }
}