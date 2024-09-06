package com.ohgiraffers.transactional.section01.annotation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    /* sqlSession.getMapper() 대신 @Mapper 가 달려 하위 구현체가 관리 되면 의존성 주입 받을 수 있다. */
    private final MenuMapper menuMapper;
    private final OrderMapper orderMapper;

    public OrderService(MenuMapper menuMapper, OrderMapper orderMapper) {
        this.menuMapper = menuMapper;
        this.orderMapper = orderMapper;
    }
    /* 전파행위 옵션
     *  REQUIRED : 진행 중인 트랜잭션이 있으면 현재 메소드를 그 트랜잭션에서 실행하되 그렇지 않은 경우 새 트랜잭션을 시작해서 실행한다.
     *  REQUIRED_NEW : 항상 새 트랜잭션을 시작해 메소드를 실행하고 진행중인 트랜잭션이 있으면 잠시 중단시킨다.
     *  SUPPORTS : 진행중인 트랜잭션이 있으면 현재 메소드를 그 트랜잭션 내에서 실행하되, 그렇지 않을 경우 트랜잭션 없이 실행한다.
     *  NOT_SUPPORTED : 트랜잭션 없이 현재 메소드를 실행하고 진행중인 트랜잭션이 있으면 잠시 중단한다
     *  MANDATORY : 반드시 트랜잭션을 걸고 현재 메소드를 실행하되 진행중인 트랜잭션이 있으면 예외를 던진다.
     *  NEVER : 반드시 트랜잭션 없이 현재 메소드를 실행하되 진행중인 트랜잭션이 있으면 예외를 던진다.
     *  NESTED : 진행중인 트랜잭션이 있으면 현재 메소드를 이 트랜잭션의 중첩트랜잭션 내에서 실행한다. 진행중인 트랜잭션이 없으면 새 트랜잭션을
     *           실행한다.
     *           배치 실행 도중 처리 할 업무가 백만개라고 하면 10만개씩 끊어서 커밋하는 경우 중간에 잘못 되어도 중첩 트랜잭션을 롤백하면 전체가
     *           아닌 10만개만 롤백된다.
     *           세이브포인트를 이용하는 방식이다. 따라서 세이브포인트를 지원하지 않는 경우 사용 불가능하다.
     */

    /* 격리레벨 (동시성)
     *  DEFAULT : DB의 기본 격리 수준을 사용한다. 대다수는 READ_COMMITTED가 기본 격리 수준이다.
     *  READ_UNCOMMITTED : 다른 트랜젝션이 아직 커밋하지 않은 값을 다른 트랜젝션이 읽을 수 있다.
     *                     따라서 오염된 값을 읽거나, 재현 불가능한 값 읽기, 허상 읽기 등의 문제가 발생할 수 있다.(모든 동시성 문제 발생)
     *  READ_COMMITTED : 트랜젝션이 다른 트랜젝션에서 커밋한 값만 읽을 수 있다.
     *                   오염된 값 읽기 문제는 해결되지만 재현 불가능한 값 읽기 및 허상읽기는 여전히 발생할 수 있다.(다른 로우 수정 및 추가는 막을 수 없다.)
     *  REPEATABLE_READ : 트랜젝션이 어떤 필드를 여러 번 읽어도 동일한 값을 읽도록 보장한다.
     *                    트랜젝션이 지속되는 동안에는 다른 트랜젝션이 해당 필드를 변경할 수 없다.
     *                    오염된 값 읽기, 재현 불가능한 값 읽기는 해결되지만 허상읽기는 여전히 발생할 수 있다.(다른 로우 추가는 막을 수 없다.)
     *  SERIALIZABLE : 트랜젝션이 테이블을 여러 번 읽어도 정확히 동일한 로우를 읽도록 보장한다. 트랜젝션이 지속되는 동안에는
     *                 다른 트랜젝션이 해당 테이블에 삽입, 수정, 삭제를 할 수 없다.
     *                 동시성 문제는 모두 해소되지만 성능은 현저하게 떨어진다. (모든 동시성 문제는 막을 수 있다.)
     * 설명.
     *  오염된 값 : 하나의 트랜젝션이 데이터를 변경 후 잠시 기다리는 동안 다른 트랜젝션이 데이터를 읽게 되면,
     *           격리레벨이 READ_UNCOMMITTED인 경우 아직 변경 후 커밋하지 않은 재고값을 그대로 읽게 된다.
     *           그러나 처음 트랜젝션이 데이터를 롤백하게 되면 다른 트랜젝션이 읽은 값은 더 이상 유효하지 않은 일시적인 값이 된다.
     *           이것을 오염된 값라고 한다.
     *  재현 불가능한 값 읽기 : 처음 트랜젝션이 데이터를 수정하면 수정이 되고 아직 커밋되지 않은 로우에 수정 잠금을 걸어둔 상태에다.
     *                       결국 다른 트랜젝션은 이 트랜젝션이 커밋 혹은 롤백 되고 수정잠금이 풀릴 때까지 기다렸다가 읽을 수 밖에 없게 된다.
     *                       하지만 다른 로우에 대해서는 또 다른 트랜젝션이 데이터를 수정하고 커밋을 하게 되면
     *                       가장 처음 동작한 트랜젝션이 데이터를 커밋하고 다시 조회를 한 경우 처음 읽은 그 값이 아니게 된다.
     *                       이것이 재현 불가능한 값이라고 한다.
     *  허상 읽기 : 처음 트랜젝션이 테이블에서 여러 로우를 읽은 후 이후 트랜젝션이 같은 테이블의 로우를 추가하는 경우
     *             처음 트랜젝션이 같은 테이블을 다시 읽으면 자신이 처음 읽었을 때와 달리 새로 추가 된 로우가 있을 것이다.
     *             이것을 허상 읽기라고 한다. (재현 불가능한 값 읽기와 유사하지만 허상 읽기는 여러 로우가 추가되는 경우를 말한다.)
     */

    /* Service 계층부터 개발 할 경우 사용자 입력 값이 어떻게 DTO or Map으로 묶여서
    * Controller 계층에서 넘어오는지를 충분히 고민 한 후 매개변수를 작성한다. */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void registNewOrder(OrderDTO orderDTO) {

        /* 1. 주문한 메뉴 코드 추출 */
        List<Integer> menuCodes = orderDTO.getOrderMenus()
                .stream()
                .map(OrderMenuDTO::getMenuCode)
                .toList();

        Map<String, List<Integer>> map = new HashMap<>();
        map.put("menuCodes", menuCodes);

        /* 2. 주문한 메뉴 별로 Menu 엔터티에 담아서 조회(select) 해오기 - 부가적인 메뉴의 정보 추출 (단가 등) */
        List<Menu> menus = menuMapper.selectMenuByMenuCodes(map);
        menus.forEach(System.out::println);

        /* 3. 해당 주문건의 총 합계를 계산 */
        int totalOrderPrice = calcTotalOrderPrice(orderDTO.getOrderMenus(), menus);
        System.out.println("total order price: " + totalOrderPrice);

        /* 4. tbl_order insert */
        /* OrderDTO(dto) -> Order(entity) */
        Order order = new Order(orderDTO.getOrderDate(), orderDTO.getOrderTime(), totalOrderPrice);
        orderMapper.registOrder(order);
        System.out.println("orderCode : " + order.getOrderCode());

        /* 5. tbl_order_menu insert */
        for(OrderMenuDTO orderMenuDTO : orderDTO.getOrderMenus()) {
            /* OrderMenuDTO(dto) -> OrderMenu(entity) */
            OrderMenu orderMenu = new OrderMenu(order.getOrderCode(), orderMenuDTO.getMenuCode(), orderMenuDTO.getOrderAmount());
            orderMapper.registOrderMenu(orderMenu);
        }
        System.out.println(order);
    }

    private int calcTotalOrderPrice(List<OrderMenuDTO> orderMenus, List<Menu> menus) {
        int totalOrderPrice = 0;
        for(int i = 0; i < orderMenus.size(); i++) {
            totalOrderPrice += menus.get(i).getMenuPrice() * orderMenus.get(i).getOrderAmount();
        }
        return totalOrderPrice;
    }

}
