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

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


public class YapelKey {

    private static final String ANDROID_KEYSTORE_PROVIDER = "AndroidKeyStore";
    private static final int AES_KEY_LENGTH_IN_BITS = 256;
    private String mAlias;

    public YapelKey(String alias) throws YapelKeyException {
        mAlias = alias;
        if (!hasKey()){
            createKey();
        }
    }

    public void createKey() throws YapelKeyException {
        KeyStore keyStore;

        try {
            keyStore = initKeyStore();

            if (!keyStore.containsAlias(mAlias)) {

                KeyGenerator generator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES,
                        ANDROID_KEYSTORE_PROVIDER);

                KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(
                        mAlias,
                        KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT);

                builder.setKeySize(AES_KEY_LENGTH_IN_BITS)
                        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE);

                generator.init(builder.build());
                generator.generateKey();
            }
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | NoSuchProviderException | KeyStoreException e) {
            throw new YapelKeyException(e);
        }
    }

    public SecretKey getKey() throws YapelKeyException {
        KeyStore keyStore = initKeyStore();
        try {
            return (SecretKey) keyStore.getKey(mAlias, null);
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new YapelKeyException(e);
        }
    }

    public boolean deleteKey() throws YapelKeyException {
        boolean result = false;
        KeyStore keyStore;

        try {
            keyStore = initKeyStore();

            if (keyStore.containsAlias(mAlias)) {
                keyStore.deleteEntry(mAlias);
                result = true;
            }
        } catch (KeyStoreException e) {
            throw new YapelKeyException(e);
        }
        return result;
    }

    public boolean hasKey() throws YapelKeyException {
        boolean result = false;

        KeyStore keyStore = initKeyStore();
        try {
            if (keyStore.containsAlias(mAlias)) {
                result = true;
            }
        } catch (KeyStoreException e) {
            throw new YapelKeyException(e);
        }
        return result;
    }

    private KeyStore initKeyStore() throws YapelKeyException {
        KeyStore keyStore;
        try {
            keyStore = KeyStore.getInstance(ANDROID_KEYSTORE_PROVIDER);
            keyStore.load(null);
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            throw new YapelKeyException(e);
        }
        return keyStore;
    }
}
