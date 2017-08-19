package moviesdb.popularmovies;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.PaintDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.text.method.ScrollingMovementMethod;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.squareup.picasso.Picasso;

import android.support.v4.app.NavUtils;
import android.os.Build;
import android.widget.Toast;

import moviesdb.popularmovies.data.MovieContract;
import moviesdb.popularmovies.data.MoviedbHelper;
import moviesdb.popularmovies.databinding.ActivityDetailsBinding;
import moviesdb.popularmovies.databinding.ActivityMainBinding;

/**
 * Created by amokk on 4/12/2017.
 */

public class DetailsActivity extends AppCompatActivity {

    String title, overview, releasedate, voteaverage, image, id;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    boolean click;
    ActivityDetailsBinding detailsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        detailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        click = true;
        LayerDrawable stars = (LayerDrawable) detailsBinding.ratingbar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        detailsBinding.release.setTextColor(Color.WHITE);
        detailsBinding.gridtitle.setTextColor(Color.WHITE);
        detailsBinding.gridoverview.setTextColor(Color.WHITE);
        detailsBinding.gridreleasedate.setTextColor(Color.WHITE);
        detailsBinding.ratingvalue.setTextColor(Color.WHITE);
        detailsBinding.fav.setTextColor(Color.WHITE);
        detailsBinding.favbutton.setImageAlpha(127);
        detailsBinding.ratingbar.setNumStars(5);
        detailsBinding.ratingbar.setMax(10);
        values();
        querydatabase();


        detailsBinding.review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailsActivity.this, Reviews.class);
                startActivity(i);
            }
        });

        detailsBinding.favbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (click) {
                    detailsBinding.favbutton.setImageAlpha(127);
                    detailsBinding.favbutton.setBackground(null);
                    detailsBinding.favbutton.setColorFilter(Color.GREEN);
                    InsertfavMovie();
                    click = false;
                } else {
                    detailsBinding.favbutton.setImageAlpha(127);
                    detailsBinding.favbutton.setBackground(null);
                    detailsBinding.favbutton.setColorFilter(Color.WHITE);
                    DeletefavMovie();
                    click = true;
                }

            }
        });

        detailsBinding.trail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailsActivity.this, AllTrailers.class);
                startActivity(i);
            }
        });
    }

    public void values() {

        image = getIntent().getStringExtra("image");
        title = getIntent().getStringExtra("title");
        overview = getIntent().getStringExtra("overview");
        releasedate = getIntent().getStringExtra("releasedate");
        voteaverage = getIntent().getStringExtra("voteaverage");
        id = getIntent().getStringExtra("movieid");
        preferences = getSharedPreferences("pref", MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString("movie_id", id);
        editor.putString("title", title);
        editor.putString("info", overview);
        editor.commit();
        Picasso.with(this).load(image).into(detailsBinding.gridimage);
        Float fvalue = Float.parseFloat(voteaverage);
        detailsBinding.review.setTextColor(Color.WHITE);
        detailsBinding.gridtitle.setText(title);
        detailsBinding.gridoverview.setText(overview);
        detailsBinding.gridreleasedate.setText(releasedate);
        detailsBinding.ratingbar.setRating(fvalue / 2);
        detailsBinding.ratingvalue.setText(voteaverage + "/10");

    }

    private void querydatabase() {
        String[] projection = {
                MovieContract.MovieEntry._ID,
                MovieContract.MovieEntry.Favorite
        };
        Cursor cursor = getContentResolver().query(MovieContract.MovieEntry.BASE_CONTENT_URI, projection, null, null, null);
        try {
            int IdColumn = cursor.getColumnIndex(MovieContract.MovieEntry._ID);
            while (cursor.moveToNext()) {
                int movieid = cursor.getInt(IdColumn);
                if (Integer.parseInt(id) == movieid) {
                    detailsBinding.favbutton.setImageAlpha(127);
                    detailsBinding.favbutton.setBackground(null);
                    detailsBinding.favbutton.setColorFilter(Color.GREEN);
                    click = false;
                }
            }
        } finally {
            cursor.close();
        }
    }

    public void InsertfavMovie() {
        MoviedbHelper mDbHelper = new MoviedbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry._ID, id);
        values.put(MovieContract.MovieEntry.Favorite, 1);
        Uri newRowId = getContentResolver().insert(MovieContract.MovieEntry.BASE_CONTENT_URI, values);
        if (newRowId == null) {
            Toast.makeText(this, "Error with saving the fav", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Favorite saved", Toast.LENGTH_SHORT).show();
            detailsBinding.favbutton.setImageAlpha(127);
            detailsBinding.favbutton.setBackground(null);
            detailsBinding.favbutton.setColorFilter(Color.GREEN);

        }
    }

    public void DeletefavMovie() {
        MoviedbHelper mDbHelper = new MoviedbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        MovieContract.MovieEntry.MOVIE_ID = id;
        int newRow = getContentResolver().delete(MovieContract.MovieEntry.BASE_CONTENT_URI, "_id=" + id, null);
        if (newRow == 0) {
            Toast.makeText(this, "Removed from favorite", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error while removing", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
