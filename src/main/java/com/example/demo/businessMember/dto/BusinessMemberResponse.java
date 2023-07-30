package com.example.demo.businessMember.dto;

import com.example.demo.businessMember.entity.BusinessMember;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BusinessMemberResponse {

    final private long businessMemberId;
    final private String businessNumber;
    final private String name;
    final private String phoneNumber;

    public static BusinessMemberResponse from(BusinessMember businessMember) {
        return new BusinessMemberResponse(
                businessMember.getBusinessMemberId(),
                businessMember.getBusinessNumber(),
                businessMember.getName(),
                businessMember.getPhoneNumber()
        );
    }

}
