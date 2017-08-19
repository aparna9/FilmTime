package moviesdb.popularmovies.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import moviesdb.popularmovies.MainActivity;

/**
 * Created by amokk on 4/7/2017.
 */

public class NetworkUtils {


    public static URL buildUrl(String mode, String api_key) {
        String BASE_URL = "https://api.themoviedb.org/3/movie/" + mode + "?api_key=" + api_key;
        Uri uri = Uri.parse(BASE_URL).buildUpon().build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            urlConnection.setConnectTimeout(60000);
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
