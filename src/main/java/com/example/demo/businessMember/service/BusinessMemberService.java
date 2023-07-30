package com.example.demo.businessMember.service;

import com.example.demo.businessMember.dto.BusinessMemberRegisterRequest;
import com.example.demo.businessMember.entity.BusinessMember;
import com.example.demo.businessMember.repositroy.BusinessMemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BusinessMemberService {

    private final BusinessMemberRepository businessMemberRepository;

    public BusinessMemberService(BusinessMemberRepository businessMemberRepository) {
        this.businessMemberRepository = businessMemberRepository;
    }

    public void register(BusinessMemberRegisterRequest req) {
        Optional<BusinessMember> businessMember = businessMemberRepository.findByEmail(req.getEmail());
        if (businessMember.isPresent()) {
            throw new RuntimeException("이미 가입되어있는 이메일입니다!");
        }

        businessMemberRepository.save(req.toEntity());
    }

}
