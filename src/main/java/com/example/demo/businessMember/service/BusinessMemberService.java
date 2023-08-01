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
        // 1. 사용자로부터 받은 email 로 데이터베이스에서 사용자정보 조회한다.
        Optional<BusinessMember> memberOptional = businessMemberRepository.findByEmail(req.getEmail());
        if (memberOptional.isEmpty()) {
            throw new RuntimeException("잘못된 Email 입니다!");
        }

        // 2. 조회된 사용자정보에서 패스워드가 일치하는지 확인한다.
        BusinessMember member = memberOptional.get();
        if (!member.validPassword(req.getPassword())) { // 일치하면 true, 일치하지 않으면 false
            throw new RuntimeException("잘못된 Password 입니다!");
        }
        
        // 3. AccessToken 만들어서 응답으로 내려줘야한다.
        //    AccessToken 은 JWT(Json Web Token) 표준을 가지고 토큰을 만든다.
        String accessToken = jwtHelper.generateAccessToken(MemberType.BUSINESS, member.getBusinessMemberId()); // PK
        return new BusinessMemberLoginResponse(accessToken);
    }

    public BusinessMemberResponse retrieve(long businessMemberId) {
        // 1. BusinessMemberId 로 데이터베이스에서 사업자 정보를 조회한다.
        return businessMemberRepository.findById(businessMemberId)

                // 2. 조회된 사업자 정보를 BusinessMemberResponse 로 모양을 바꿔서 반환한다.
                .map(BusinessMemberResponse::from)
                .orElseThrow();
    }
}
