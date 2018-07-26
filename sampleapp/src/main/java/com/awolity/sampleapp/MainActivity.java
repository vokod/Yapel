package com.awolity.sampleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.awolity.yapel.Yapel;
import com.awolity.yapel.YapelException;

import static android.view.View.Y;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String key_alias = "my_key_alias";
        String label1 = "label1";
        String label2 = "label2";

        try {
            Yapel yapel = new Yapel(key_alias, this.getApplicationContext());
            yapel.setString(label1, "some string");
            yapel.setLong(label2, 1234);
        } catch (YapelException e) {
            e.printStackTrace();
        }

        try {
            Yapel yapel = new Yapel(key_alias, this.getApplicationContext());
            String readString = yapel.getString(label1, "no string");
            Log.d(TAG, "string: "+ readString);
            long readLong = yapel.getLong(label2, 0);
            Log.d(TAG, "long: "+ readLong);
        } catch (YapelException e) {
            e.printStackTrace();
        }
    }
}
