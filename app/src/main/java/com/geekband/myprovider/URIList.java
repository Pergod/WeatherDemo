package com.geekband.myprovider;

import com.geekband.mydatabase.MyDatabaseHelper;

/**
 * Created by Hyper on 2016/3/13.
 */
public class URIList {

    public static final String CONTENT="content://";

    public static final String AUTHORITY="com.geekband.myprovider";

    public static final String WEATHER_URI =CONTENT+AUTHORITY+"/"+ MyDatabaseHelper.TABLE_NAME;

}