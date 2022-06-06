package com.mscar.lease.connector;

import com.mscar.lease.utils.LogConstants;
import com.mscar.lease.exception.LeaseApiException;
import com.mscar.lease.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Connector to connect to Car API
 */
@Component
@Slf4j
public class CarApiConnector {

    private final String carEndpoint;

    private final RestTemplate restTemplate;

    public CarApiConnector(@Value("${carapi.endpoint}") String carEndpoint,
                           RestTemplate restTemplate) {
        this.carEndpoint = carEndpoint;
        this.restTemplate = restTemplate;
    }

    /**
     * Retrieve car using car api endpoint, in case of error throw exception
     * @param id
     * @return
     */
    public Car retrieveCar(long id) {
        try {
            return restTemplate.getForObject(carEndpoint, Car.class, id);
        } catch (RestClientException rec) {
            log.error(LogConstants.LOG_LEASEAPI_003, rec.getMessage());

            List errorList = new ArrayList<>();
            errorList.add(new ErrorMessage(ErrorMessages.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getValue()));
            throw new LeaseApiException(new ErrorWrapper(errorList), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}