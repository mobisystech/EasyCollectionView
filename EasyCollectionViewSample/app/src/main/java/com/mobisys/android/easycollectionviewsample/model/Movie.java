package com.mobisys.android.easycollectionviewsample.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.mobisys.android.easycollectionview.annotations.ViewId;
import com.mobisys.android.easycollectionviewsample.R;
import com.mobisys.android.easycollectionviewsample.rest.Request;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mahavir on 7/4/15.
 */
public class Movie implements Parcelable {
    private static final SimpleDateFormat RELEASE_SDF = new SimpleDateFormat("dd/MM/yyyy");
    private boolean adult;
    private long id;
    private String overview;
    private Date release_date;
    private String title;
    private float vote_average;
    private int vote_count;
    private String poster_path;

    public Movie() {
    }

    public Movie(Parcel in) {
        readFromParcel(in);
    }

    public Movie(boolean adult, long id, String overview, Date date, String title, float vote_average, int vote_count, String poster_path) {
        this.adult = adult;
        this.id = id;
        this.overview = overview;
        this.release_date = date;
        this.title = title;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.poster_path = poster_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Date getRelease_date() {
        return release_date;
    }

    @ViewId(id=R.id.release_date)
    public String getReleaseDate() {
        return RELEASE_SDF.format(release_date);
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    @ViewId(id = R.id.movie_title)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ViewId(id=R.id.movie_rate)
    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    @ViewId(id = R.id.movie_poster)
    public String getPoster_path() {
        return Request.IMAGE_PATH+poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;

        Movie movieNew = (Movie) o;

        if (adult != movieNew.adult) return false;
        if (id != movieNew.id) return false;
        if (Float.compare(movieNew.vote_average, vote_average) != 0) return false;
        if (vote_count != movieNew.vote_count) return false;
        if (overview != null ? !overview.equals(movieNew.overview) : movieNew.overview != null)
            return false;
        if (release_date != null ? !release_date.equals(movieNew.release_date) : movieNew.release_date != null)
            return false;
        if (title != null ? !title.equals(movieNew.title) : movieNew.title != null) return false;
        return !(poster_path != null ? !poster_path.equals(movieNew.poster_path) : movieNew.poster_path != null);

    }

    @Override
    public int hashCode() {
        int result = (adult ? 1 : 0);
        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + (overview != null ? overview.hashCode() : 0);
        result = 31 * result + (release_date != null ? release_date.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (vote_average != +0.0f ? Float.floatToIntBits(vote_average) : 0);
        result = 31 * result + vote_count;
        result = 31 * result + (poster_path != null ? poster_path.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MovieNew{" +
                "adult=" + adult +
                ", id=" + id +
                ", overview='" + overview + '\'' +
                ", release_date=" + release_date +
                ", title='" + title + '\'' +
                ", vote_average=" + vote_average +
                ", vote_count=" + vote_count +
                ", poster_path='" + poster_path + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(adult?1:0);
        parcel.writeLong(id);
        parcel.writeString(overview);
        parcel.writeSerializable(release_date);
        parcel.writeString(title);
        parcel.writeFloat(vote_average);
        parcel.writeInt(vote_count);
        parcel.writeString(poster_path);
    }

    public void readFromParcel(Parcel in){
        adult = in.readInt()==1;
        id = in.readLong();
        overview = in.readString();
        release_date = (Date)in.readSerializable();
        title = in.readString();
        vote_average = in.readFloat();
        vote_count = in.readInt();
        poster_path = in.readString();
    }

    public static Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
