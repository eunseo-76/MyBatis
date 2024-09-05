package com.kenny.section02.javaconfig;

import org.apache.ibatis.annotations.*;

import java.util.List;

// xmlconfig - MenuDAO 의 역할을 대신하는 MenuMapper 인터페이스
// 구현체를 만들 필요 없이(MyBatis가 알아서 만든다) 어노테이션을 사용하면 된다.
public interface MenuMapper {
    // select 수행 결과에 따른 매핑
    @Results(id = "menuResultMap", value = {
            @Result(id = true, property = "menuCode", column = "menu_code"),
            @Result(property = "menuName", column = "menu_name"),
            @Result(property = "menuPrice", column = "menu_price"),
            @Result(property = "categoryCode", column = "category_code"),
            @Result(property = "orderableStatus", column = "orderable_status"),
    })
    @Select("       SELECT\n" +
            "                menu_code\n" +
            "              , menu_name\n" +
            "              , menu_price\n" +
            "              , category_code\n" +
            "              , orderable_status\n" +
            "         FROM tbl_menu\n" +
            "        WHERE orderable_status = 'Y'\n" +
            "        ORDER BY menu_code")

    List<MenuDTO> selectAllMenu();


    @ResultMap("menuResultMap") // 위에서 쓴 @Results 어노테이션을 이용해 선언된 id 재사용
    @Select("       SELECT\n" +
            "                menu_code\n" +
            "              , menu_name\n" +
            "              , menu_price\n" +
            "              , category_code\n" +
            "              , orderable_status\n" +
            "         FROM tbl_menu\n" +
            "        WHERE orderable_status = 'Y'\n" +
            "          AND menu_code = #{ menuCode }")
    MenuDTO selectMenuByMenuCode(int menuCode);

    @Insert("INSERT INTO\n" +
            "            tbl_menu(menu_name, menu_price, category_code, orderable_status)\n" +
            "        VALUES\n" +
            "            (#{ menuName }, #{ menuPrice }, #{ categoryCode }, 'Y')")
    int insertMenu(MenuDTO menu);

    @Update("UPDATE tbl_menu\n" +
            "           SET menu_name = #{ menuName },\n" +
            "               menu_price = #{ menuPrice },\n" +
            "               category_code = #{ categoryCode }\n" +
            "         WHERE menu_code = #{ menuCode }")
    int updateMenu(MenuDTO menu);

    @Delete("DELETE FROM tbl_menu\n" +
            "         WHERE menu_code = #{ menuCode }")
    int deleteMenu(int menuCode);
}

// sql 쿼리 쓰는 게 너무 구리다! => xml 설정과 java 설정을 합친다