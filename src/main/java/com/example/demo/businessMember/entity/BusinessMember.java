package com.example.demo.businessMember.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class BusinessMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long businessMemberId;

    private String email;
    private String password;
    private String businessNumber;
    private String name;
    private String phoneNumber;

    public BusinessMember(String email, String password, String businessNumber, String name, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.businessNumber = businessNumber;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public boolean validPassword(String password) {
        return Objects.equals(this.password, password);
    }
}
