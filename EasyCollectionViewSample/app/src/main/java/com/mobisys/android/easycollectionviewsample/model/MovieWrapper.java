package com.mobisys.android.easycollectionviewsample.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * Created by mahavir on 7/4/15.
 */
public class MovieWrapper {
    private int page;
    private int total_pages;
    private int total_results;
    @JsonProperty("results")
    private ArrayList<Movie> movies;

    public MovieWrapper() {
    }

    public MovieWrapper(int page, int total_pages, int total_results, ArrayList<Movie> movies) {
        this.page = page;
        this.total_pages = total_pages;
        this.total_results = total_results;
        this.movies = movies;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieWrapper)) return false;

        MovieWrapper that = (MovieWrapper) o;

        if (page != that.page) return false;
        if (total_pages != that.total_pages) return false;
        if (total_results != that.total_results) return false;
        return !(movies != null ? !movies.equals(that.movies) : that.movies != null);

    }

    @Override
    public int hashCode() {
        int result = page;
        result = 31 * result + total_pages;
        result = 31 * result + total_results;
        result = 31 * result + (movies != null ? movies.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MovieWrapper{" +
                "page=" + page +
                ", total_pages=" + total_pages +
                ", total_results=" + total_results +
                ", movies=" + movies +
                '}';
    }
}
