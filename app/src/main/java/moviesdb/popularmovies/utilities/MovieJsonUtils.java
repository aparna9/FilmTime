package moviesdb.popularmovies.utilities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import moviesdb.popularmovies.MainActivity;

/**
 * Created by amokk on 4/8/2017.
 */

public class MovieJsonUtils {

    public void jsonParse(Context context, String jsonresponse) throws JSONException {

        JSONObject moviedata = new JSONObject(jsonresponse);
        JSONArray data_array = moviedata.getJSONArray("results");
        GridItem item;

        for (int i = 0; i < data_array.length(); i++) {
            JSONObject moviedatainfo = data_array.getJSONObject(i);
            String movietitle = moviedatainfo.getString("original_title");
            String poster = moviedatainfo.getString("poster_path");
            item = new GridItem();
            item.setImage(poster);
            item.setTitle(poster);
        }

    }
}
