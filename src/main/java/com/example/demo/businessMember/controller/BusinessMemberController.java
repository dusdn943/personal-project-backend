package com.example.demo.businessMember.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.businessMember.dto.BusinessMemberLoginRequest;
import com.example.demo.businessMember.dto.BusinessMemberLoginResponse;
import com.example.demo.businessMember.dto.BusinessMemberRegisterRequest;
import com.example.demo.businessMember.dto.BusinessMemberResponse;
import com.example.demo.businessMember.service.BusinessMemberService;

@RestController
public class BusinessMemberController {

    private final BusinessMemberService businessMemberService;

    public BusinessMemberController(BusinessMemberService businessMemberService) {
        this.businessMemberService = businessMemberService;
    }

//    @GetMapping("/api/ping")
//    public String ping() {
//        return "pong";
//    }

    @Operation(summary = "사업자가 회원가입을 하는 API")
    @PostMapping("/business-member/signup")
    public void register(@RequestBody BusinessMemberRegisterRequest req) {
        System.out.println(req); 
        businessMemberService.register(req);
    }

    // 원래 로그인은 GET 이 맞다.
    // 근데 POST 로 하는 이유는, 로그인할 때 전달해야하는 정보가 민감하기 때문에,
    //  -> GET Request Body 를 사용하지않고,
    //  -> POST 를 사용해서 민감한 정보를 Request Body 에 넣으려 한다.
    @Operation(summary = "사업자 회원이 로그인하는 API")
    @PostMapping("/business-member/login")
    public BusinessMemberLoginResponse login(@RequestBody BusinessMemberLoginRequest req) {
        System.out.println(req);
        return businessMemberService.login(req);
    }

    @Operation(summary = "사업자 회원을 조회하는 API")
    @GetMapping("/business-member/{id}")
    public BusinessMemberResponse retrieveBusinessMemberInfo(@PathVariable(name = "id") long businessMemberId) {
        System.out.println(businessMemberId);
        return businessMemberService.retrieve(businessMemberId);
    }

}
