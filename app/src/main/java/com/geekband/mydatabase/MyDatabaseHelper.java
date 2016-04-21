package com.geekband.mydatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Hyper on 2016/3/12.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="WeatherStore.db";
    private static final int VERSION=1;
    public static final String TABLE_NAME = "CityWeather";
    private static final String CREATE_WEATHER_TABLE= "create table " + TABLE_NAME + "(cityId varchar(10) primary key not null,cityName varchar(10) not null,weather varchar(10) not null)";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_WEATHER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
