package com.example.commons.validators;

public interface MobileValidator {

    default boolean mobileNumCheck(String mobile) {
        /**
         *  010-0000-0000
         *  010_0000_0000
         *  010 0000 0000
         *
         *  1. 형식의 통일화 - 숫자가 아닌 문자를 전부 제거 -> 숫자
         *  2. 패턴 생성 체크
         *  (복잡한 패턴일 땐 단순화할 방법을 생각해야함)
         */
        mobile = mobile.replaceAll("\\D", "");
        String pattern = "^01[016]\\d{3,4}\\d{4}$";

        return mobile.matches(pattern);
    }
}
