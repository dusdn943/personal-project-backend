package com.example.demo.restaurant.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.restaurant.dto.RegisterRestaurantRequest;
import com.example.demo.restaurant.dto.RestaurantDetailResponse;
import com.example.demo.restaurant.dto.RestaurantResponse;
import com.example.demo.restaurant.service.RestaurantService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Operation(summary = "전체 맛집 목록을 조회하는 API")
    @GetMapping("/restaurants")
    public List<RestaurantResponse> retrieveAllRestaurants() {
        return restaurantService.retrieveAll();
    }

    // API : 사용자는 Restaurant 의 상세정보를 확인할 수 있다.
    @Operation(summary = "레스토랑 상세 정보를 확인하는 API")
    @GetMapping("/restaurant/{id}") // 이런 방식의 데이터 전달 방식 => Path Variable
    public RestaurantDetailResponse retrieveRestaurantDetail(@PathVariable(name = "id") long restaurantId) {
        System.out.println("restaurantId = " + restaurantId);
        return restaurantService.retrieveDetail(restaurantId);
    }

    @Operation(summary = "사업자 회원에 대한 모든 레스토랑 정보를 확인하는 API")
    @GetMapping("/restaurants/business-member/{id}")
    public List<RestaurantResponse> retrieveRestaurantByBusinessMember (
            @PathVariable(name = "id") long businessMemberId
    ) {
        System.out.println("businessMemberId = " + businessMemberId);
        return restaurantService.retrieveAll(businessMemberId);
    }

    // 맛집 등록을 수정해야함 (정보 추가)
    //  -> 영업일(business_days), 영업시간(opening_at, closing_at), 시간당 예약가능 인원(reservation_capacity)
    @Operation(summary = "레스토랑 상세 정보 등록하는 API")
    @PostMapping("/restaurant")
    public void registerRestaurant(@RequestBody RegisterRestaurantRequest req) {
        System.out.println(req);
        restaurantService.register(req);
    }

    @Operation(summary = "레스토랑을 삭제하는 API")
    @DeleteMapping("/restaurant/{id}")
    public void deleteRestaurant(@PathVariable(name = "id") long restaurantId) {
        System.out.println("restaurantId = " + restaurantId);
        restaurantService.delete(restaurantId);
    }

}

