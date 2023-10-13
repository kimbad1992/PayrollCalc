package com.leepay.payrollcalc.dto;


import lombok.Data;

@Data
public class Menu {
    String page_seq;
    String page_name;
    String page_url;
    String gnb_sort;
    String gnb_name;
    String role_id;
    String parent_page_seq;
    String level;
    String role_name;
    Boolean has_sub_menu; // 부모 메뉴 판단용
}
