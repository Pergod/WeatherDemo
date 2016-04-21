package com.geekband.mywork7;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.geekband.mydatabase.MyDatabaseHelper;
import com.geekband.myprovider.URIList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Hyper on 2016/3/12.
 */
public class ResultShowActivity extends Activity implements View.OnClickListener{

    private static final  String COUNTRY_NAME="国家";
    private static final String LOCATION="位置";
    private static final String PM25="PM2.5";
    private static final String AIR="空气质量";
    private static final String CITY_NAME ="city";
    private static final String DATE="更新日期";
    private static final String NOWTEP="当前温度";
    private static final String LABEL="℃";
    private static final String WINDDIR="风向";
    private static final String COMF="舒适度";

    private String HttpURL="http://apis.baidu.com/heweather/weather/free";

    private TextView cityTextView;
    private TextView resultTextView;

    private Button refreshButton;
    private Button addButton;

    private String cityName;

    private String pm25;
    private String qlty;
    private String city;
    private String cnty;
    private String cityId;
    private String condTxt;
    private String update;
    private String tmp;
    private String dir;
    private String brf;
    private String comfTxt;
    private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_window);

        cityTextView= (TextView) findViewById(R.id.city_name_text_view);
        resultTextView = (TextView) findViewById(R.id.resut_window_text_view);

        refreshButton= (Button) findViewById(R.id.refresh_button);
        addButton= (Button) findViewById(R.id.add_button);

        dbHelper=new MyDatabaseHelper(this);

        refreshButton.setOnClickListener(this);
        addButton.setOnClickListener(this);

        Intent intent=getIntent();

        if (intent!=null){
            cityName=intent.getStringExtra("city");
            cityTextView.setText(String.valueOf(cityName));
            getResponseData(cityName);
        }

    }

    //百度ApiStore提供的Api
    public void getResponseData(final String cityName) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                Parameters para=new Parameters();
                para.put(CITY_NAME, cityName);

                ApiStoreSDK.execute(HttpURL, ApiStoreSDK.GET, para, new ApiCallBack() {

                    @Override
                    public void onSuccess(int status, String responseString) {
                        JSONParse(responseString);
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(int status, String responseString, Exception e) {
                        resultTextView.setText(String.valueOf(status));
                        Toast.makeText(ResultShowActivity.this, "获取失败,请重试!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }

    /**
     *  因为网络获取的JSON数据稍显庞大复杂，只解析部分重要的数据
     */

    public void JSONParse(String JSONData) {

        try {
            JSONObject jsonObject=new JSONObject(JSONData);
            JSONArray jsonArray=jsonObject.getJSONArray("HeWeather data service 3.0");
            JSONObject jsonObjectWeather=jsonArray.getJSONObject(0);

            if (jsonObjectWeather.has("aqi")){

                JSONObject jsonObjectAqi=jsonObjectWeather.getJSONObject("aqi");
                JSONObject jsonObjectCity=jsonObjectAqi.getJSONObject("city");

                pm25=(jsonObjectCity.getString("pm25"));
                qlty=(jsonObjectCity.getString("qlty"));

            }

            JSONObject jsonObjectBasic=jsonObjectWeather.getJSONObject("basic");

            city=jsonObjectBasic.getString("city");
            cnty=jsonObjectBasic.getString("cnty");
            cityId=jsonObjectBasic.getString("id");

            JSONObject jsonObjectUpdate=jsonObjectBasic.getJSONObject("update");

            update=jsonObjectUpdate.getString("loc");

            JSONObject jsonObjectNow=jsonObjectWeather.getJSONObject("now");
            tmp=jsonObjectNow.getString("tmp");

            JSONObject jsonObjectNowWeather=jsonObjectNow.getJSONObject("cond");
            condTxt=jsonObjectNowWeather.getString("txt");

            JSONObject jsonObjectWind=jsonObjectNow.getJSONObject("wind");

            dir=jsonObjectWind.getString("dir");

            JSONObject jsonObjectSuggestion=jsonObjectWeather.getJSONObject("suggestion");
            JSONObject jsonObjectComf=jsonObjectSuggestion.getJSONObject("comf");

            brf=jsonObjectComf.getString("brf");
            comfTxt=jsonObjectComf.getString("txt");

            if (PM25!=null&&qlty!=null) {

                resultTextView.setText(
                        COUNTRY_NAME + ":" + cnty + "\n" +
                                LOCATION + ":" + city + "\n" +
                                PM25 + ":" + pm25 + "\n" +
                                AIR + ":" + qlty + "\n" +
                                DATE + ":" + update + "\n" +
                                NOWTEP + ":" + tmp + LABEL + "  " + condTxt + "\n" +
                                WINDDIR + ":" + dir + "\n" +
                                COMF + ":" + brf + "  " + comfTxt);

            }else {

                resultTextView.setText(
                        COUNTRY_NAME + ":" + cnty + "\n" +
                                LOCATION + ":" + city + "\n" +
                                DATE + ":" + update + "\n" +
                                NOWTEP + ":" + tmp + LABEL + "  " + condTxt + "\n" +
                                WINDDIR + ":" + dir + "\n" +
                                COMF + ":" + brf + "  " + comfTxt);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.add_button:

                dbHelper.getWritableDatabase();

                ContentResolver contentResolver=getContentResolver();
                ContentValues contentValues=new ContentValues();

                contentValues.put("cityId",cityId);
                contentValues.put("cityName",city);
                contentValues.put("weather", condTxt);

                contentResolver.insert(Uri.parse(URIList.WEATHER_URI), contentValues);
                break;

            //刷新数据
            case R.id.refresh_button:
                cityTextView.setText(String.valueOf(cityName));
                getResponseData(cityName);
                break;
        }
    }
}
