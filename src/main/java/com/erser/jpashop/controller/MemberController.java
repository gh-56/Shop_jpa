package com.erser.jpashop.controller;

import com.erser.jpashop.dto.MemberDto;
import com.erser.jpashop.entity.Member;
import com.erser.jpashop.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/members/new")
    public String memberForm(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "member/memberForm";
    }

    // 검증이 필요한 객체 앞에 valid 선언, BingdingResult 객체를 파라마터로 추가해서 결과를 담아준다.
    @PostMapping("/members/new")
    public String memberForm(@Valid MemberDto memberDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "member/memberForm";
        }
        // Post - Redirect - Get pattern
        try{
            Member member = Member.createMember(memberDto, passwordEncoder);
            memberService.saveMember(member);
        }catch(IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }
        return "redirect:/";
    }

    @GetMapping("/members/login")
    public String loginMember(){
        return "/member/memberLoginForm";
    }
    @GetMapping("/members/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디와 비밀번호를 확인해주세요");
        return "/member/memberLoginForm";
    }

    @GetMapping("/")
    public String main(){
        return "main";
    }
}