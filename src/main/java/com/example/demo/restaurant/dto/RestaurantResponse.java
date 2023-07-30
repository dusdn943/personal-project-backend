package com.example.demo.restaurant.dto;

import com.example.demo.restaurant.entity.Restaurant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestaurantResponse {

    final private long restaurantId;
    final private String name;
    final private String address;
    final private String phoneNumber;

    public static RestaurantResponse from(Restaurant restaurant) {
        return new RestaurantResponse(
                restaurant.getRestaurantId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getPhoneNumber()
        );
    }
}
