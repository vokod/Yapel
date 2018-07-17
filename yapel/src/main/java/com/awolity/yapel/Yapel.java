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

import java.util.Set;

import javax.crypto.SecretKey;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Yapel {

    private YapelKey yapelKey;
    private SharedPreferences sharedPreferences;
    private SecretKey secretKey;

     public Yapel(String keyAlias, Context context) throws YapelException {
        try {
            yapelKey = new YapelKey(keyAlias);
            secretKey = yapelKey.getKey();
        } catch (YapelKeyException e) {
            throw new YapelException(e);
        }
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public Yapel(String keyAlias, Context context, String prefFileName) throws YapelException {
        try {
            yapelKey = new YapelKey(keyAlias);
            secretKey = yapelKey.getKey();
        } catch (YapelKeyException e) {
            throw new YapelException(e);
        }
        sharedPreferences = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    public void clear (){
        sharedPreferences.edit().clear().apply();
    }

    public boolean contains (String key){
        return sharedPreferences.contains(key);
    }

    public void remove (String key){
        sharedPreferences.edit().remove(key).apply();
    }

    public String getString(String prefKey, @SuppressWarnings("SameParameterValue") String defaultValue) throws YapelException {
        String encryptedPrefVal = readString(prefKey);
        if (encryptedPrefVal == null) {
            return defaultValue;
        } else {
            return CryptUtils.decryptString(encryptedPrefVal, secretKey);
        }
    }

    public void setString(String prefKey, String prefVal) throws YapelException {
        String encryptedPrefVal = CryptUtils.encryptString(prefVal, secretKey);
        writeString(prefKey, encryptedPrefVal);
    }

    public boolean getBoolean(String prefKey, boolean defaultValue) throws YapelException {
        String encryptedBooleanValue = readString(prefKey);
        if (encryptedBooleanValue == null) {
            return defaultValue;
        } else {
            return CryptUtils.decryptBoolean(encryptedBooleanValue, secretKey);
        }
    }

    public void setBoolean(String prefKey, boolean prefVal) throws YapelException {
        String encryptedPrefVal = CryptUtils.encryptBoolean(prefVal, secretKey);
        writeString(prefKey, encryptedPrefVal);
    }

    public float getFloat(String prefKey, @SuppressWarnings("SameParameterValue") float defaultValue) throws YapelException {
        String encryptedFloatValueAsString = readString(prefKey);
        if (encryptedFloatValueAsString == null) {
            return defaultValue;
        } else {
            return CryptUtils.decryptFloat(encryptedFloatValueAsString, secretKey);
        }
    }

    public void setFloat(String prefKey, float prefVal) throws YapelException {
        String encryptedPrefVal = CryptUtils.encryptFloat(prefVal, secretKey);
        writeString(prefKey, encryptedPrefVal);
    }

    public long getLong(String prefKey, @SuppressWarnings("SameParameterValue") long defaultValue) throws YapelException {
        String longAsString = readString(prefKey);
        if (longAsString == null) {
            return defaultValue;
        } else {
            return CryptUtils.decryptLong(longAsString, secretKey);
        }
    }

    public void setLong(String prefKey, long prefVal) throws YapelException {
        String encryptedPrefVal = CryptUtils.encryptLong(prefVal, secretKey);
        writeString(prefKey, encryptedPrefVal);
    }

    public int getInt(String prefKey, @SuppressWarnings("SameParameterValue") int defaultValue) throws YapelException {
        String intAsString = readString(prefKey);
        if (intAsString == null) {
            return defaultValue;
        } else {
            return CryptUtils.decryptInt(intAsString, secretKey);
        }
    }

    public void setInt(String prefKey, int prefVal) throws YapelException {
        String encryptedPrefVal = CryptUtils.encryptInt(prefVal, secretKey);
        writeString(prefKey, encryptedPrefVal);
    }

    public void setStringSet(String prefKey, Set<String> prefVal) throws YapelException {
        Set<String> encryptedPrefVal = CryptUtils.encryptStringSet(prefVal, secretKey);
        writeStringSet(prefKey, encryptedPrefVal);
    }

    public Set<String> getStringSet(String prefKey, @SuppressWarnings("SameParameterValue") Set<String> defaultValue) throws YapelException {
        Set<String> encryptedPrefVal = readStringSet(prefKey);
        if (encryptedPrefVal == null) {
            return defaultValue;
        } else {
            return CryptUtils.decryptStringSet(encryptedPrefVal, secretKey);
        }
    }

    private void writeString(String prefKey, String prefVal) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(prefKey, prefVal);
        editor.apply();
    }

    private String readString(String prefKey) {
        return sharedPreferences.getString(prefKey, null);
    }

    private void writeStringSet(String prefKey, Set<String> prefVals) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(prefKey, prefVals);
        editor.apply();
    }

    private Set<String> readStringSet(String prefKey) {
        return sharedPreferences.getStringSet(prefKey, null);
    }

}
