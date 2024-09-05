package com.kenny.section01.javaconfig;

import org.apache.ibatis.annotations.Select;

// Mapper : 수행해야할 sql 문을 작성해놓는 인터페이스
// menuMapper 이런 식으로 비즈니스 로직과 관련된 이름을 붙인다

public interface Mapper {

    @Select("SELECT NOW()")
    java.util.Date selectDate();    // selectDate 메소드의 반환값을 java.util.Date로 설정

}
