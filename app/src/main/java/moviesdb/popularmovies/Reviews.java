package moviesdb.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.borjabravo.readmoretextview.ReadMoreTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import moviesdb.popularmovies.databinding.ActivityReviewBinding;
import moviesdb.popularmovies.utilities.NetworkUtils;

/**
 * Created by amokk on 6/12/2017.
 */

public class Reviews extends AppCompatActivity {
    SharedPreferences preferences;
    LinearLayout Llayout;
    ActivityReviewBinding reviewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        reviewBinding = DataBindingUtil.setContentView(this, R.layout.activity_review);

        //   Llayout = (LinearLayout) findViewById(R.id.layout);
        preferences = getSharedPreferences("pref", MODE_PRIVATE);
        String id = preferences.getString("movie_id", null);

        String mode = id + "/reviews";
        /* enter your own key */
        String key = "be642de20ece0687bafdafe62fdfc383";
        new MovieTask().execute(mode, key);
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
                jsonParse(Reviews.this, strings);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void jsonParse(Context context, String jsonresponse) throws JSONException {
        JSONObject moviedata = new JSONObject(jsonresponse);
        JSONArray data_array = moviedata.getJSONArray("results");
        if (data_array.length() == 0) {
            TextView tv = new TextView(this);
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            tv.setTextColor(Color.WHITE);
            Llayout.addView(tv);
            tv.setText("No Reviews Yet");
        } else {
            for (int i = 0; i < data_array.length(); i++) {
                ReadMoreTextView tv = new ReadMoreTextView(this);
                tv.setTextColor(Color.WHITE);
                tv.setTrimCollapsedText("show more");
                tv.setTrimExpandedText("show less");
                tv.setTrimLines(8);
                tv.setColorClickableText(Color.MAGENTA);
                tv.setBackground(getResources().getDrawable(R.drawable.black));
                tv.setPadding(0, 0, 0, 5);
                reviewBinding.layout.addView(tv);
                JSONObject moviedatainfo = data_array.getJSONObject(i);
                String Title = "<font color='#FF4081'>Author : </font>";
                String author = "<font color='#ffffff'>" + moviedatainfo.getString("author") + "</font>";
                String content = "<font color='#ffffff'>" + moviedatainfo.getString("content") + "</font>";
                tv.setText(Html.fromHtml(Title + author + "<br>" + content + "<br><br>"));
            }
        }
    }

}
