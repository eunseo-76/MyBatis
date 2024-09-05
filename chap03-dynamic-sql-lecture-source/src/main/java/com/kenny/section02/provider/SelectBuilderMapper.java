package com.kenny.section02.provider;

import com.kenny.common.MenuDTO;
import com.kenny.common.SearchCriteria;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface SelectBuilderMapper {

    // 이전에는 @Select("...select 구문...") 이렇게 헀으나
    // 이번에는 selectprovider 사용하여 구문을 만들어서 가져옴
    @SelectProvider(type = SelectBuilderProvider.class, method = "selectAllMenu")
    List<MenuDTO> selectAllMenu();  // mapper에 메소드 만들기

    @SelectProvider(type = SelectBuilderProvider.class, method = "searchMenuByNameOrCategory")
    List<MenuDTO> searchMenuByNameOrCategory(SearchCriteria searchCriteria);
}
