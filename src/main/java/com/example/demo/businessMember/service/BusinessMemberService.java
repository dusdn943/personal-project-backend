package com.example.demo.businessMember.service;

import java.util.Optional;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.example.demo.businessMember.dto.BusinessMemberLoginRequest;
import com.example.demo.businessMember.dto.BusinessMemberLoginResponse;
import com.example.demo.businessMember.dto.BusinessMemberRegisterRequest;
import com.example.demo.businessMember.dto.BusinessMemberResponse;
import com.example.demo.businessMember.entity.BusinessMember;
import com.example.demo.businessMember.repositroy.BusinessMemberRepository;
import com.example.demo.common.helper.JwtHelper;
import com.example.demo.common.type.MemberType;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class BusinessMemberService {

    private final BusinessMemberRepository businessMemberRepository;
    private final JwtHelper jwtHelper;


    public void register(BusinessMemberRegisterRequest req) {
        // 1. 해당 email 을 조건으로 하는 사업자가 있는지 DB 에서 조회한다. 있으면 에러를 발생시킨다.
        Optional<BusinessMember> businessMember = businessMemberRepository.findByEmail(req.getEmail());
        if (businessMember.isPresent()) {
            throw new RuntimeException("이미 가입되어있는 이메일입니다!");
        }

        // 2. 사업자 정보를 DB 에 저장한다.
        //    JPA Repository 는 저장할때 무조건 Entity 여야만한다.
        businessMemberRepository.save(req.toEntity());
    }

    public BusinessMemberLoginResponse login(BusinessMemberLoginRequest req) {
        return businessMemberRepository.findByEmail(req.getEmail())
                .map(member -> {
                    if (!member.getPassword().equals(req.getPassword())) {
                        throw new RuntimeException("잘못된 Password 입니다!");
                    }
                    String accessToken = jwtHelper.generateAccessToken(MemberType.BUSINESS, member.getBusinessMemberId());
                    return new BusinessMemberLoginResponse(accessToken);
                })
                .orElseThrow(() -> new RuntimeException("잘못된 Email 입니다!"));
    }

    public BusinessMemberResponse retrieve(long businessMemberId) {
        // 1. BusinessMemberId 로 데이터베이스에서 사업자 정보를 조회한다.
        return businessMemberRepository.findById(businessMemberId)

                // 2. 조회된 사업자 정보를 BusinessMemberResponse 로 모양을 바꿔서 반환한다.
                .map(BusinessMemberResponse::from)
                .orElseThrow();
    }
}
