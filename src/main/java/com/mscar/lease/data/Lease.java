package com.mscar.lease.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * JPA Entity - Lease
 */
@Entity
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Lease {
    private @Id
    @GeneratedValue Long id;

    @Column(nullable = false)
    @Min(value = 1 , message = "INVALID_CUSTOMER_ID")
    private long customerId;

    @Column(nullable = false)
    @Min(value = 1 , message = "INVALID_CAR_ID")
    private long carId;

    @Column(nullable = false, length = 10)
    @Pattern(regexp = "^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-[0-9]{4}$", message = "START_DATE_INVALID")
    private String startDate;
    @Column(nullable = false, length = 10)
    @Pattern(regexp = "^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-[0-9]{4}$", message = "END_DATE_INVALID")
    private String endDate;
    @Column(nullable = false, precision = 2, scale = 2)
    @DecimalMin(value = "0.0" , inclusive = false , message = "INVALID_INTEREST_RATE")
    @Digits(integer = 2, fraction=2 , message= "INVALID_INTEREST_RATE")
    private double interestRate;

    public Lease(long customerId, long carId, String startDate,
                    String endDate, double interestRate) {
        this.customerId = customerId;
        this.carId = carId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.interestRate = interestRate;
    }
}
