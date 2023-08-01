package com.example.demo.review.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.Where;

import com.example.demo.reservation.entity.Reservation;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    private String contents;
    private int starCount;

    public Review(Reservation reservation, String contents, int starCount) {
        this.reservation = reservation;
        this.contents = contents;
        this.starCount = starCount;
    }

    public long getMemberId() {
        return this.reservation.getMember().getMemberId();
    }
}
