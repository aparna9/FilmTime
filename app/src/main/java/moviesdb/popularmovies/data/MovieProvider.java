package moviesdb.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by amokk on 6/14/2017.
 */

public class MovieProvider extends ContentProvider {

    private MoviedbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new MoviedbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = db.query(MovieContract.MovieEntry.TABLE_NAME, projection, null, null, null, null, null);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long RowId = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, values);
        return ContentUris.withAppendedId(uri, RowId);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(MovieContract.MovieEntry.TABLE_NAME, s, strings);
        return 0;
    }
}
