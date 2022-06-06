package com.mscar.lease.exception;

import com.mscar.lease.model.ErrorWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * Lease API Exception
 */
@AllArgsConstructor
@Data
public class LeaseApiException extends RuntimeException {
    private ErrorWrapper errors;
    private Integer status;

    public LeaseApiException(Integer status) {
        super();
        this.status = status;
    }
}
