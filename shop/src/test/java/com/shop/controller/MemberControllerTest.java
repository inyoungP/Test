package com.shop.controller;

import com.shop.dto.MemberFormDTO;
import com.shop.entity.Member;
import com.shop.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

@SpringBootTest
@AutoConfigureMockMvc//MVC테스트 프레임 워크
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class MemberControllerTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember(String email, String password){
        MemberFormDTO memberFormDTO = new MemberFormDTO();
        memberFormDTO.setEmail(email);
        memberFormDTO.setName("홍길동");
        memberFormDTO.setAddress("서울시 마포구 합정동");
        memberFormDTO.setPassword(password);
        Member member = Member.creatMember(memberFormDTO, passwordEncoder);
        return memberService.saveMember(member);
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginSuccessTest() throws Exception{
        String email = "test@eamil.com";
        String password = "1234";
        this.createMember(email, password);
        //SpringMVC :테스트 프레임워크에서 제공하는 메서드
        //mockMvc.perform() : httprequest를 모방하여 컨드롤러의 동작을 테스트하는데 사용

        mockMvc.perform(formLogin().userParameter("email")//로그인 처리 url설정
                .loginProcessingUrl("/members/login")//로그인에 사용될 사용자의 이메일
                .user(email).password(password))//로그인에 사용될 사용자의 비밀번호
                .andExpect(SecurityMockMvcResultMatchers.authenticated());

    }

}
