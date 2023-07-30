package com.example.demo.businessMember.controller;

import com.example.demo.businessMember.dto.BusinessMemberRegisterRequest;
import com.example.demo.businessMember.service.BusinessMemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BusinessMemberController {

    private final BusinessMemberService businessMemberService;

    public BusinessMemberController(BusinessMemberService businessMemberService) {
        this.businessMemberService = businessMemberService;
    }

    @GetMapping("/api/ping")
    public String ping() {
        return "pong";
    }

    @PostMapping("/business-member/signup")
    public void register(@RequestBody BusinessMemberRegisterRequest req) {
        System.out.println(req);
        businessMemberService.register(req);
    }
}
