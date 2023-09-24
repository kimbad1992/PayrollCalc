package com.leepay.payrollcalc;

import com.leepay.payrollcalc.dto.Menu;
import com.leepay.payrollcalc.dto.StaticMenu;
import com.leepay.payrollcalc.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MenuInitializer implements ApplicationRunner {

    @Autowired
    private CommonService commonService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 전체 메뉴-권한 정보 리스트 조회/저장
        StaticMenu.menuList = commonService.getAllMenu();
    }
}
