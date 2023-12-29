package com.leepay.payrollcalc.service;

import com.leepay.payrollcalc.dto.AdminUser;
import com.leepay.payrollcalc.mapper.PlaygroundMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaygroundService {

    @Autowired
    private PlaygroundMapper playgroundMapper;

    public String getPlaygroundData(String key) {
        return playgroundMapper.getPlaygroundData(key);
    }
}
