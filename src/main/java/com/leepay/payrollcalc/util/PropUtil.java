package com.leepay.payrollcalc.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class PropUtil {
    private static Environment env;

    @Autowired
    public void PropertyService(Environment env) {
        PropUtil.env = env;
    }

    public static String getProperty(String key) {
        return env.getProperty(key);
    }
}
