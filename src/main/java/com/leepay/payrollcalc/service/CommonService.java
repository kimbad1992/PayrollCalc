package com.leepay.payrollcalc.service;

import com.leepay.payrollcalc.dto.Menu;
import com.leepay.payrollcalc.mapper.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonService {
    @Autowired
    private CommonMapper commonMapper;

    public List<Menu> getAllMenu() {
        return commonMapper.getAllMenu();
    }
}
