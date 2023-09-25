package com.aiman.coursemanagement.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import java.security.SecureRandom;

public class GeneralUtils {
    public static String generateRandomPassword(int length) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);

        // Encode the random bytes into a secure string
        return Base64.encodeBase64String(bytes);
    }
}
