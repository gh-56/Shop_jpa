package com.erser.jpashop.controller;

import com.erser.jpashop.dto.MemberDto;
import com.erser.jpashop.entity.Member;
import com.erser.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @PostMapping("/members/new")
    public String memberForm(MemberDto memberDto) {
        // Post - Redirect - Get pattern

        Member member = Member.createMember(memberDto, passwordEncoder);
        memberService.saveMember(member);
        return "redirect:/";
    }

    @GetMapping("/")
    public String main(){
        return "main";
    }
}