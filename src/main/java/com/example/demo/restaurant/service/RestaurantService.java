package com.example.demo.restaurant.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.businessMember.entity.BusinessMember;
import com.example.demo.businessMember.repositroy.BusinessMemberRepository;
import com.example.demo.reservation.entity.Reservation;
import com.example.demo.reservation.service.ReservationService;
import com.example.demo.restaurant.dto.RegisterRestaurantRequest;
import com.example.demo.restaurant.dto.RestaurantDetailResponse;
import com.example.demo.restaurant.dto.RestaurantResponse;
import com.example.demo.restaurant.entity.Restaurant;
import com.example.demo.restaurant.repository.RestaurantRepository;
import com.example.demo.review.dto.ReviewResponse;
import com.example.demo.review.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final ReviewService reviewService;
    private final RestaurantRepository restaurantRepository;
    private final ReservationService reservationService;
    private final BusinessMemberRepository businessMemberRepository;

    public List<RestaurantResponse> retrieveAll() {
        return restaurantRepository.findAll().stream()
                .map(restaurant -> {
                    double starCount = reviewService.calculateStarCountAverage(restaurant);
                    return RestaurantResponse.from(restaurant, starCount);
                })
                .collect(Collectors.toList());
    }

    public List<RestaurantResponse> retrieveAll(long businessMemberId) {
        return restaurantRepository.findAllByBusinessMemberId(businessMemberId).stream()
                .map(restaurant -> {
                    double starCount = reviewService.calculateStarCountAverage(restaurant);
                    return RestaurantResponse.from(restaurant, starCount);
                })
                .collect(Collectors.toList());
    }

    public RestaurantDetailResponse retrieveDetail(long restaurantId) {
        // 1. 사용자로부터 받은 RestaurantId 로 데이터베이스에서 레스토랑 정보를 조회한다.
        return restaurantRepository.findById(restaurantId) // Optional

                // 2. 조회한 레스토랑 정보를 RestaurantDetailResponse 모양으로 바꿔서 반환한다.
                .map(restaurant -> {
                    double starCount = reviewService.calculateStarCountAverage(restaurant);
                    List<ReviewResponse> reviewList = reviewService.retrieveAll(restaurant.getRestaurantId());
                    return RestaurantDetailResponse.from(restaurant, starCount, reviewList);
                })
                .orElseThrow();
    }

    @Transactional
    public List<LocalTime> retrieveAllAvailableReservationTime(long restaurantId, LocalDate date, int count) {
        // 1. restaurantId 로 데이터베이스에서 레스토랑 정보 조회한다.
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow();

        // 2. 해당 레스토랑의 특정날짜 예약정보를 조회해준다.
        List<Reservation> reservationList = reservationService.retrieveAll(restaurant, date);

        // 3. 예약 가능한 시간 리스트를 계산해서 반환한다.
        return restaurant.findAllAvailableReservationTime(reservationList, count);
    }

    @Transactional
    public void register(RegisterRestaurantRequest req) {
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

}
