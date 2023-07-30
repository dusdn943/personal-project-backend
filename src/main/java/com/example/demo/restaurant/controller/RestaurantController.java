package com.example.demo.restaurant.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.restaurant.dto.RegisterRestaurantRequest;
import com.example.demo.restaurant.dto.RestaurantResponse;
import com.example.demo.restaurant.service.RestaurantService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping("/restaurant")
    public void registerRestaurant(@RequestBody RegisterRestaurantRequest req) {
        System.out.println(req);
        restaurantService.register(req);
    }

    @DeleteMapping("/restaurant/{id}")
    public void deleteRestaurant(@PathVariable(name = "id") long restaurantId) {
        System.out.println("restaurantId = " + restaurantId);
        restaurantService.delete(restaurantId);
    }

    @GetMapping("/restaurants/business-member/{id}")
    public List<RestaurantResponse> retrieveRestaurantByBusinessMember(
            @PathVariable(name = "id") long businessMemberId
    ) {
        System.out.println("businessMemberId = " + businessMemberId);
        return restaurantService.retrieveAll(businessMemberId);
    }
}

