package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

//★ JpaRepository<엔티티 타입, 기본키 타입>
//알아서 페이지까지 처리해준다.
public interface ItemRepository extends JpaRepository<Item, Long>,
        QuerydslPredicateExecutor<Item> {

    //쿼리 메소드 : find+엔티티이름+By+변수이름
    //인터페이스에 추상메서드 만든 것임
    //itemName 또는 itemDetail
    List<Item> findByItemNameOrItemDetail(String itemName, String itemDetail);

    List<Item> findByPriceLessThan(Integer price);

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    @Query("select i from Item i where i.itemDetail like %:itemDetail% " +
            " order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

    //필드명이 같지 않으면 오류남
    @Query(value = "select * from Item i where i.item_Detail like %:itemDetail%" +
            " order by i.price desc", nativeQuery = true)
    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);




}
