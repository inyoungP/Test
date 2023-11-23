package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
//import javax.persistence.Entity;
//import javax.persistence.Table;
import java.time.LocalDateTime;

//@Entity : 클래스를 entity로 설정
//@Table : 엔티티와 매핑할 테이블 설정
@Entity
@Table(name = "item")
@Getter
@Setter
@ToString
public class Item {

    @Id //테이블의 기본키
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO) //자동으로 설정해주겠다.
    private Long id;    //상품코드
    // 필드가 연결이 된다.

    @Column(nullable = false, length = 50)  //공백불가에 50글자까지 지정하겠다.
    private String itemName;    //상품명

    @Column(name = "price", nullable = false)   //공백불가
    private int price;  //가격

    @Column(nullable = false)
    private int stockNumber;    //재고수량

    //@LOB : BLOB, CLOB 타입 매핑
    //BLOB, CLOB : 사이즈가 큰 데이터를 외부 파일로 저장하기 위한 데이터 타입
    @Lob    //많은 양의 데이터, 큰 데이터를 저장할 때 사용한다.
    @Column(nullable = false)
    private String itemDetail;     //상품 설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;  //상품 판매 상태

    private LocalDateTime regTime;  //등록시간

    private LocalDateTime updateTime;   //제품 수정 시간

}
