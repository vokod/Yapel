/*
 * Copyright 2017, Dániel Vokó
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.awolity.yapelexample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.awolity.yapel.EncryptedPrefKeyYapel;
import com.awolity.yapel.Yapel;
import com.awolity.yapel.YapelException;

public class MainActivity2 extends AppCompatActivity /*implements AdapterView.OnItemSelectedListener*/ {

    private Yapel mYapel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //spinner.setOnItemSelectedListener(this);

        try {
            mYapel = new EncryptedPrefKeyYapel("my_awesome_key_xx", this);
        } catch (YapelException e) {
            e.printStackTrace();
        }
        Button button = (Button) findViewById(R.id.button);
        EditText preferenceKeyEt = (EditText) findViewById(R.id.et_pref_key);
        EditText preferenceValueEt = (EditText) findViewById(R.id.et_pref_value);
        EditText savedPreferenceKeyEt = (EditText) findViewById(R.id.et_saved_pref_key);
        EditText savedPreferenceValueEt = (EditText) findViewById(R.id.et_saved_pref_value);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = spinner.getSelectedItem().toString();
                switch (item){
                    case "String":
                        Log.d("Yapel", "String selected");

                        break;
                    case "Boolean":
                        Log.d("Yapel", "Boolean selected");
                        break;
                    case "Int":
                        Log.d("Yapel", "Int selected");
                        break;
                    case "Long":
                        Log.d("Yapel", "Long selected");
                        break;
                    case "Float":
                        Log.d("Yapel", "Float selected");
                        break;
                    case "String Set":
                        Log.d("Yapel", "String Set selected");
                        break;
                }
            }
        });


    }

   /* @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
        switch (item){
            case "String":
                Log.d("Yapel", "String selected");
                break;
            case "Boolean":
                Log.d("Yapel", "Boolean selected");
                break;
            case "Int":
                Log.d("Yapel", "Int selected");
                break;
            case "Long":
                Log.d("Yapel", "Long selected");
                break;
            case "Float":
                Log.d("Yapel", "Float selected");
                break;
            case "String Set":
                Log.d("Yapel", "String Set selected");
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }*/
}
