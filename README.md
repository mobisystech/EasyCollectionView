# EasyCollectionView
EasyListView &amp; EasyGridView. Just provide the model name &amp; row layout from xml. Bind the model methods &amp; row layout view by annotation &amp; you are done with ListView &amp; GridView. No need to set Adapter. It is served for you by EasyCollectionView library :)

##Installation
Add repository to project's ```build.gradle```
```
repositories {
    jcenter()
    maven {
        url 'http://dl.bintray.com/mobisystech/maven'
    }
}
```
Add compile dependency to app ```build.gradle```
```
compile 'com.mobisys.android:easycollectionview:1.0'
```
##Usage

```
<com.mobisys.android.easycollectionview.EasyListView
        android:id="@+id/movieList"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:modelClass="com.mobisys.android.easycollectionviewsample.model.Movie"
        app:rowLayout="@layout/row_movie"/>
        
```
For your ```EasyListView``` or ```EasyGridView```, simply provide model class (modelClass) & row layout (rowLayout). Bind view id defined in row layout with your model class through ```@viewId``` annotation & you are done.

row_movie.xml
```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent" android:layout_height="match_parent"
    android:descendantFocusability="blocksDescendants"
    android:padding="10dp">
    <ImageView android:id="@+id/movie_poster"
        android:layout_width="60dp"
        android:layout_height="60dp"
        android:scaleType="centerCrop"/>
    <LinearLayout android:id="@+id/linearLayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_toRightOf="@id/movie_poster"
        android:layout_marginLeft="10dp"
        android:orientation="vertical" android:gravity="center_vertical">
        <TextView android:id="@+id/movie_title"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:textColor="@android:color/black"/>
        <TextView android:id="@+id/release_date"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:textColor="@android:color/black"/>
        <TextView android:id="@+id/adult"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="(A)"
            android:textColor="@android:color/black"/>
        <RatingBar android:id="@+id/movie_rate"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:numStars="5"
            style="?android:attr/ratingBarStyleSmall"/>
    </LinearLayout>
    <Button android:id="@+id/btnVoteCount"
        android:layout_width="100dp"
        android:layout_height="wrap_content"
        android:layout_alignParentRight="true"
        android:layout_centerVertical="true"
        android:text="Vote Count"/>
</RelativeLayout>

```

Movie model class method
```
@ViewId(id = R.id.movie_title)
public String getTitle() {
    return title;
}
    
```

Here ```movie_title TextView``` of row layout binds with ```getTitle()``` method of model class.

If you want your own operation to be done while displaying, then, You can also bind view of row layout through Java method `bindViewId()`.
```
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

```
Moreover, you can set click listeners for viewId of row layout through Java:
```
easyListView.setOnViewIdClickListener(R.id.btnVoteCount, new OnViewIdClickListener() {
    @Override
    public void onViewIdClickListener(View view, int position) {
        Movie movie = movieWrapper.getMovies().get(position);
        //Handle your click event
    }
});
```
