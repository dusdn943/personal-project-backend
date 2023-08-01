package com.example.demo.review.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.helper.JwtHelper;
import com.example.demo.common.type.MemberType;
import com.example.demo.review.dto.ReviewResponse;
import com.example.demo.review.dto.WriteReviewRequest;
import com.example.demo.review.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final JwtHelper jwtHelper;

    // API 1. 사용자는 맛집 상세페이지에 들어가서 리뷰 목록을 확인할 수 있다.
    //  -> 각 리뷰의 (작성자 nickname, 별점, 리뷰 내용, 방문일자, 예약인원)
    @Operation(summary = "맛집 상세페이지에서 리뷰 목록을 확인하는 API")
    @GetMapping("/reviews/restaurant/{id}")
    public List<ReviewResponse> retrieveAllReviewByRestaurant(@PathVariable(name = "id") long restaurantId) {
        System.out.println("restaurantId = " + restaurantId);
        return reviewService.retrieveAll(restaurantId);
    }

    // API 3. 사용자는 자신이 예약한 목록에서 기간이 "지난 예약"에 대해서 리뷰를 작성할 수 있어야한다.
    @Operation(summary = "회원이 예약한 맛집에 리뷰를 작성할 수 있는 API")
    @PostMapping("/review")  // 어떤 예약인지, 내용이 뭔지, 별점은 몇개줄건지에 대한 것
    public void writeReview(@RequestBody WriteReviewRequest req) {
        System.out.println("req = " + req);
        reviewService.write(req);
    }

    @Operation(summary = "회원이 작성한 리뷰만 삭제할 수 있는 API")
    @DeleteMapping("/review/{id}")
    public void deleteReview(
            @PathVariable(name = "id") long reviewId,
            @RequestHeader(name = "Authorization") String authorization
    ) {
        String accessToken = authorization.substring("Bearer ".length());
        MemberType type = MemberType.valueOf(jwtHelper.getClaim(accessToken, "MEMBER_TYPE"));
        if (!type.equals(MemberType.GENERAL)) {
            throw new RuntimeException("일반 사용자만 이용가능한 기능입니다.");
        }
        long memberId = Long.parseLong(jwtHelper.getClaim(accessToken, "MEMBER_ID"));
        reviewService.delete(memberId, reviewId);
    }

}
