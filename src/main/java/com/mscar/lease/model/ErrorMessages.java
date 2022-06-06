package com.mscar.lease.model;

/**
 * Enum for Error messages
 */
public enum ErrorMessages {

    INTERNAL_SERVER_ERROR("Internal server error occurred"),
    INVALID_CAR_ID("Car Id is invalid"),
    INVALID_CONTRACT_DURATION("Contract duration is invalid"),
    CAR_NOT_FOUND("Car not found"),

    INVALID_INTEREST_RATE("Invalid interest rate value"),

    INVALID_CUSTOMER_ID("Invalid customer id"),

    CAR_ID_MISSING("Car id missing"),

    CONTRACT_DURATION_MISSING("Contract duration missing"),

    START_DATE_INVALID("Start date of the lease is invalid"),

    END_DATE_INVALID("End date of the lease is invalid");

    private final String value;

    ErrorMessages(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
