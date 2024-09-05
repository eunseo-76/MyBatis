package com.kenny.section01.xmlmapper;

import java.util.List;

public class CategoryAndMenuDTO {
    // 하나의 category 객체가 있다.
    // 한식-4-1 에 소속된 메뉴의 목록을 알고 싶다.
    // sql 쿼리의 결과와 java 객체의 형태가 다를 때 resultMap을 사용한다.
    private int categoryCode;
    private String categoryName;
    private Integer refCategoryCode;    // int(기본자료형): null x 값이 없으면 0, Integer(wrapper): null 가능. null 값 표현하기 위해 Integer
    private List<MenuDTO> menuList;

    public CategoryAndMenuDTO() {
    }

    public CategoryAndMenuDTO(int categoryCode, String categoryName, Integer refCategoryCode, List<MenuDTO> menuList) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.refCategoryCode = refCategoryCode;
        this.menuList = menuList;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getRefCategoryCode() {
        return refCategoryCode;
    }

    public void setRefCategoryCode(Integer refCategoryCode) {
        this.refCategoryCode = refCategoryCode;
    }

    public List<MenuDTO> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuDTO> menuList) {
        this.menuList = menuList;
    }

    @Override
    public String toString() {
        return "CategoryAndMenuDTO{" +
                "categoryCode=" + categoryCode +
                ", categoryName='" + categoryName + '\'' +
                ", refCategoryCode=" + refCategoryCode +
                ", menuList=" + menuList +
                '}';
    }
}
