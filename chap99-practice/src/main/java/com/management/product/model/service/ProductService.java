package com.management.product.model.service;

import com.common.SearchCondition;
import com.management.product.model.dao.ProductDAO;
import com.management.product.model.dto.ProductDTO;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

import static com.common.Template.getSqlSession;

public class ProductService {

    // * 주석을 지우고 Service 역할에 해당하는 메소드를 작성하세요.

    // 1. 자주 사용할 DAO 객체를 선언하세요.
    private ProductDAO productDAO;

    public List<ProductDTO> selectAllProductList() {
        SqlSession sqlSession = getSqlSession();
        productDAO = sqlSession.getMapper(ProductDAO.class);

        List<ProductDTO> productList = productDAO.selectAllProductList();
        sqlSession.close();

        return productList;


        // 2. 전체 제품 목록을 조회하는 로직을 작성하세요.
        // 　　아래 작성된 return null은 과제 툴 오류를 제거하고자 임의 작성하였으니 지우고 로직을 작성하세요.

    }

    public List<ProductDTO> selectProductByCondition(SearchCondition searchCondition) {
        SqlSession sqlSession = getSqlSession();
        productDAO = sqlSession.getMapper(ProductDAO.class);
        // 3. 조건에 따른 제품 목록을 조회하는 로직을 작성하세요.
        // 　　아래 작성된 return null은 과제 툴 오류를 제거하고자 임의 작성하였으니 지우고 로직을 작성하세요.

        List<ProductDTO> productList = productDAO.selectProductByCondition(searchCondition);
        return productList;


    }

    public boolean registNewProduct(ProductDTO product) {
        SqlSession sqlSession = getSqlSession();
        productDAO = sqlSession.getMapper(ProductDAO.class);

        // 4. 제품 정보를 등록하는 로직을 작성하세요.
        // 　　아래 작성된 return false 과제 툴 오류를 제거하고자 임의 작성하였으니 지우고 로직을 작성하세요.
        boolean result = productDAO.insertProduct(product);

        if (result) {
            sqlSession.commit();
        } else {
            sqlSession.rollback();
        }
        sqlSession.close();

        return result;

    }

    public boolean modifyProductInfo(ProductDTO product) {
        SqlSession sqlSession = getSqlSession();
        productDAO = sqlSession.getMapper(ProductDAO.class);

        boolean result = productDAO.updateProduct(product);

        if (result) {
            sqlSession.commit();
        } else {
            sqlSession.rollback();
        }
        sqlSession.close();

        return result;

        // 5. 제품 정보를 수정하는 로직을 작성하세요.
        // 　　아래 작성된 return false 과제 툴 오류를 제거하고자 임의 작성하였으니 지우고 로직을 작성하세요.

    }

    public boolean deleteProduct(Map<String, String> parameter) {
        SqlSession sqlSession = getSqlSession();
        productDAO = sqlSession.getMapper(ProductDAO.class);

        boolean result = productDAO.deleteProduct(parameter);

        if (result) {
            sqlSession.commit();
        } else {
            sqlSession.rollback();
        }
        sqlSession.close();

        return result;

        // 6. 제품 정보를 삭제하는 로직을 작성하세요.
        // 　　아래 작성된 return false 과제 툴 오류를 제거하고자 임의 작성하였으니 지우고 로직을 작성하세요.
    }
}
