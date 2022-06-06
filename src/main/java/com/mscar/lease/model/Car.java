package com.mscar.lease.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model for Car
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Car {
    private String make;
    private String model;
    private String version;
    private int numberOfDoors;
    private double co2Emission;
    private double grossPrice;
    private double nettPrice;
    private long mileage;
}
