/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.util;

import lombok.NonNull;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Hex;

/**
 * Utility methods for computing hash values.
 */
public final class HashUtils {

    private static final String MD5 = "MD5";
    private static final String SHA256 = "SHA-256";
    private static final String HMAC_SHA256 = "HmacSHA256";
    private static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");

    // -- message digest utils begin --

    /**
     * MD5 Message Digest
     *
     * @param input data to calculate message digest
     * @return String
     */
    public static String md5(@NonNull byte[] input) {
        return md5(input, 0, input.length);
    }

    public static String md5(@NonNull String input) {
        return md5(input.getBytes(CHARSET_UTF8));
    }

    public static String md5(@NonNull byte[] input, int offset, int length) {
        try {
            byte[] hash = messageDigest(input, offset, length, MessageDigest.getInstance(MD5));
            return new String(Hex.encodeHex(hash));
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static byte[] md5(@NonNull InputStream is) throws IOException {
        try {
            return messageDigest(is, MessageDigest.getInstance(MD5));
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * SHA256 Message Digest
     *
     * @param input data to calculate message digest
     * @return String
     */
    public static String sha256(@NonNull byte[] input) {
        return sha256(input, 0, input.length);
    }

    public static String sha256(@NonNull byte[] input, int offset, int length) {
        try {
            byte[] hash = messageDigest(input, offset, length, MessageDigest.getInstance(SHA256));
            return new String(Hex.encodeHex(hash));
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static byte[] sha256(@NonNull InputStream is) throws NoSuchAlgorithmException, IOException {
        try {
            return messageDigest(is, MessageDigest.getInstance(SHA256));
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static byte[] messageDigest(@NonNull byte[] input, int offset, int length,
                                       @NonNull MessageDigest messageDigest) {
        messageDigest.update(input, offset, length);
        return messageDigest.digest();
    }

    public static byte[] messageDigest(@NonNull InputStream is, @NonNull MessageDigest messageDigest)
            throws IOException {
        try {
            byte[] buffer = new byte[16384];
            int bytesRead;
            while ((bytesRead = is.read(buffer, 0, buffer.length)) != -1) {
                messageDigest.update(buffer, 0, bytesRead);
            }
            return messageDigest.digest();
        } finally {
            try {
                is.close();
            } catch (Exception e) {
                // do nothing
            }
        }
    }

    // -- message digest utils end --

    // -- message authentication code utils begin --

    /**
     * Encodes the input String using the UTF8 charset and calls hmacSha256;
     *
     * @param input data to calculate mac
     * @param secretKey secret key
     * @return String, sha256 mac
     */
    public static String hmacSha256(@NonNull String input, @NonNull String secretKey) {
        return hmacSha256(input.getBytes(CHARSET_UTF8), secretKey.getBytes(CHARSET_UTF8));
    }

    public static String hmacSha256(@NonNull byte[] input, @NonNull byte[] secretKey) {
        return hmacSha256(input, 0, input.length, secretKey);
    }

    public static String hmacSha256(@NonNull byte[] input, int offset, int length, @NonNull byte[] secretKey) {
        return new String(Hex.encodeHex(mac(input, offset, length, new SecretKeySpec(secretKey, HMAC_SHA256))));
    }

    public static byte[] mac(@NonNull byte[] input, @NonNull SecretKey secretKey) {
        return mac(input, 0, input.length, secretKey);
    }

    public static byte[] mac(@NonNull byte[] input, int offset, int length, @NonNull SecretKey secretKey) {
        try {
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            mac.update(input, offset, length);
            return mac.doFinal();
        } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
            throw new RuntimeException(ex);
        }
    }

    // -- message authentication code utils end --
}
