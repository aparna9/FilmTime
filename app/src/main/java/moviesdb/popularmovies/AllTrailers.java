package moviesdb.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import moviesdb.popularmovies.utilities.NetworkUtils;

/**
 * Created by amokk on 6/25/2017.
 */

public class AllTrailers extends AppCompatActivity {

    String[] videoids, names, type;
    SharedPreferences preferences;
    String title, id;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recycler_main);
        preferences = getSharedPreferences("pref", MODE_PRIVATE);
        id = preferences.getString("movie_id", null);
        title = preferences.getString("title", null);
        String mode = id + "/videos";
        /* enter your own key */
        String key = "be642de20ece0687bafdafe62fdfc383";
        new MovieTask().execute(mode, key);

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
                jsonParse(AllTrailers.this, strings);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void jsonParse(Context context, String jsonresponse) throws JSONException {
        JSONObject moviedata = new JSONObject(jsonresponse);
        JSONArray data_array = moviedata.getJSONArray("results");
        videoids = new String[data_array.length()];
        names = new String[data_array.length()];
        type = new String[data_array.length()];
        for (int i = 0; i < data_array.length(); i++) {
            JSONObject moviedatainfo = data_array.getJSONObject(i);
            videoids[i] = moviedatainfo.getString("key");
            names[i] = moviedatainfo.getString("name");
            type[i] = moviedatainfo.getString("type");
        }

        myValues(names, videoids, type);
    }

    public void myValues(String[] names, String[] videoids, String[] type) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        TrailerAdapter adapter = new TrailerAdapter(AllTrailers.this, names, videoids, type, title);
        recyclerView.setAdapter(adapter);
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