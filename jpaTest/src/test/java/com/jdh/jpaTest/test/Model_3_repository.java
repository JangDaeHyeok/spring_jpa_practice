package com.jdh.jpaTest.test;

import com.jdh.jpaTest.model.entity.Address;
import com.jdh.jpaTest.model.entity.Member;
import com.jdh.jpaTest.model.entity.Orders;
import com.jdh.jpaTest.model.enums.OrderStatus;
import com.jdh.jpaTest.model.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * repository test code
 */
// jpa data test annotation
@DataJpaTest
// 실제 db 사용시
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class Model_3_repository {

    @Autowired MemberRepository memberRepository;
    @Autowired OrdersRepository ordersRepository;
    @Autowired OrderItemRepository orderItemRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired DeliveryRepository deliveryRepository;
    @Autowired CategoryRepository categoryRepository;
    @Autowired CategoryItemRepository categoryItemRepository;

    @Test
    @Order(1)
    @DisplayName("회원정보_주문정보_연관관계_저장")
    // 테스트 코드는 한글 메소드명 가능
    void 회원정보_주문정보_연관관계_저장() {
        // member entity
        Member member = new Member();
        member.setName("장대혁");
        Address address = Address.builder()
                .city("춘천")
                .street("장학리")
                .zipcode("01010")
                .build();
        member.setAddress(address);

        // member insert
        memberRepository.save(member);

        // order entity
        Orders orders = new Orders();
        orders.setOrderdate(new Date());
        orders.setStatus(OrderStatus.ORDER);
        orders.setMember(member);

        // order insert
        ordersRepository.save(orders);

        // then member
        assertThat(member).isNotNull();
        assertThat(member.getName()).isEqualTo("장대혁");
        assertThat(member.getAddress().getCity()).isEqualTo("춘천");
        assertThat(member.getAddress().getStreet()).isEqualTo(("장학리"));
        assertThat(member.getAddress().getZipcode()).isEqualTo("01010");

        // then orders
        assertThat(orders).isNotNull();
        assertThat(orders.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(orders.getMember().getName()).isEqualTo("장대혁");
    }

}
