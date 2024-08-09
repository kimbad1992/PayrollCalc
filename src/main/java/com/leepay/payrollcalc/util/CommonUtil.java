package com.leepay.payrollcalc.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CommonUtil {
    public static <T> Map<String, Object> convertDtoToMap(T dto) {
        Map<String, Object> map = new HashMap<>();

        Arrays.stream(dto.getClass().getDeclaredFields()).forEach(field -> {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(dto));
            } catch (IllegalAccessException e) {
                log.error("Reflection API Error : {}", e);
            }
        });

        return map;
    }

}
