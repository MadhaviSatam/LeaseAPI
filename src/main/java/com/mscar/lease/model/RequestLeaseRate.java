package com.mscar.lease.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Model for Lease Rate request object
 */
@AllArgsConstructor
@Data
public class RequestLeaseRate {

    @NotNull(message="CAR_ID MISSING")
    private Integer carId;

    @NotNull(message="CONTRACT_DURATION_MISSING")
    private Integer contractDuration;
}
