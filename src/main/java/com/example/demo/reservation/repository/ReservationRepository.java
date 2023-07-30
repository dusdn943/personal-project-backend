package com.example.demo.reservation.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.member.entity.Member;
import com.example.demo.reservation.entity.Reservation;
import com.example.demo.restaurant.entity.Restaurant;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByRestaurantAndDate(Restaurant restaurant, LocalDate date);

    List<Reservation> findAllByMember(Member member);
}

