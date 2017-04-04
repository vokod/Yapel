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
import android.preference.PreferenceManager;

import java.util.Set;

public class PlainPrefKeyYapel extends Yapel {

    public PlainPrefKeyYapel(String keyAlias, Context context) throws YapelException {
        super(keyAlias, context);
    }

    public PlainPrefKeyYapel(String keyAlias, Context context, String prefFileName) throws YapelException {
        super(keyAlias, context, prefFileName);
    }

    @Override
    String getString(String prefKey, String defaultValue) throws YapelException {
        String encryptedPrefVal = readString(prefKey);
        if (encryptedPrefVal == null) {
            return defaultValue;
        } else {
            return CryptUtils.decryptString(encryptedPrefVal, mKey);
        }
    }

    @Override
    void setString(String prefKey, String prefVal) throws YapelException {
        String encryptedPrefVal = CryptUtils.encryptString(prefVal, mKey);
        writeString(prefKey, encryptedPrefVal);
    }

    @Override
    boolean getBoolean(String prefKey, boolean defaultValue) throws YapelException {
        String encryptedBooleanValue = readString(prefKey);
        if (encryptedBooleanValue == null) {
            return defaultValue;
        } else {
            return CryptUtils.decryptBoolean(encryptedBooleanValue, mKey);
        }
    }

    @Override
    void setBoolean(String prefKey, boolean prefVal) throws YapelException {
        String encryptedPrefVal = CryptUtils.encryptBoolean(prefVal, mKey);
        writeString(prefKey, encryptedPrefVal);
    }

    @Override
    float getFloat(String prefKey, float defaultValue) throws YapelException {
        String encryptedFloatValueAsString = readString(prefKey);
        if (encryptedFloatValueAsString == null) {
            return defaultValue;
        } else {
            return CryptUtils.decryptFloat(encryptedFloatValueAsString, mKey);
        }
    }

    @Override
    void setFloat(String prefKey, float prefVal) throws YapelException {
        String encryptedPrefVal = CryptUtils.encryptFloat(prefVal, mKey);
        writeString(prefKey, encryptedPrefVal);
    }

    @Override
    long getLong(String prefKey, long defaultValue) throws YapelException {
        String longAsString = readString(prefKey);
        if (longAsString == null) {
            return defaultValue;
        } else {
            return CryptUtils.decryptLong(longAsString, mKey);
        }
    }

    @Override
    void setLong(String prefKey, long prefVal) throws YapelException {
        String encryptedPrefVal = CryptUtils.encryptLong(prefVal, mKey);
        writeString(prefKey, encryptedPrefVal);
    }

    @Override
    int getInt(String prefKey, int defaultValue) throws YapelException {
        String intAsString = readString(prefKey);
        if (intAsString == null) {
            return defaultValue;
        } else {
            return CryptUtils.decryptInt(intAsString, mKey);
        }
    }

    @Override
    void setInt(String prefKey, int prefVal) throws YapelException {
        String encryptedPrefVal = CryptUtils.encryptInt(prefVal, mKey);
        writeString(prefKey, encryptedPrefVal);
    }

    @Override
    void setStringSet(String prefKey, Set<String> prefVal) throws YapelException {
        Set<String> encryptedPrefVal = CryptUtils.encryptStringSet(prefVal, mKey);
        writeStringSet(prefKey, encryptedPrefVal);
    }

    @Override
    Set<String> getStringSet(String prefKey, Set<String> defaultValue) throws YapelException {
        Set<String> encryptedPrefVal = readStringSet(prefKey);
        if (encryptedPrefVal == null) {
            return defaultValue;
        } else {
            return CryptUtils.decryptStringSet(encryptedPrefVal, mKey);
        }
    }


}

