package com.shop.entity;

import com.shop.constant.Role;
import com.shop.dto.MemberFormDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity // 엔티티 클래스임을 지정
@Table(name="member") //테이블 이름 지정
@Getter
@Setter
@ToString
public class Member {

    @Id //기본키
    @Column(name="member_id") //테이블의 열과 매핑
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @Column(unique = true) //유일한 값으로 설정
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING) //멤버변수가 Enum(열거형)타입일 때 사용
    private Role role;

    //Member 엔티티를 생성하는 메서드
    public static Member creatMember(MemberFormDTO memberFormDTO, PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setName(memberFormDTO.getName());
        member.setEmail(memberFormDTO.getEmail());
        member.setAddress(memberFormDTO.getAddress());
        String password = passwordEncoder.encode(memberFormDTO.getPassword());
        member.setPassword(password);
        member.setRole(Role.ADMIN);
        return member;
    }
}
