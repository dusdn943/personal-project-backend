package com.example.demo.restaurant.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<DayOfWeek> businessDays;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private int reservationCapacity;

    public Restaurant toEntity() {
        // List<DayOfWeek> -> String 으로 만들어준다.
        // 리스트 요소(Element) 사이 사이에 콤마 찍어준다.
        //  [A, B, C] -> A,B,C

        return new Restaurant(
                name,
                address,
                contents,
                phoneNumber,
                businessDays.stream()
                        .map(Enum::toString)
                        .collect(Collectors.joining(",")),
                openingTime,
                closingTime,
                reservationCapacity
        );
    }
}
