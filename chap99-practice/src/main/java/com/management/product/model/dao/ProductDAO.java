package com.management.product.model.dao;

import com.common.SearchCondition;
import com.management.product.model.dto.ProductDTO;

import java.util.List;
import java.util.Map;

public interface ProductDAO {
    List<ProductDTO> selectAllProductList();

    List<ProductDTO> selectProductByCondition(SearchCondition searchCondition);

    boolean insertProduct(ProductDTO product);

    boolean updateProduct(ProductDTO product);

    boolean deleteProduct(Map<String, String> parameter);


    // * 주석을 지우고 Mapper interface(= DAO class) 역할을 하도록 내용을 작성하세요.

}
