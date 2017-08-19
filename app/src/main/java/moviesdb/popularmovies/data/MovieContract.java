package moviesdb.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by amokk on 6/14/2017.
 */

public final class MovieContract {
    private MovieContract() {
    }

    public static class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "fav_movie";
        public static final String _ID = "_id";
        public static final String Favorite = "fav";
        public static String MOVIE_ID = "";

        public static final String CONTENT_AUTHORITY = "moviesdb.popularmovies";
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        public static final String PATH_FAV = "fav";
        public static final Uri COMPLETE_CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_FAV);
    }

}
