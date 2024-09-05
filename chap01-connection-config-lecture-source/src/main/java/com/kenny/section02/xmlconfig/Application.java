package com.kenny.section02.xmlconfig;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class Application {
    public static void main(String[] args) {


        try {
            String resource = "mybatis-config.xml"; // xml 파일을 읽어올 input stream
            InputStream inputStream = Resources.getResourceAsStream(resource);

            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);   // configuration을 전달했던 java 코드와 달리 xml 전달
            // sqlsessionFactory 객체는 builder를 통해 build 한다
            SqlSession sqlSession = sqlSessionFactory.openSession(false);
            /* selectOne : 조회 결과가 1행인 경우 사용하는 sqlSession의 메소드
            * mapper.xml 파일의 namespace와 select 태그의 id를 통해 수행 구문을 찾아온다. (namespace.id) */
            java.util.Date date = sqlSession.selectOne("mapper.selectDate");
            // namespace: mapper, id: selectDate
            // mapper는 기능에 따라 여러 개를 작성할 수 있기 때문에 namespace로 구분한다. sql 쿼리 역시 여러 개 일 수 있으므로 id로 구분한다.
            System.out.println("date = " + date);
            sqlSession.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
