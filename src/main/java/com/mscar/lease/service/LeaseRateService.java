package com.mscar.lease.service;

import com.mscar.lease.connector.CarApiConnector;
import com.mscar.lease.exception.LeaseApiException;
import com.mscar.lease.model.ErrorMessage;
import com.mscar.lease.model.ErrorMessages;
import com.mscar.lease.model.ErrorWrapper;
import com.mscar.lease.model.RequestLeaseRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Lease rate service
 */
@Component
public class LeaseRateService {
    @Autowired
    private CarApiConnector carApiConnector;

    // Interest rate is considered as constant for now,
    // the interest rate can even be saved  in the database and expose it via another endpoint
    // (depending on the functional requirement)
    private static final double INTEREST_RATE = 2.1;

    /**
     * Calculate lease rate based on car mileage, net price, contract duration and interest rate
     * @param requestLeaseRate
     * @return
     */
    public double calculateLeaseRate(RequestLeaseRate requestLeaseRate) {
        double leaseRate = 0;

        var car = carApiConnector.retrieveCar(requestLeaseRate.getCarId());
        if (car == null) {
            var errorList = new ArrayList<ErrorMessage>();
            errorList.add(new ErrorMessage(ErrorMessages.CAR_NOT_FOUND.toString(), ErrorMessages.CAR_NOT_FOUND.getValue()));
            throw new LeaseApiException(new ErrorWrapper(errorList), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        leaseRate = (((car.getMileage() / 12) * requestLeaseRate.getContractDuration())
                / car.getNettPrice())
                + (((INTEREST_RATE / 100) * car.getNettPrice()) / 12);

        return leaseRate;
    }
}
