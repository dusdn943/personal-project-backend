package com.example.demo.reservation.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.annotations.Where;

import com.example.demo.member.entity.Member;
import com.example.demo.restaurant.entity.Restaurant;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@Where(clause = "is_canceled = false") // Reservation 을 조회하는 모든 쿼리에 Default 값으로 취소된거 제외하는 Where 문 추가
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reservationId; // PK

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 누가

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant; // 어디를

    // 2023-07-23
    private LocalDate date;
    // 15:00
    private LocalTime time;

    private int count; // 몇명
    private boolean isCanceled; // true (Soft Delete)

    public Reservation(Member member, Restaurant restaurant, LocalDateTime datetime, int count) {
        this.member = member;
        this.restaurant = restaurant;
        this.date = datetime.toLocalDate();
        this.time = datetime.toLocalTime();
        this.count = count;
        this.isCanceled = false;
    }

    public void cancel() {
        this.isCanceled = true;
    }
    public LocalDateTime getDateTime() {
        return LocalDateTime.of(date, time);
    }
}
