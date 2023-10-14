package com.leepay.payrollcalc.dto;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
    List<Menu> sub_menus = new ArrayList<Menu>();
}
