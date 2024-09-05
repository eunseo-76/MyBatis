package com.management.product.model.dao;

import com.management.product.model.dto.ProductDTO;

import java.util.List;

public interface ProductDAO {

    // * 주석을 지우고 Mapper interface(= DAO class) 역할을 하도록 내용을 작성하세요.
    List<ProductDTO> selectAllProductList();
}
