package com.leepay.payrollcalc.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class RandomUtil {

    private static String DATA = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static SecureRandom random = new SecureRandom();

    /** 랜덤 문자열을 생성한다 **/
    @Autowired
    public static String generate(int length) {
        if (length < 1) {
            throw new IllegalArgumentException("길이는 1자리 이상이어야 합니다.");
        }

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append( DATA.charAt(random.nextInt(DATA.length())));
        }
        return sb.toString();
    }

//    public static void main(String[] args) {
//
//        String ENGLISH_LOWER = "abcdefghijklmnopqrstuvwxyz";
//        String ENGLISH_UPPER = ENGLISH_LOWER.toUpperCase();
//        String NUMBER = "0123456789";
//
//        /** 랜덤을 생성할 대상 문자열 **/
//        String DATA_FOR_RANDOM_STRING = ENGLISH_LOWER + ENGLISH_UPPER + NUMBER;
//
//        /** 랜덤 문자열 길이 **/
//        int random_string_length=10;
//
//        System.out.println("DATA_FOR_RANDOM_STRING ==> " + DATA_FOR_RANDOM_STRING);
//        for (int i = 0; i < 10; i++) {
//            System.out.println("random " + i + " : " + generate(DATA_FOR_RANDOM_STRING, random_string_length));
//        }
//    }
}
