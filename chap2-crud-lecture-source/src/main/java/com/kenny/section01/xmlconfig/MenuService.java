package com.kenny.section01.xmlconfig;

import org.apache.ibatis.session.SqlSession;

import java.util.List;

import static com.kenny.section01.xmlconfig.Template.getSqlSession;

// Service : 트랜잭션 단위를 묶어 메소드 화
public class MenuService {

    private MenuDAO menuDAO;

    public MenuService() {
        this.menuDAO = new MenuDAO();
    }

    public List<MenuDTO> selectAllMenu() {
        SqlSession sqlSession = getSqlSession();    // 템플릿 이용하여 sqlsession 객체 받아옴
        // db와 연동된 sqlsession 객체로 모든 메뉴를 가져와야 한다.
        List<MenuDTO> menuList = menuDAO.selectAllMenu(sqlSession); // sql 결과를 반환해서 controller로...

        sqlSession.close();

        return menuList;
    }

    public MenuDTO selectMenuByMenuCode(int menuCode) {
        SqlSession sqlSession = getSqlSession();

        // 이번에는 menuCode라는 파라미터를 같이 넘겨주어야 함.
        MenuDTO menu = menuDAO.selectMenuByMenuCode(sqlSession, menuCode);

        sqlSession.close();

        return menu;

    }

    public boolean registMenu(MenuDTO menu) {
        SqlSession sqlSession = getSqlSession();

        int result = menuDAO.insertMenu(sqlSession, menu);   // menuDTO 타입의 객체도 넘겨준다

        // insert, delete... 메소드의 반환값이 int 라서 int result로 한 것임

        if (result > 0) {
            sqlSession.commit();
        } else {
            sqlSession.rollback();
        }

        sqlSession.close();

        return result > 0 ;

    }

    public boolean modifyMenu(MenuDTO menu) {
        SqlSession sqlSession = getSqlSession();

        int result = menuDAO.updateMenu(sqlSession, menu);

        System.out.println("result: " + result);

        if (result > 0) {
            sqlSession.commit();
        } else {
            sqlSession.rollback();
        }

        sqlSession.close();

        return result > 0;
    }

    public boolean deleteMenu(int menuCode) {
        SqlSession sqlSession = getSqlSession();

        int result = menuDAO.deleteMenu(sqlSession, menuCode);

        if(result > 0) {
            sqlSession.commit();
        } else {
            sqlSession.rollback();
        }

        sqlSession.close();

        return result > 0;
    }
}
