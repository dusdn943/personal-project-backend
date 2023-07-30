package com.example.demo.review.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.review.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // JPQL
    // JPA 가 자동으로 쿼리를 만들어주기는 하지만 좀 복잡한 쿼리를 만드는데는 제약이 좀 있음
    // 그래서 내가 쿼리를 직접 작성하고 싶다. -> JPQL (SQL 문이랑은 조금 다르다.)
    // 비슷한 부분도 있지만, 기본적으로 JPQL 은 객체 기반으로 쿼리를 작성한다.
    @Query("SELECT re FROM Review re WHERE re.reservation.restaurant.restaurantId = :restaurantId")
    List<Review> findAllByRestaurantId(@Param("restaurantId") long restaurantId);
}
