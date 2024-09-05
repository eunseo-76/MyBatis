package com.kenny.section01.xmlconfig;


import org.apache.ibatis.session.SqlSession;

import java.util.List;

// 실제 crud 작업 수행
public class MenuDAO {
    public List<MenuDTO> selectAllMenu(SqlSession sqlSession) {
        /* 메소드의 첫 번째 인자로 어떠한 SQL 구문을 수행할 것인지를 전달한다.
        * Mapper의 namespace와 id로 구분한다. */
        return sqlSession.selectList("MenuMapper.selectAllMenu");   // Mapper의 namesapce.id
        // menuDTO 타입의 객체로 변환 시켜서 리턴
        // sql 구문 실행하고, 그 결과값을 알맞은 자바 객체 타입으로 바꾸어서 List 타입으로 반환 (이에 대한 자세한 내용은 mapper.xml에
    }

    public MenuDTO selectMenuByMenuCode(SqlSession sqlSession, int menuCode) {
        /* 메소드의 두 번째 인자로 SQL 구문을 수행할 때 필요한 객체를 전달한다. -> 여기서는 menuCode */
        return sqlSession.selectOne("MenuMapper.selectMenuByMenuCode", menuCode);
    }

    public int insertMenu(SqlSession sqlSession, MenuDTO menu) {
        return sqlSession.insert("MenuMapper.insertMenu", menu);
    }

    public int updateMenu(SqlSession sqlSession, MenuDTO menu) {
        return sqlSession.update("MenuMapper.updateMenu", menu);
    }

    public int deleteMenu(SqlSession sqlSession, int menuCode) {
        return sqlSession.delete("MenuMapper.deleteMenu", menuCode);
    }
}

// 이렇게 구현체를 만드는 건 불필요하지 않을까? => xml 대신 java로 인터페이스로 처리