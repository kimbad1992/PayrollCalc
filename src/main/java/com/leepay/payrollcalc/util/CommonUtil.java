package com.leepay.payrollcalc.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Slf4j
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
                log.error("Reflection API Error : {}", e);
            }
        }

        return map;
    }

}
