package com.leepay.payrollcalc.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class CommonUtil {
    public static <T> Map<String, Object> convertDtoToMap(T dto) {
        Map<String, Object> map = new HashMap<>();

        // DTO의 클래스 정보 가져오기
        Class<?> dtoClass = dto.getClass();

        // DTO의 모든 필드에 접근하기
        for (Field field : dtoClass.getDeclaredFields()) {
            // 필드의 접근성 설정 (private 필드에 접근 가능하도록)
            field.setAccessible(true);

            try {
                // 필드의 이름과 값을 Map에 추가
                map.put(field.getName(), field.get(dto));
            } catch (IllegalAccessException e) {
                e.printStackTrace(); // 필드에 접근하는 중에 발생하는 예외 처리
            }
        }

        return map;
    }

}
