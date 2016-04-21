package com.geekband.myprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.geekband.mydatabase.MyDatabaseHelper;

/**
 * Created by Hyper on 2016/3/13.
 */
public class MyProvider extends ContentProvider {
    private MyDatabaseHelper dbHelper;
    private static UriMatcher uriMatcher;

    public static final int URI_MATCH_WEATHER=1;

    static {
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(URIList.AUTHORITY, MyDatabaseHelper.TABLE_NAME,URI_MATCH_WEATHER);
    }
    @Override
    public boolean onCreate() {
        dbHelper=new MyDatabaseHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor=dbHelper.getReadableDatabase()
                .query(MyDatabaseHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        Long id=dbHelper.getWritableDatabase().insert(MyDatabaseHelper.TABLE_NAME,null,values);

        return ContentUris.withAppendedId(uri,id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        return dbHelper.getWritableDatabase().delete(MyDatabaseHelper.TABLE_NAME,selection,selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        return dbHelper.getWritableDatabase().update(MyDatabaseHelper.TABLE_NAME,values,selection,selectionArgs);
    }
}
