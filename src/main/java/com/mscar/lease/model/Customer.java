package com.mscar.lease.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model for Customer
 */
@Data
@NoArgsConstructor
public class Customer {
    private long id;
    private String name;
    private String street;
    private int houseNumber;
    private String zipcode;
    private String place;
    private String email;
    private int phoneNumber;
}
