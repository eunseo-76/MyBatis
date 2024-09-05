package com.kenny.section02.provider;

import com.kenny.common.MenuDTO;
import org.apache.ibatis.jdbc.SQL;

public class SqlBuilderProvider {

    public String insertMenu(MenuDTO menuDTO) {

        return new SQL()
                .INSERT_INTO("tbl_menu")
                .VALUES("menu_name", "#{ menuName }")   // menuDTO.getMenuName() 도 가능
                .VALUES("menu_price", "#{ menuPrice }")
                .VALUES("category_code", "#{ categoryCode }")
                .VALUES("orderable_status", "#{ orderableStatus }")
                .toString();
    }

    public String updateMenu(MenuDTO menuDTO) {

        SQL sql = new SQL();
        sql.UPDATE("tbl_menu");

        if (menuDTO.getMenuName() != null && !menuDTO.getMenuName().isEmpty()) {
            sql.SET("menu_name = #{ menuName }");
        }

        if (menuDTO.getMenuName() != null && !menuDTO.getMenuName().isEmpty()) {
            sql.SET("orderable_status = #{ orderableStatus }");
        }

        if (menuDTO.getCategoryCode() > 0) {
            sql.SET("category_code = #{ categoryCode }");
        }

        if (menuDTO.getMenuPrice() > 0) {
            sql.SET("menu_price = #{ menuPrice }");
        }

        sql.WHERE("menu_code = #{ menuCode }");

        return sql.toString();
    }

    // 매개변수 x
    public String deleteMenu() {
        return new SQL()
                .DELETE_FROM("tbl_menu")
                .WHERE("menu_code = #{ menuCode }") // @Param("menuCode")
                .toString();
    }
}
