package com.example.demo.restaurant.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.businessMember.entity.BusinessMember;
import com.example.demo.businessMember.repositroy.BusinessMemberRepository;
import com.example.demo.restaurant.dto.RegisterRestaurantRequest;
import com.example.demo.restaurant.dto.RestaurantResponse;
import com.example.demo.restaurant.entity.Restaurant;
import com.example.demo.restaurant.repository.RestaurantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final BusinessMemberRepository businessMemberRepository;

    @Transactional
    public void register(RegisterRestaurantRequest req) {
        // 똑같은 주소로 2개 등록하면 안되서 예외처리 해주기.
        Optional<Restaurant> restaurant = restaurantRepository.findByAddress(req.getAddress());
        if (restaurant.isPresent()) {
            throw new RuntimeException("동일 주소에 등록된 맛집이 있습니다.");
        }

        BusinessMember businessMember = businessMemberRepository.findById(req.getBusinessMemberId())
                .orElseThrow(() -> new RuntimeException("가입되어있지 않은 사업자정보입니다."));
        Restaurant createdRestaurant = restaurantRepository.save(req.toEntity());
        createdRestaurant.setBusinessMember(businessMember);
    }

    public void delete(long id) {
        restaurantRepository.deleteById(id);
    }

    public List<RestaurantResponse> retrieveAll(long businessMemberId) {
        return restaurantRepository.findAllByBusinessMemberId(businessMemberId).stream()
                .map(RestaurantResponse::from)
                .collect(Collectors.toList());
    }

}
