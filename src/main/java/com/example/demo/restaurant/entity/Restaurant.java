package com.example.demo.restaurant.entity;

import com.example.demo.businessMember.entity.BusinessMember;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class Restaurant {

    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_member_id")
    private BusinessMember businessMember; // 연관관계

    private String name;
    private String address;
    private String contents;
    private String phoneNumber;

    public Restaurant(String name, String address, String contents, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.contents = contents;
        this.phoneNumber = phoneNumber;
    }

}
