package com.kenny.common;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class Template {
    /* SqlSessionFactory : 애플리케이션을 실행하는 동안 존재해야하며 여러 차례 다시 빌드하지 않는 것이 좋은 형태이다.
     * => singleon (오로지 1개의 객체만 생성) 패턴을 이용하는 것이 가장 좋다.
     *  */
    private static SqlSessionFactory sqlSessionFactory;

    /* 한 번의 요청 당 1개의 SqlSession 객체가 필요하므로 필요 시 호출한 메소드 */
    // SqlSession 객체가 필요할 때마다 이 메소드를 호출한다.
    public static SqlSession getSqlSession() {

        if (sqlSessionFactory == null) {
            try {
                String resource = "config/mybatis-config.xml";
                InputStream inputStream = Resources.getResourceAsStream(resource);
                /* SqlSessionFactoryBuilder : SqlSessionFactory를 생성한 뒤 유지할 필요가 없으므로
                 * 메소드 스코프로 만들면 된다. */
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        /* SqlSession : Thread에 안전하지 않고 공유되지 않아야 하므로 요청 시마다 생성한다. */
        // thread에 안전하지 않다 : 생성된 객체가 애플리케이션 단위로 공유되는 게 아니다
        return sqlSessionFactory.openSession(false);
    }
}
