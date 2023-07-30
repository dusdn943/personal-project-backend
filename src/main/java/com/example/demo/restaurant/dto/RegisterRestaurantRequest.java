package com.example.demo.restaurant.dto;

import com.example.demo.restaurant.entity.Restaurant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class RegisterRestaurantRequest {
    private long businessMemberId;
    private String name;
    private String address;
    private String contents;
    private String phoneNumber;

    public Restaurant toEntity() {
        return new Restaurant(name, address, contents, phoneNumber);
    }
}
