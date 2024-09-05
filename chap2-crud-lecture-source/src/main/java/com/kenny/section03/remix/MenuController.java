package com.kenny.section03.remix;

import java.util.List;
import java.util.Map;

// 사용자의 요청을 받고, 응답을 처리
// controller : 넘어온 값을 가공하고, 비즈니스 로직인 service layer로 연결함
// 비즈니스 로직이 처리되면 응답을 보내줌
public class MenuController {

    private final MenuService menuService;
    private final PrintResult printResult;

    public MenuController() {
        this.menuService = new MenuService();
        this.printResult = new PrintResult();
    }
    // 달리 넘어온 파라미터가 없으므로 조회만 한다
    public void selectAllMenu() {

        List<MenuDTO> menuList = menuService.selectAllMenu();

        if(menuList != null && !menuList.isEmpty()) {
            printResult.printMenuList(menuList);
        } else {
            printResult.printErrorMessage("selectList");
        }
    }

    // 넘어온 menuCode를 꺼내서 가공
    public void selectMenuByMenuCode(Map<String, String> parameter) {
        int menuCode = Integer.parseInt(parameter.get("menuCode"));

        // service 기능 수행 후 특정 메뉴 객체를 반환받아야 함
        // 특정 메뉴 코드로 메뉴를 받아온다
        MenuDTO menu = menuService.selectMenuByMenuCode(menuCode);

        if (menu != null) {
            printResult.printMenu(menu);
        } else {
            printResult.printErrorMessage("selectOne");
        }
    }
    public void registMenu(Map<String, String> parameter) {
        // 파라미터로 넘어온 값을 내가 원하는 타입으로 가공(,파싱)하는 게 controller의 역할
        MenuDTO menu = new MenuDTO();

        menu.setMenuName(parameter.get("menuName"));
        menu.setMenuPrice(Integer.parseInt(parameter.get("menuPrice")));
        menu.setCategoryCode(Integer.parseInt(parameter.get("categoryCode")));

        if (menuService.registMenu(menu)) {
            printResult.printSuccessMessage("insert");
        } else {
            printResult.printErrorMessage("insert");
        }
    }

    public void modifyMenu(Map<String, String> parameter) {

        MenuDTO menu = new MenuDTO();

        menu.setMenuCode(Integer.parseInt(parameter.get("menuCode")));
        menu.setMenuName(parameter.get("menuName"));
        menu.setMenuPrice(Integer.parseInt(parameter.get("menuPrice")));
        menu.setCategoryCode(Integer.parseInt(parameter.get("categoryCode")));

        if (menuService.modifyMenu(menu)) {
            printResult.printSuccessMessage("update");
        } else {
            printResult.printErrorMessage("update");
        }

    }

    public void deleteMenu(Map<String, String> parameter) {
        int menuCode = Integer.parseInt(parameter.get("menuCode"));

        if(menuService.deleteMenu(menuCode)) {
            printResult.printSuccessMessage("delete");
        } else {
            printResult.printErrorMessage("delete");
        }
    }
}
