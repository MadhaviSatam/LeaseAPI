package com.mscar.lease.utils;

import com.mscar.lease.exception.LeaseApiException;
import com.mscar.lease.model.ErrorMessage;
import com.mscar.lease.model.ErrorMessages;
import com.mscar.lease.model.ErrorWrapper;
import com.mscar.lease.model.RequestLeaseRate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Lease validator
 */
@Component
public class LeaseValidator {

    /**
     * Method to validate lease rate endpoint input
     * @param requestLeaseRate
     */
    public void validateLeaseRateInput(RequestLeaseRate requestLeaseRate)
    {
        var errorList = new ArrayList<ErrorMessage>();

        if (requestLeaseRate.getCarId() == null || requestLeaseRate.getCarId() <= 0) {
            errorList.add(new ErrorMessage(ErrorMessages.INVALID_CAR_ID.toString(),
                    ErrorMessages.INVALID_CAR_ID.getValue()));
        }
        if (requestLeaseRate.getContractDuration() == null || requestLeaseRate.getContractDuration() <= 0) {
            errorList.add(new ErrorMessage(ErrorMessages.INVALID_CONTRACT_DURATION.toString(),
                    ErrorMessages.INVALID_CONTRACT_DURATION.getValue()));
        }

        if (!errorList.isEmpty()) {
            throw new LeaseApiException(new ErrorWrapper(errorList), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
