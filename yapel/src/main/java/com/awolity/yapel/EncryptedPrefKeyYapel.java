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

import java.util.Map;
import java.util.Set;

public class EncryptedPrefKeyYapel extends Yapel {

    public EncryptedPrefKeyYapel(String keyAlias, Context context) throws YapelException {
        super(keyAlias, context);
    }

    public EncryptedPrefKeyYapel(String keyAlias, Context context, String prefFileName) throws YapelException {
        super(keyAlias, context, prefFileName);
    }

    @Override
    String getString(String prefKey, String defaultValue) throws YapelException {
        Map<String, ?> allEntries = getAll();
        String decryptedPrefValue = null;

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String decryptedPrefKey = CryptUtils.decryptString(entry.getKey(), mKey);
            if (decryptedPrefKey.equals(prefKey)) {
                decryptedPrefValue = CryptUtils.decryptString(entry.getValue().toString(), mKey);
            }
        }
        if (decryptedPrefValue == null) {
            return defaultValue;
        }
        return decryptedPrefValue;
    }

    @Override
    void setString(String prefKey, String prefVal) throws YapelException {
        String encryptedPrefKey = CryptUtils.encryptString(prefKey, mKey);
        String encryptedPrefVal = CryptUtils.encryptString(prefVal, mKey);
        writeString(encryptedPrefKey, encryptedPrefVal);
    }

    @Override
    boolean getBoolean(String prefKey, boolean defaultValue) throws YapelException {
        Map<String, ?> allEntries = getAll();
        boolean foundEntry = false;
        boolean decryptedPrefValue = false;

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String decryptedPrefKey = CryptUtils.decryptString(entry.getKey(), mKey);
            if (decryptedPrefKey.equals(prefKey)) {
                decryptedPrefValue = CryptUtils.decryptBoolean(entry.getValue().toString(), mKey);
                foundEntry = true;
            }
        }
        if (!foundEntry) {
            return defaultValue;
        } else {
            return decryptedPrefValue;
        }
    }

    @Override
    void setBoolean(String prefKey, boolean prefVal) throws YapelException {
        String encryptedPrefVal = CryptUtils.encryptBoolean(prefVal, mKey);
        String encryptedPrefKey = CryptUtils.encryptString(prefKey, mKey);
        writeString(encryptedPrefKey, encryptedPrefVal);
    }

    @Override
    float getFloat(String prefKey, float defaultValue) throws YapelException {
        Map<String, ?> allEntries = getAll();
        boolean foundEntry = false;
        float decryptedPrefValue = 0;

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String decryptedPrefKey = CryptUtils.decryptString(entry.getKey(), mKey);
            if (decryptedPrefKey.equals(prefKey)) {
                decryptedPrefValue = CryptUtils.decryptFloat(entry.getValue().toString(), mKey);
                foundEntry = true;
            }
        }
        if (!foundEntry) {
            return defaultValue;
        } else {
            return decryptedPrefValue;
        }
    }

    @Override
    void setFloat(String prefKey, float prefVal) throws YapelException {
        String encryptedPrefVal = CryptUtils.encryptFloat(prefVal, mKey);
        String encryptedPrefKey = CryptUtils.encryptString(prefKey, mKey);
        writeString(encryptedPrefKey, encryptedPrefVal);
    }

    @Override
    long getLong(String prefKey, long defaultValue) throws YapelException {
        Map<String, ?> allEntries = getAll();
        boolean foundEntry = false;
        long decryptedPrefValue = 0;

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String decryptedPrefKey = CryptUtils.decryptString(entry.getKey(), mKey);
            if (decryptedPrefKey.equals(prefKey)) {
                decryptedPrefValue = CryptUtils.decryptLong(entry.getValue().toString(), mKey);
                foundEntry = true;
            }
        }
        if (!foundEntry) {
            return defaultValue;
        } else {
            return decryptedPrefValue;
        }
    }

    @Override
    void setLong(String prefKey, long prefVal) throws YapelException {
        String encryptedPrefVal = CryptUtils.encryptLong(prefVal, mKey);
        String encryptedPrefKey = CryptUtils.encryptString(prefKey, mKey);
        writeString(encryptedPrefKey, encryptedPrefVal);
    }

    @Override
    int getInt(String prefKey, int defaultValue) throws YapelException {
        Map<String, ?> allEntries = getAll();
        boolean foundEntry = false;
        int decryptedPrefValue = 0;

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String decryptedPrefKey = CryptUtils.decryptString(entry.getKey(), mKey);
            if (decryptedPrefKey.equals(prefKey)) {
                decryptedPrefValue = CryptUtils.decryptInt(entry.getValue().toString(), mKey);
                foundEntry = true;
            }
        }
        if (!foundEntry) {
            return defaultValue;
        } else {
            return decryptedPrefValue;
        }
    }

    @Override
    void setInt(String prefKey, int prefVal) throws YapelException {
        String encryptedPrefVal = CryptUtils.encryptInt(prefVal, mKey);
        String encryptedPrefKey = CryptUtils.encryptString(prefKey, mKey);
        writeString(encryptedPrefKey, encryptedPrefVal);
    }

    @Override
    void setStringSet(String prefKey, Set<String> prefVal) throws YapelException {
        Set<String> encryptedPrefVal = CryptUtils.encryptStringSet(prefVal, mKey);
        String encryptedPrefKey = CryptUtils.encryptString(prefKey, mKey);
        writeStringSet(encryptedPrefKey, encryptedPrefVal);
    }

    @Override
    Set<String> getStringSet(String prefKey, Set<String> defaultValue) throws YapelException {
        Map<String, ?> allEntries = getAll();
        Set<String> decryptedPrefValue = null;
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String decryptedPrefKey = CryptUtils.decryptString(entry.getKey(), mKey);
            if (decryptedPrefKey.equals(prefKey)) {
                decryptedPrefValue = CryptUtils.decryptStringSet((Set<String>) entry.getValue(), mKey);
            }
        }
        if (decryptedPrefValue == null) {
            return defaultValue;
        } else {
            return decryptedPrefValue;
        }
    }
}
