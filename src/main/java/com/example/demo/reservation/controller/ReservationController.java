package com.example.demo.reservation.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.reservation.dto.ReservationCommand;
import com.example.demo.reservation.dto.ReservationRequest;
import com.example.demo.reservation.dto.ReservationResponse;
import com.example.demo.reservation.service.ReservationService;
import com.example.demo.restaurant.service.RestaurantService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    // 레스토랑 서비스가 가지고 있는 책임이 너무많아져서 여러 곳으로 분산 시킨다.
    private final RestaurantService restaurantService;
    private final ReservationService reservationService;

    // 전제 : 사용자가 몇명 예약할지를 선택해야할거같음
    // API : 사용자는 달력의 특정 날짜, 예약 인원을 선택해 해당 Restaurant 의 예약가능한 시간을 확인할 수 있다.
    @Operation(summary = "레스토랑의 현재 이용가능한 예약시간 조회하는 API")
    @GetMapping("/restaurant/{id}/available-reservation") // 사용자로부터 받아야 할 데이터 : 언제(날짜)
    public List<LocalTime> retrieveAvailableForReservation(
            @PathVariable(name = "id") long restaurantId,
            @RequestParam(name = "reservation_date") LocalDate reservationDate,
            @RequestParam(name = "reservation_count") int count
    ) {
        System.out.println("restaurantId = " + restaurantId + ", reservationDate = " + reservationDate + ", count = " + count);
        return restaurantService.retrieveAllAvailableReservationTime(restaurantId, reservationDate, count);
        // [12:00, 13:00, 14:00, 15:00, 19:00]
    }

    @Operation(summary = "해당 멤버가 예약한 예약정보 전체 조회 API")
    @GetMapping("/reservations/member/{id}")
    public List<ReservationResponse> retrieveAllReservationByMember(@PathVariable(name = "id") long memberId) {
        System.out.println("memberId = " + memberId);
        return reservationService.retrieveAll(memberId);
    }

    // API : 사용자는 Restaurant 을 예약할 수 있다.
    @Operation(summary = "레스토랑을 예약하는 API")
    @PostMapping("/restaurant/{id}/reservation")
    public void reservation(
            @PathVariable(name = "id") long restaurantId,
            @RequestBody ReservationRequest req
    ) {
        // restaurantId(어디), req(누가, 언제, 몇명) -> 하나의 객체로 묶음
        // 그러면 당연히 이 묶인 객체도 DTO임
        ReservationCommand command = new ReservationCommand(restaurantId, req);
        reservationService.reservation(command);
    }

    @Operation(summary = "레스토랑 예약을 취소 하는 API")
    @DeleteMapping("/reservation/{id}")
    public void cancelReservation(@PathVariable(name = "id") long reservationId) {
        reservationService.cancel(reservationId);
    }

}