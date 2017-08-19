package moviesdb.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.*;
import android.widget.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.job.*;

import java.io.IOException;
import java.util.ArrayList;

import moviesdb.popularmovies.data.MovieContract;
import moviesdb.popularmovies.databinding.ActivityMainBinding;
import moviesdb.popularmovies.utilities.GridItem;
import moviesdb.popularmovies.utilities.GridViewAdapter;
import moviesdb.popularmovies.utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity {
    LinearLayout.LayoutParams params;
    GridViewAdapter gridViewAdapter;
    ArrayList<GridItem> mGridData;
    ArrayList<String> resdata;
    String mode, key, tabid;
    TabHost host;

    ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        mainBinding.progressBar.setVisibility(View.VISIBLE);
        mainBinding.progressBar1.setVisibility(View.VISIBLE);
        mainBinding.progressBar2.setVisibility(View.VISIBLE);
        host = (TabHost) findViewById(R.id.tabhost);
        host.setup();


        //Popularity
        TabHost.TabSpec spec = host.newTabSpec("Popularity");
        spec.setContent(R.id.main1);
        spec.setIndicator("Popularity");
        host.addTab(spec);

        //Rating
        spec = host.newTabSpec("Rating");
        spec.setContent(R.id.main);
        spec.setIndicator("Rating");
        host.addTab(spec);


        //Favorite
        spec = host.newTabSpec("Favorite");
        spec.setContent(R.id.main2);
        spec.setIndicator("Favorite");
        host.addTab(spec);

        for (int i = 0; i < host.getTabWidget().getChildCount(); i++) {
            TextView tv = (TextView) host.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.WHITE);
        }
        Integer lastTab = (Integer) getLastCustomNonConfigurationInstance();
        if (lastTab == null) {
            host.setCurrentTab(0);
            mode = "popular";
               /* enter your own key */
            key = "be642de20ece0687bafdafe62fdfc383";
            new MovieTask().execute(mode, key);
        } else {
            host.setCurrentTab(lastTab);
            switch (lastTab) {
                case 0:
                    mode = "popular";
                       /* enter your own key */
                    key = "be642de20ece0687bafdafe62fdfc383";
                    new MovieTask().execute(mode, key);
                    break;
                case 1:
                    mode = "top_rated";
                       /* enter your own key */
                    key = "be642de20ece0687bafdafe62fdfc383";
                    new MovieTask().execute(mode, key);
                    break;
                case 2:
                    mode = "popular";
                       /* enter your own key */
                    key = "be642de20ece0687bafdafe62fdfc383";
                    new MovieTask().execute(mode, key);
                    mode = "top_rated";
                       /* enter your own key */
                    key = "be642de20ece0687bafdafe62fdfc383";
                    new MovieTask().execute(mode, key);
                    break;
            }
        }
        //  gridView = (GridView) findViewById(R.id.gridView);
        mainBinding.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                GridItem item = (GridItem) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                ImageView imageView = (ImageView) v.findViewById(R.id.grid_item_image);
                int[] screenLocation = new int[2];
                imageView.getLocationOnScreen(screenLocation);
                intent.putExtra("width", imageView.getWidth()).
                        putExtra("height", imageView.getHeight()).
                        putExtra("title", item.getTitle()).
                        putExtra("image", item.getImage()).
                        putExtra("overview", item.getOverview()).
                        putExtra("releasedate", item.getReleasedate()).
                        putExtra("voteaverage", item.getVoteaverage()).
                        putExtra("movieid", item.getMovie_id());

                startActivity(intent);
            }
        });
        //  gridView1 = (GridView) findViewById(R.id.gridView1);
        mainBinding.gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                GridItem item = (GridItem) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                ImageView imageView = (ImageView) v.findViewById(R.id.grid_item_image);
                int[] screenLocation = new int[2];
                imageView.getLocationOnScreen(screenLocation);
                intent.putExtra("width", imageView.getWidth()).
                        putExtra("height", imageView.getHeight()).
                        putExtra("title", item.getTitle()).
                        putExtra("image", item.getImage()).
                        putExtra("overview", item.getOverview()).
                        putExtra("releasedate", item.getReleasedate()).
                        putExtra("voteaverage", item.getVoteaverage()).
                        putExtra("movieid", item.getMovie_id());

                startActivity(intent);
            }
        });
        //  gridView2 = (GridView) findViewById(R.id.gridView2);
        mainBinding.gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                GridItem item = (GridItem) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                ImageView imageView = (ImageView) v.findViewById(R.id.grid_item_image);
                int[] screenLocation = new int[2];
                imageView.getLocationOnScreen(screenLocation);
                intent.putExtra("width", imageView.getWidth()).
                        putExtra("height", imageView.getHeight()).
                        putExtra("title", item.getTitle()).
                        putExtra("image", item.getImage()).
                        putExtra("overview", item.getOverview()).
                        putExtra("releasedate", item.getReleasedate()).
                        putExtra("voteaverage", item.getVoteaverage()).
                        putExtra("movieid", item.getMovie_id());

                startActivity(intent);
            }
        });
        mGridData = new ArrayList<>();
        resdata = new ArrayList<>();
        gridViewAdapter = new GridViewAdapter(this, R.layout.grid_item, mGridData);
        mainBinding.gridView.setAdapter(gridViewAdapter);
        mainBinding.gridView1.setAdapter(gridViewAdapter);
        mainBinding.gridView2.setAdapter(gridViewAdapter);
        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                tabid = host.getCurrentTabTag();
                if (tabid == "Popularity") {
                    mGridData.clear();
                    mode = "popular";
                       /* enter your own key */
                    key = "be642de20ece0687bafdafe62fdfc383";
                    new MovieTask().execute(mode, key);
                }
                if (tabid == "Rating") {
                    mGridData.clear();
                    mode = "top_rated";
                       /* enter your own key */
                    key = "be642de20ece0687bafdafe62fdfc383";
                    new MovieTask().execute(mode, key);
                }
                if (tabid == "Favorite") {
                    mGridData.clear();
                    mode = "popular";
                       /* enter your own key */
                    key = "be642de20ece0687bafdafe62fdfc383";
                    new MovieTask().execute(mode, key);
                    mode = "top_rated";
                       /* enter your own key */
                    key = "be642de20ece0687bafdafe62fdfc383";
                    new MovieTask().execute(mode, key);
                }
            }
        });

    }

    public class MovieTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String mode_url = strings[0];
            String key_url = strings[1];
            try {
                String Response = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildUrl(mode_url, key_url));
                return Response;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String strings) {
            try {
                String tabid = host.getCurrentTabTag();
                if (tabid == "Popularity") {
                    jsonParse(MainActivity.this, strings);
                    mainBinding.progressBar.setVisibility(View.INVISIBLE);
                } else if (tabid == "Rating") {
                    jsonParse(MainActivity.this, strings);
                    mainBinding.progressBar1.setVisibility(View.INVISIBLE);
                } else {
                    jsonParse1(MainActivity.this, strings);
                    mainBinding.progressBar2.setVisibility(View.INVISIBLE);
                }

                gridViewAdapter.setGridData(mGridData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void jsonParse(Context context, String jsonresponse) throws JSONException {
        JSONObject moviedata = new JSONObject(jsonresponse);
        JSONArray data_array = moviedata.getJSONArray("results");
        GridItem item = null;

        for (int i = 0; i < data_array.length(); i++) {
            JSONObject moviedatainfo = data_array.getJSONObject(i);
            String movietitle = moviedatainfo.getString("original_title");
            String poster = moviedatainfo.getString("poster_path");
            String movie_overview = moviedatainfo.getString("overview");
            String releasedate = moviedatainfo.getString("release_date");
            String voteavg = moviedatainfo.getString("vote_average");
            String movieid = moviedatainfo.getString("id");
            item = new GridItem();
            item.setImage("http://image.tmdb.org/t/p/w342/" + poster);
            item.setTitle(movietitle);
            item.setOverview(movie_overview);
            item.setReleasedate(releasedate);
            item.setVoteaverage(voteavg);
            item.setMovie_id(movieid);
            mGridData.add(item);
        }

    }

    public void jsonParse1(Context context, String jsonresponse) throws JSONException {
        querydatabase();
        JSONObject moviedata = new JSONObject(jsonresponse);
        JSONArray data_array = moviedata.getJSONArray("results");
        GridItem item = null;

        for (int i = 0; i < data_array.length(); i++) {
            JSONObject moviedatainfo = data_array.getJSONObject(i);
            String movietitle = moviedatainfo.getString("original_title");
            String poster = moviedatainfo.getString("poster_path");
            String movie_overview = moviedatainfo.getString("overview");
            String releasedate = moviedatainfo.getString("release_date");
            String voteavg = moviedatainfo.getString("vote_average");
            String movieid = moviedatainfo.getString("id");
            item = new GridItem();
            for (int j = 0; j < resdata.size(); j++) {

                if (movieid.equals(resdata.get(j))) {
                    item.setImage("http://image.tmdb.org/t/p/w342/" + poster);
                    item.setTitle(movietitle);
                    item.setOverview(movie_overview);
                    item.setReleasedate(releasedate);
                    item.setVoteaverage(voteavg);
                    item.setMovie_id(movieid);
                    mGridData.add(item);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemThatWasSelected = item.getItemId();
        if (menuItemThatWasSelected == R.id.action_refresh) {
            tabid = host.getCurrentTabTag();
            if (tabid == "Popularity") {
                mGridData.clear();
                mode = "popular";
                /* enter your own key */
                key = "be642de20ece0687bafdafe62fdfc383";
                new MovieTask().execute(mode, key);
            }
            if (tabid == "Rating") {
                mGridData.clear();
                mode = "top_rated";
                /* enter your own key */
                key = "be642de20ece0687bafdafe62fdfc383";
                new MovieTask().execute(mode, key);
            }
            if (tabid == "Favorite") {
                mGridData.clear();
                mode = "popular";
                /* enter your own key */
                key = "be642de20ece0687bafdafe62fdfc383";
                new MovieTask().execute(mode, key);
                mode = "top_rated";
                /* enter your own key */
                key = "be642de20ece0687bafdafe62fdfc383";
                new MovieTask().execute(mode, key);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void querydatabase() {
        String[] projection = {
                MovieContract.MovieEntry._ID,
                MovieContract.MovieEntry.Favorite
        };
        Cursor cursor = getContentResolver().query(MovieContract.MovieEntry.BASE_CONTENT_URI, projection, null, null, null);
        try {
            int IdColumn = cursor.getColumnIndex(MovieContract.MovieEntry._ID);
            resdata.clear();
            while (cursor.moveToNext()) {
                String movieid = cursor.getString(IdColumn);
                resdata.add(movieid);
            }
        } finally {
            cursor.close();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        String tabid = host.getCurrentTabTag();
        if (tabid == "Popularity") {
            mGridData.clear();
            mode = "popular";
            /* enter your own key */
            key = "be642de20ece0687bafdafe62fdfc383";
            new MovieTask().execute(mode, key);
        }
        if (tabid == "Rating") {
            mGridData.clear();
            mode = "top_rated";
            /* enter your own key */
            key = "be642de20ece0687bafdafe62fdfc383";
            new MovieTask().execute(mode, key);
        }
        if (tabid == "Favorite") {
            mGridData.clear();
            mode = "popular";
            /* enter your own key */
            key = "be642de20ece0687bafdafe62fdfc383";
            new MovieTask().execute(mode, key);
            mode = "top_rated";
            /* enter your own key */
            key = "be642de20ece0687bafdafe62fdfc383";
            new MovieTask().execute(mode, key);
        }
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return host.getCurrentTab();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}

