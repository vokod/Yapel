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
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.crypto.SecretKey;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class YapelKeyTest {

    @Test
    public void createKey_getKey_isCorrect() throws Exception {
        YapelKey yapelKey = new YapelKey("whatever");
        // yapelKey.createKey();
        SecretKey sKey = yapelKey.getKey();

        assertNotNull(sKey);
    }

    @Test
    public void hasKey_isCorrect() throws Exception {
        YapelKey yapelKey = new YapelKey("whatever");
        // yapelKey.createKey();
        Assert.assertEquals(yapelKey.hasKey(),true);
    }

    @Test
    public void deleteKey_isCorrect() throws Exception {
        YapelKey yapelKey = new YapelKey("whatever");
        yapelKey.deleteKey();
        Assert.assertEquals(yapelKey.hasKey(),false);
    }

    @After
    public void deleteKey() throws Exception{
        YapelKey yapelKey = new YapelKey("whatever");
        yapelKey.deleteKey();
    }
}
