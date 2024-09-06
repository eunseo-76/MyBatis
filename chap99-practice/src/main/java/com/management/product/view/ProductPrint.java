package com.management.product.view;

import com.common.SearchCondition;
import com.management.product.model.dto.ProductDTO;

import java.util.List;
import java.util.SimpleTimeZone;

public class ProductPrint {

    // * 모든 출력은 System.out.println()을 통해 콘솔에 출력하는 것을 의미합니다.
    // 주석을 지우고 다음 네 가지 메소드를 작성하세요.

    public void printAllProductList(List<ProductDTO> allProductList) {

        // 1. 전체 조회한 목록 출력하는 메소드
        //    (조건) List<ProductDTO>로 받아온 데이터 목록을 전체 출력하세요.
        System.out.println("===== 전체 상품 목록 =====");
        for (ProductDTO productDTO : allProductList) {
            System.out.println(productDTO);
        }
    }

    public void printProductList(List<ProductDTO> productList, SearchCondition searchCondition) {

        // 2. 조건에 따라 조회한 목록을 출력하는 메소드
        //    (조건 1) SearchCondition 객체로 검색 조건이 무엇인지 출력하세요.
        //    (조건 2) List<ProductDTO>로 받아온 데이터 목록을 전체 출력하세요.
        System.out.println("===== 상품 목록 =====");
        for (ProductDTO productDTO : productList) {
            System.out.println(productDTO);
        }
    }

    public void printSuccessMessage(String successCode) {

        // 3. 성공메시지를 출력하는 메소드
        //    (조건) 성공코드를 전달받아 성공을 알리는 메시지를 출력하세요.
        System.out.println("***** Success *****");
        String successMessage = "";
        switch (successCode) {
            case "등록성공":
                successMessage = "제품 등록을 완료했습니다.";
            case "수정성공":
                successMessage = "제품 수정을 완료했습니다.";
            case "삭제성공":
                successMessage = "제품 삭제를 완료했습니다.";
        }
        System.out.println(successMessage);
    }

    public void printErrorMessage(String errorCode) {

        // 4. 에러메시지를 출력하는 메소드
        //    (조건) 에러코드를 전달받아 에러를 알리는 메시지를 출력하세요.
        System.out.println("***** Error *****");
        String errorMessage = "";
        switch (errorCode) {
            case "조회실패":
                errorMessage = "조회에 실패했습니다.";
            case "조회결과없음":
                errorMessage = "조회 결과가 없습니다.";
            case "등록실패":
                errorMessage = "제품 등록에 실패했습니다.";
            case "수정실패":
                errorMessage = "수정에 실패했습니다.";
            case "삭제성공":
                errorMessage = "삭제에 실패했습니다.";
        }
        System.out.println(errorMessage);

    }

}
