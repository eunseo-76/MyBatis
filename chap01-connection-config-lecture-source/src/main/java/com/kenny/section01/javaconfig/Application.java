package com.kenny.section01.javaconfig;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

public class Application {

    private static String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static String URL = "jdbc:mysql://localhost:3306/menudb";
    private static String USER = "swcamp";
    private static String PASSWORD = "swcamp";

    public static void main(String[] args) {

        /* DB 접속에 관한 설정 */
        // JdbcTransaationFactory : 수동 커밋, ManagedTransactionFactory : 자동 커밋
        Environment environment = new Environment(
                "dev",  // 환경 정보 이름 - development 줄임말, 개발환경에서 사용한다는 의미로 지음
                new JdbcTransactionFactory(),   // 트랜잭션 매니저 종류 (JDBC or MANAGERD)
                new PooledDataSource(
                        DRIVER, URL, USER, PASSWORD // Connection 생성 시 사용할 DB 연결 정보
                )  // Connection Pool 사용 유무 (Pooled or UnPooled)
        );

        /* 생성한 환경 설정 정보로 MyBatis 설정 객체 생성 */
        Configuration configuration = new Configuration(environment);

        /* 설정 객체와 매퍼(Mapper 등록) */
        // Mapper: sql 쿼리를 정의하고 실행하기 위한 인터페이스
        configuration.addMapper(Mapper.class);

        /* SqlSessionFactory : SqlSession 객체를 생성하기 위한 팩토리 역할의 인터페이스
        * SqlSessionFactoryBuilder : SqlSessionFactory 인터페이스 타입의 하위 구현체를 생성하기 위한 빌드 역할
        *  */
        // SqlSessionFactoryBuilder는 SqlSessionFactory 타입의 객체를 만듦

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        // SqlSessionFactory는 SqlSession객체를 만들어냄.

        /* openSession() : SqlSession 타입의 인터페이스를 반환하는 메소드로, boolean 타입으로 인자 전달
        * - false : DML 수행 후 auto commit 옵션을 false 로 저장 */
        SqlSession sqlSession = sqlSessionFactory.openSession(false);   // db와 연동할 커넥션이 만들어지는 것과 유사. mybaits에서는 커넥션을 sqlsession 이라는 객체로 다룸.

        /* getMapper() : Configuration에 등록된 매퍼를 동일 타입에 대해 반환 */
        Mapper mapper = sqlSession.getMapper(Mapper.class);

        /* Mapper 인터페이스에 작성된 메소드를 호출하여 select 쿼리 실행 후 반환값 출력 */
        java.util.Date date = mapper.selectDate();
        System.out.println("date = " + date);

        /* SqlSession 객체 반납 */
        sqlSession.close();

    }
}
// mybatis의 쿼리문을 사용하기 위한 타입 : sqlsession 타입
// sqlsession은 sqlsession factory를 통해 객체를 만든다. 특정 연결을 하고 싶을 때 마다 sqlsession 객체를 만들어야 함.
// sqlsession 객체를 만드려면 builder가 필요하다