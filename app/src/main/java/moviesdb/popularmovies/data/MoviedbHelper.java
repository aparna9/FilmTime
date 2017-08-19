package moviesdb.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by amokk on 6/14/2017.
 */

public class MoviedbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movies.db";
    public static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " (" +
                    MovieContract.MovieEntry._ID + " TEXT PRIMARY KEY," +
                    MovieContract.MovieEntry.Favorite + " INTEGER DEFAULT 0)";

    private static final String SQL_DELETE_ENTRY =
            "DELETE FROM " + MovieContract.MovieEntry.TABLE_NAME + " where " +
                    MovieContract.MovieEntry._ID + "=" + MovieContract.MovieEntry.MOVIE_ID + ";";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public MoviedbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
