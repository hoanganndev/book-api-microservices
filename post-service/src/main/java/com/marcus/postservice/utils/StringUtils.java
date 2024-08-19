package com.marcus.postservice.utils;

import java.util.Random;

public class StringUtils {
    public static String generateRandomText(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "abcdefghijklmnopqrstuvwxyz"
                + "0123456789";
        StringBuilder randomText = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            randomText.append(characters.charAt(index));
        }
        return randomText.toString();
    }
}
