package com.shop.controller;

import com.shop.dto.MemberFormDTO;
import com.shop.entity.Member;
import com.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/login")
    public String loginMember(){
        return "/member/memberLoginForm";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model){
       model.addAttribute("loginErrorMsg", "이메일 또는 비밀번호를 확인하세요");
       return "/member/memberLoginForm";
    }

    @GetMapping(value = "/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDTO", new MemberFormDTO());
        return "member/memberForm";
    }

    //Valid : springMVC에서 검증 기능을 활성화하는 역할
    //BindingResult : 검증을 수행한 객체와 검증 결과에 대한 정보
    @PostMapping(value = "new")
    public String memberForm(@Valid MemberFormDTO memberFormDTO, BindingResult bindingResult, Model model){

        //오류가 있으면 회원가입 페이지로 이동
        if (bindingResult.hasErrors()){
            return "member/memberForm";
        }

        try {
            //회원가입
            Member member = Member.creatMember(memberFormDTO, passwordEncoder);
            memberService.saveMember(member);
        }catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";

        }


        return "redirect:/";
    }
}
