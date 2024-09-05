package com.kenny.section02.provider;

import com.kenny.common.SearchCriteria;
import org.apache.ibatis.jdbc.SQL;

public class SelectBuilderProvider {

    public String selectAllMenu() {

        // 반환값(문자열이어야 함)에 문자열화 한 SQL 객체 넣기
        return new SQL()
                .SELECT("menu_code")
                .SELECT("menu_name")
                .SELECT("menu_price")
                .SELECT("category_code")
                .SELECT("orderable_status")
                .FROM("tbl_menu")
                .WHERE("orderable_status = 'Y'")
                .toString();    // sql 객체를 문자열화 시킴
    }

    public String searchMenuByNameOrCategory(SearchCriteria searchCriteria) {

        SQL sql = new SQL();

        sql.SELECT("menu_code", "menu_name", "menu_price", "category_code", "orderable_status")
                .FROM("tbl_menu");

        if("category".equals(searchCriteria.getCondition())) {
            sql.JOIN("tbl_category USING (category_code)")
                    .WHERE("orderable_status = 'Y'")
                    .AND()
                    .WHERE("category_name = #{ value }");
        } else if("name".equals(searchCriteria.getCondition())) {
            // where 메소드에 가변 인자로 전달 시 자동적으로 AND로 조건이 처리되므로 OR의 경우 별도 메소드 사용할 것
            sql.WHERE("orderable_status = 'Y'", "menu_name LIKE CONCAT('%', #{ value }, '%')");
        }

        return sql.toString();
    }
}
// 파라미터 searchCriteria가 전달된다
