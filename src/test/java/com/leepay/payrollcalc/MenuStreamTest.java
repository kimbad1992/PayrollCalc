package com.leepay.payrollcalc;

import com.leepay.payrollcalc.dto.Menu;
import com.leepay.payrollcalc.mapper.SystemMapper;
import com.leepay.payrollcalc.service.SystemService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
public class MenuStreamTest {

    @Autowired
    private SystemService systemService;

    @Autowired
    private SystemMapper systemMapper;

    @Test
    void 메뉴_테스트() {
        List<Menu> originalMenuList = systemService.getAllMenu();
        List<Menu> streamMenuList = streamMenuList2(systemMapper.getAllMenu());

        assertEquals(originalMenuList, streamMenuList);
    }

    private List<Menu> streamMenuList(List<Menu> menus) {
        // 메뉴들을 Page_seq를 키로 하는 Map으로 변환
        Map<String, Menu> menuMap = menus.stream()
                .collect(Collectors.toMap(Menu::getPage_seq, menu -> menu));

        // 최상위 메뉴와 서브 메뉴 분리
        List<Menu> topLevelMenus = menus.stream()
                .filter(menu -> menu.getParent_page_seq() == null || menu.getParent_page_seq().equals("0"))
                .collect(Collectors.toList());

        menus.stream()
                .filter(menu -> menu.getParent_page_seq() != null && !menu.getParent_page_seq().equals("0"))
                .forEach(menu -> {
                    Menu parent = menuMap.get(menu.getParent_page_seq());
                    if (parent != null) {
                        parent.getSub_menus().add(menu);
                    }
                });
        return topLevelMenus;
    }

    private List<Menu> streamMenuList2(List<Menu> menus) {
        // 메뉴들을 Page_seq를 키로 하는 Map으로 변환
        Map<String, Menu> menuMap = new HashMap<>();

        return menus.stream()
                .peek(menu -> menuMap.put(menu.getPage_seq(), menu)) // 메뉴들을 Map에 저장
                .filter(menu -> {
                    if (menu.getParent_page_seq() == null || menu.getParent_page_seq().equals("0")) {
                        return true; // 최상위 메뉴로 유지
                    } else {
                        Menu parent = menuMap.get(menu.getParent_page_seq());
                        if (parent != null) {
                            parent.getSub_menus().add(menu); // 서브 메뉴로 추가
                        }
                        return false; // 최상위 메뉴에서 제외
                    }
                })
                .collect(Collectors.toList()); // 최상위 메뉴만 리스트로 반환
    }
}
