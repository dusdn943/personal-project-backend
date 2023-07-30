package com.example.demo.restaurant.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.restaurant.entity.Restaurant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestaurantDetailResponse {
    final private long restaurantId;
    final private String name;
    final private String address;
    final private String phoneNumber;
    final private List<DayOfWeek> businessDays;
    final private LocalTime openingTime;
    final private LocalTime closingTime;
    final private int reservationCapacity;

    // from, of 이런애들이 A 를 가지고 해당 객체를 만들어내는 일을 한다.
    // Restaurant Entity 객체를 가지고 RestaurantDetailResponse 객체를 만드는 일을 해야한다.
    public static RestaurantDetailResponse from(Restaurant restaurant) {
        return new RestaurantDetailResponse(
                restaurant.getRestaurantId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getPhoneNumber(),
                restaurant.getBusinessDays(),
                restaurant.getOpeningTime(),
                restaurant.getClosingTime(),
                restaurant.getReservationCapacity()
        );
    }
}
