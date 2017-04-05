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

import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("unused")
@RunWith(AndroidJUnit4.class)
public class CryptUtilsTest {

    private YapelKey mKey;

    @Before
    public void initKey()  throws Exception{
        mKey = new YapelKey("whatever");
    }

    @After
    public void deleteKey()  throws Exception{
        mKey.deleteKey();
    }

    @Test
    public void encryptString_decryptString() throws Exception {

        String plainString = "whatever1234";
        String encryptedString = CryptUtils.encryptString(plainString, mKey.getKey());
        String decryptedString = CryptUtils.decryptString(encryptedString, mKey.getKey());

        assertTrue(plainString.equals(decryptedString));
    }

    @Test
    public void encryptString_decryptString2() throws Exception {
        String plainString = "whatever1234";
        String encryptedString = CryptUtils.encryptString(plainString, mKey.getKey());

        mKey = new YapelKey("whatever2");

        try {
            @SuppressWarnings("UnusedAssignment") String decryptedString = CryptUtils.decryptString(encryptedString, mKey.getKey());
        } catch (YapelException e) {
            assertTrue("javax.crypto.AEADBadTagException".equals(e.getMessage()));
        }
    }

    @Test
    public void encryptBoolean_decryptBoolean() throws Exception {
        String encryptedBoolean = CryptUtils.encryptBoolean(true, mKey.getKey());
        boolean decryptedBoolean = CryptUtils.decryptBoolean(encryptedBoolean, mKey.getKey());

        assertEquals(true, decryptedBoolean);
    }

    @Test
    public void encryptBoolean_decryptBoolean2() throws Exception {
        String encryptedBoolean = CryptUtils.encryptBoolean(false, mKey.getKey());
        boolean decryptedBoolean = CryptUtils.decryptBoolean(encryptedBoolean, mKey.getKey());

        assertEquals(false, decryptedBoolean);
    }

    @Test
    public void encryptFloat_decryptFloat() throws Exception {
        float value = (float) 6.836542E-8;
        String encryptedFloat = CryptUtils.encryptFloat(value, mKey.getKey());
        float decryptedFloat = CryptUtils.decryptFloat(encryptedFloat, mKey.getKey());

        //noinspection RedundantCast,RedundantCast
        assertEquals((float)value, (float)decryptedFloat,0.0001F);
    }

    @Test
    public void encryptLong_decryptLong() throws Exception {
        long value = 654987321;
        String encryptedLong = CryptUtils.encryptLong(value, mKey.getKey());
        float decryptedLong = CryptUtils.decryptLong(encryptedLong, mKey.getKey());

        assertEquals(value, decryptedLong,1);
    }

    @Test
    public void encryptInt_decryptInt() throws Exception {
        int value = 654987;
        String encryptedInt = CryptUtils.encryptInt(value, mKey.getKey());
        float decryptedInt = CryptUtils.decryptInt(encryptedInt, mKey.getKey());

        assertEquals(value, decryptedInt,1);
    }

    @Test
    public void encryptStringSet_decryptStringSet () throws Exception {
        Set<String> value = new HashSet<>();
        value.add("subidubi");
        value.add("sálálálá");
        value.add("whatever");
        Set<String> encryptedStringSet = CryptUtils.encryptStringSet(value,mKey.getKey());
        Set<String> decryptedStringSet = CryptUtils.decryptStringSet(encryptedStringSet,mKey.getKey());

        assertTrue(value.equals(decryptedStringSet));
    }
}
