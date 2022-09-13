package com.mindhub.homebanking.utils;

public final class CardUtils {

    private CardUtils() {
    }

    public static Integer getCvvNumber() {
        Integer randomCvvNumber = getRandomNumber(100,999);
        return randomCvvNumber;
    }

    public static String getRandomNumberCard() {
        String randomCardNumber = getRandomNumber(1000,9999) + "-" + getRandomNumber(1000,9999)+ "-" + getRandomNumber(1000,9999)+ "-" + getRandomNumber(1000,9999);
        return randomCardNumber;
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }


}
