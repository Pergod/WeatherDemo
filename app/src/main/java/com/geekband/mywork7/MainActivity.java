package com.geekband.mywork7;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.geekband.mydatabase.MyDatabaseHelper;

/**
 * Created by Hyper on 2016/3/10.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button searchButton;
    private EditText mEditText;

    private String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchButton = (Button) findViewById(R.id.search_button);
        mEditText= (EditText) findViewById(R.id.input_edit_text);

        searchButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_button:

                cityName= mEditText.getText().toString();
                Intent intent=new Intent(MainActivity.this,ResultShowActivity.class);
                intent.putExtra("city", cityName);
                startActivity(intent);
                break;

        }
    }

}
