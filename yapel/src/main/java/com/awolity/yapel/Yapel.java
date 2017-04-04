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

package com.awolity.yapel;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Map;
import java.util.Set;

import javax.crypto.SecretKey;

public abstract class Yapel {

    YapelKey mYapelKey;
    SharedPreferences mSp;
    SecretKey mKey;

     Yapel(String keyAlias, Context context) throws YapelException {
        try {
            mYapelKey = new YapelKey(keyAlias);
            mKey = mYapelKey.getKey();
        } catch (YapelKeyException e) {
            throw new YapelException(e);
        }
        mSp = PreferenceManager.getDefaultSharedPreferences(context);
    }

     Yapel(String keyAlias, Context context, String prefFileName) throws YapelException {
        try {
            mYapelKey = new YapelKey(keyAlias);
            mKey = mYapelKey.getKey();
        } catch (YapelKeyException e) {
            throw new YapelException(e);
        }
        mSp = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    abstract String getString(String prefKey, String defaultValue) throws YapelException;

    abstract void setString(String prefKey, String prefVal) throws YapelException;

    abstract boolean getBoolean(String prefKey, boolean defaultValue) throws YapelException;

    abstract void setBoolean(String prefKey, boolean prefVal) throws YapelException;

    abstract float getFloat(String prefKey, float defaultValue) throws YapelException;

    abstract void setFloat(String prefKey, float prefVal) throws YapelException;

    abstract long getLong(String prefKey, long defaultValue) throws YapelException;

    abstract void setLong(String prefKey, long prefVal) throws YapelException;

    abstract int getInt(String prefKey, int defaultValue) throws YapelException;

    abstract void setInt(String prefKey, int prefVal) throws YapelException;

    abstract void setStringSet(String prefKey, Set<String> prefVal) throws YapelException;

    abstract Set<String> getStringSet(String prefKey, Set<String> defaultValue) throws YapelException;

    void writeString(String prefKey, String prefVal) {
        SharedPreferences.Editor editor = mSp.edit();
        editor.putString(prefKey, prefVal);
        editor.apply();
    }

    String readString(String prefKey) {
        return mSp.getString(prefKey, null);
    }

    void writeStringSet(String prefKey, Set<String> prefVals) {
        SharedPreferences.Editor editor = mSp.edit();
        editor.putStringSet(prefKey, prefVals);
        editor.apply();
    }

    Set<String> readStringSet(String prefKey) {
        return mSp.getStringSet(prefKey, null);
    }

    Map<String, ?> getAll() {
        return mSp.getAll();
    }

}
