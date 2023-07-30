package com.example.demo.review.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.reservation.entity.Reservation;
import com.example.demo.reservation.repository.ReservationRepository;
import com.example.demo.restaurant.entity.Restaurant;
import com.example.demo.review.dto.ReviewResponse;
import com.example.demo.review.dto.WriteReviewRequest;
import com.example.demo.review.entity.Review;
import com.example.demo.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public List<ReviewResponse> retrieveAll(long restaurantId) {
        // 1. 사용자가 준 restaurantId 에 작성된 모든 리뷰를 예약일로 정렬해서 데이터베이스에서 조회한다.
        return reviewRepository.findAllByRestaurantId(restaurantId).stream()

                // 2. 조회된 리뷰를 ReviewResponse 모양으로 바꾼 후 반환한다.
                .map(ReviewResponse::from)
                .collect(Collectors.toList());
    }

    public void write(WriteReviewRequest req) {
        // 1. 사용자가 준 reservationId 로 데이터베이스에서 예약정보를 조회한다.
        Reservation reservation = reservationRepository.findById(req.getReservationId()).orElseThrow();
        if (reservation.getDateTime().isAfter(LocalDateTime.now())) {
            // 리뷰 작성이 실패하면 예외를 발생시킨다.
            throw new RuntimeException("아직 리뷰작성이 불가능한 예약입니다.");
        }

        // 2. 예약정보 + 사용자가 준 리뷰정보로 Review Entity 생성.
        Review review = new Review(reservation, req.getContents(), req.getStarCount());

        // 3. 생성된 Review Entity 데이터베이스에 저장.
        reviewRepository.save(review);
    }

    @Transactional
    public void delete(long memberId, long reviewId) {
        // 기존에 작성된 리뷰를 찾는다.
        reviewRepository.findById(reviewId).ifPresent(review -> {
            // 만약 리뷰를 작성한 사람과 지금 지우려는 사람이 다르다면 에러를 발생시킨다.
            if (review.getMemberId() != memberId) {
                throw new RuntimeException("작성자가 아니어서 리뷰를 삭제할 수 없습니다!");
            }
            reviewRepository.delete(review);
        });
    }

    public double calculateStarCountAverage(Restaurant restaurant) {
        return 0;
    }
}
