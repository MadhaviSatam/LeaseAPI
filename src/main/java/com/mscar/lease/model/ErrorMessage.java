package com.mscar.lease.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * Model for Error Message
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessage {

    private String code;
    private String message;

}
