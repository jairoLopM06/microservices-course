package com.example.userservice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Car {
    private String manufacturer;
    private String model;
    private int userId;
}
