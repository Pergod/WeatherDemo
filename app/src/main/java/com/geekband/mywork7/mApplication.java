package com.geekband.mywork7;

import android.app.Application;

import com.baidu.apistore.sdk.ApiStoreSDK;

/**
 * Created by Hyper on 2016/3/10.
 */
public class mApplication extends Application {

    @Override
    public void onCreate() {
        ApiStoreSDK.init(this,"feb3c53abc6ace62005497a5a54f03fc");

        super.onCreate();

    }

}
