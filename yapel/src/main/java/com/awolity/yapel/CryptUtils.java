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

import android.util.Base64;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class CryptUtils {

    private CryptUtils() {
    }

    private static final int IV_LENGTH_IN_BYTES = 12; // GCM mode cipher only accepts 12 byte IV
    private static final int GCM_AUTH_TAG_LENGTH_IN_BITS = 128;
    private static final String AES_GCM_NOPADDING = "AES/GCM/NoPadding";
    private static final String TRUE = "TRUE";
    private static final String FALSE = "FALSE";

    public static String encryptString(String plainText, SecretKey key) throws YapelException {
        byte[] plainArray = plainText.getBytes();
        byte[] encryptedArray = encryptByteArray(plainArray, key);
        String encryptedString = Base64.encodeToString(encryptedArray, Base64.DEFAULT);
        return removeNewLines(encryptedString);
    }

    public static String decryptString(String encryptedString, SecretKey key) throws YapelException {
        byte[] encryptedArray = Base64.decode(encryptedString, Base64.DEFAULT);
        byte[] decryptedArray = decryptByteArray(encryptedArray, key);
        String decryptedString = new String(decryptedArray);
        return removeNewLines(decryptedString);
    }

    public static String encryptBoolean(boolean value, SecretKey key) throws YapelException {
        if (value) {
            return encryptString(TRUE, key);
        } else {
            return encryptString(FALSE, key);
        }
    }

    public static boolean decryptBoolean(String encryptedBoolean, SecretKey key) throws YapelException {
        String decryptedBoolean = decryptString(encryptedBoolean, key);
        switch (decryptedBoolean) {
            case TRUE:
                return true;
            case FALSE:
                return false;
            default:
                throw new YapelException(new Throwable("Cannot decrypt boolean"));
        }
    }

    public static String encryptFloat(float floatValue, SecretKey key) throws YapelException {
        String floatAsString = Float.toString(floatValue);
        return encryptString(floatAsString, key);
    }

    public static float decryptFloat(String encryptedFloat, SecretKey key) throws YapelException {
        String decryptedFloatAsString = decryptString(encryptedFloat, key);
        return Float.parseFloat(decryptedFloatAsString);
    }

    public static String encryptLong(long longValue, SecretKey key) throws YapelException {
        String valueAsString = Long.toString(longValue);
        return encryptString(valueAsString, key);
    }

    public static long decryptLong(String encryptedLongAsString, SecretKey key) throws YapelException {
        String decryptedLongAsString = decryptString(encryptedLongAsString, key);
        return Long.parseLong(decryptedLongAsString);
    }

    public static String encryptInt(int intValue, SecretKey key) throws YapelException {
        String intValueAsString = Integer.toString(intValue);
        return encryptString(intValueAsString, key);
    }

    public static int decryptInt(String encryptedIntAsString, SecretKey key) throws YapelException {
        String decryptedIntAsString = decryptString(encryptedIntAsString, key);
        return Integer.parseInt(decryptedIntAsString);
    }

    public static Set<String> encryptStringSet(Set<String> plainStringSet, SecretKey key) throws YapelException {
        Set<String> encryptedStringSet = new HashSet<>(plainStringSet.size());
        for (String element : plainStringSet) {
            encryptedStringSet.add(encryptString(element, key));
        }
        return encryptedStringSet;
    }

    public static Set<String> decryptStringSet(Set<String> encryptedStringSet, SecretKey key) throws YapelException {
        Set<String> plainStringSet = new HashSet<>(encryptedStringSet.size());
        for (String element : encryptedStringSet) {
            plainStringSet.add(decryptString(element, key));
        }
        return plainStringSet;
    }

    static private byte[] encryptByteArray(byte[] plainArray, SecretKey key) throws YapelException {
        byte[] result;

        Cipher cipher;
        try {
            cipher = Cipher.getInstance(AES_GCM_NOPADDING);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] iv = cipher.getIV();
            byte[] encryptedBytes = cipher.doFinal(plainArray);
            result = concatenateByteArrays(iv, encryptedBytes);

        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new YapelException(e);
        }
        return result;
    }

    private static byte[] decryptByteArray(byte[] encryptedArray, SecretKey key)
            throws YapelException {

        byte[] plainArray;
        byte[] iv = new byte[IV_LENGTH_IN_BYTES];
        byte[] encryptedBytes = new byte[encryptedArray.length - IV_LENGTH_IN_BYTES];

        System.arraycopy(encryptedArray, 0, iv, 0, IV_LENGTH_IN_BYTES);
        System.arraycopy(encryptedArray, IV_LENGTH_IN_BYTES, encryptedBytes, 0, encryptedBytes.length);

        Cipher cipher;
        try {
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_AUTH_TAG_LENGTH_IN_BITS, iv);
            cipher = Cipher.getInstance(AES_GCM_NOPADDING);
            cipher.init(Cipher.DECRYPT_MODE, key, gcmParameterSpec);
            plainArray = cipher.doFinal(encryptedBytes);

        } catch (IllegalBlockSizeException
                | BadPaddingException | NullPointerException | NoSuchPaddingException
                | NoSuchAlgorithmException | InvalidKeyException |
                InvalidAlgorithmParameterException e) {
            throw new YapelException(e);
        }
        return plainArray;
    }

    private static byte[] concatenateByteArrays(byte[] first, byte[] second) {
        byte[] result = new byte[first.length + second.length];
        System.arraycopy(first, 0, result, 0, first.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    private static String removeNewLines(String input){
        String result = input.replace("\n","");
        result = result.replace("\r", "");
        return result;
    }


}
