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
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("unused")
@RunWith(AndroidJUnit4.class)
public class DefaultFileYapelTest {

    private Yapel yapel;
    private static final String STRING_KEY = "string key";
    private static final String STRING_SET_KEY = "stringset key";
    private static final String INT_KEY = "int key";
    private static final String LONG_KEY = "long key";
    private static final String FLOAT_KEY = "float key";
    private static final String BOOLEAN_KEY = "boolean key";

    @Before
    public void setup() throws Exception {
        Context instrumentationCtx = InstrumentationRegistry.getContext();
        yapel = new Yapel("my_key_alias", instrumentationCtx);

    }

    @Test
    public void stringTest() throws Exception {
        String str = "whatever";
        yapel.setString(STRING_KEY, str);
        String result = yapel.getString(STRING_KEY, null);
        assertTrue(str.equals(result));
    }

    @Test
    public void intTest() throws Exception {
        int value = 12345;
        yapel.setInt(INT_KEY, value);
        int result = yapel.getInt(INT_KEY,0);
        assertEquals(value, result);
    }

    @Test
    public void booleanTest() throws Exception{
        yapel.setBoolean(BOOLEAN_KEY,true);
        boolean result = yapel.getBoolean(BOOLEAN_KEY,false);
        assertEquals(true, result);
    }

    @Test
    public void booleanTest2() throws Exception{
        yapel.setBoolean(BOOLEAN_KEY,false);
        boolean result = yapel.getBoolean(BOOLEAN_KEY,true);
        assertEquals(false, result);
    }

    @Test
    public void longTest() throws Exception {
        long value = 12345678;
        yapel.setLong(LONG_KEY, value);
        long result = yapel.getLong(LONG_KEY,0);
        assertEquals(value, result);
    }

    @Test
    public void floatTest() throws Exception {
        float value = 1234.5678F;
        yapel.setFloat(FLOAT_KEY, value);
        float result = yapel.getFloat(FLOAT_KEY,0F);
        assertEquals(value, result,0.00001F);
    }

    @Test
    public void stringSetTest() throws Exception {
        Set<String> value = new HashSet<>();
        value.add("sálálálá");
        value.add("Subidubi");
        value.add("\\Ä€Í;>*$Ł");
        yapel.setStringSet(STRING_SET_KEY, value);
        Set<String> result = yapel.getStringSet(STRING_SET_KEY, null);
        assertTrue(value.equals(result));
    }

}
