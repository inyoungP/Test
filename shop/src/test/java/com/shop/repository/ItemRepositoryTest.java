package com.shop.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import com.shop.entity.QItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.thymeleaf.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest //테스트 어노테이션
//기본적으로 읽어오는 것에서 먼저 실행되도록 설정해주는 것
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {

    //EntityManager를 빈으로 주입할 때 사용하는 어노테이션
    @PersistenceContext
    EntityManager em;

    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("상품 저장 테스트")   //실행되는 화면의 이름을 변경해주는 것
    public void createItemTest() {
        Item item = new Item();

        item.setItemName("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());

        //save
        Item savedItem = itemRepository.save(item);
        System.out.println(savedItem.toString());
    }

    public void createItemList() {
        for(int i=1;i<=10;i++) {
            Item item = new Item();

            item.setItemName("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());

            //save
            Item savedItem = itemRepository.save(item);
        }
    }
    public void createItemList2() {
        for(int i=1;i<=5;i++) {
            Item item = new Item();

            item.setItemName("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());

            //save
            Item savedItem = itemRepository.save(item);
        }

        for(int i=6;i<=10;i++) {
            Item item = new Item();

            item.setItemName("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
            item.setStockNumber(0);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());

            //save
            Item savedItem = itemRepository.save(item);
        }


    }

    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNameTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNameOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");
        for(Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 LessThan 테스트")
    public void findByPriceLessThanTest() {

        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThan(10005);
        for(Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 LessThanOrderBy 테스트")
    public void findByPriceLessThanOrderByPriceDescTest(){

        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
        for(Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("@Query를 이용한 상품 조회 테스트")
    public void findByItemDetailTest() {

        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세 설명");
        for(Item item : itemList) {
            System.out.println(item.toString());
        }
    }



    @Test
    @DisplayName("nativeQuery를 이용한 상품 조회 테스트")
    public void findByItemDetailByNativeTest() {

        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetailByNative("테스트 상품 상세 설명");
        for(Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("@QueryDSL 조회 테스트1")
    public void queryDslTest(){

        this.createItemList();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem qItem = QItem.item;

        JPAQuery<Item> query = queryFactory.selectFrom(qItem)
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.itemDetail.like("%" + "테스트 상품 상세 설명" + "%"))
                .orderBy(qItem.price.desc());

        List<Item> itemList = query.fetch();    //fetch에서 List타입에서 받아서 for문의 내용을 출력시켜준다.

        for(Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("@QueryDSL 조회 테스트2")
    public void queryDslTest2() {

        this.createItemList2();

        //쿼리에 들어갈 조건을 만들어주는 빌더
        //동적으로 조건을 만들기 위해 사용
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QItem item = QItem.item;

        //지정된 문자열을 포함하는 상품을 검색하도록 조건을 추가한다.
        String itemDetail = "테스트 상품 상세 설명";
        int price = 10003;
        String itemSellStat = "SELL";

        //지정된 문자열을 포함하는 상품을 검색하도록 조건을 추가한다.
        booleanBuilder.and(item.itemDetail.like("%" + itemDetail + "%"));
        booleanBuilder.and(item.price.gt(price));   //가격보다 큰것을 찾기 위해 내용 추가

        //판매 상태가 'SELL'인 경우 해당하는 상품을 검색하도록 조건을 추가
        //StringUtils : 문자열 직업과 관련된 기능을 모아둔 라이브러리
        if(StringUtils.equals(itemSellStat, ItemSellStatus.SELL)) {
            booleanBuilder.and(item.itemSellStatus.eq(ItemSellStatus.SELL));
        }

        //페이징 처리
        //PageRequest.of(조회할 페이지번호, 한 페이지당 조회할 데이터 개수)
        //페이지 번호는 0부터 시작
        Pageable pageable = PageRequest.of(0, 5);

        //Querydsl 조건을 이용하여 상품을 조회하고 결과를 페이징 처리한다.
        Page<Item> itemPagingResult = itemRepository.findAll(booleanBuilder, pageable);
        //상품의 목록을 출력 시켜준다.
        System.out.println("total elements : " + itemPagingResult.getTotalElements());

        List<Item> resultItemList = itemPagingResult.getContent();
        for (Item resultItem : resultItemList) {
            System.out.println(resultItem.toString());
        }

    }

}