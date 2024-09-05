package com.kenny.section03.remix;

import java.util.List;

// xmlconfig - MenuDAO 의 역할을 대신하는 MenuMapper 인터페이스
// 구현체를 만들 필요 없이(MyBatis가 알아서 만든다) 어노테이션을 사용하면 된다.
public interface MenuMapper {

    List<MenuDTO> selectAllMenu();

    MenuDTO selectMenuByMenuCode(int menuCode);

    int insertMenu(MenuDTO menu);

    int updateMenu(MenuDTO menu);

    int deleteMenu(int menuCode);
}