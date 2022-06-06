package com.mscar.lease.connector;

import com.mscar.lease.model.ErrorMessages;
import com.mscar.lease.utils.LogConstants;
import com.mscar.lease.model.ErrorMessage;
import com.mscar.lease.model.ErrorWrapper;
import com.mscar.lease.exception.LeaseApiException;
import com.mscar.lease.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Connector to connect to Customer API
 */
@Component
@Slf4j
public class CustomerApiConnector {

    private final String customerEndpoint;

    private final RestTemplate restTemplate;

    public CustomerApiConnector(@Value("${customerapi.endpoint}") String customerEndpoint,
                                RestTemplate restTemplate) {
        this.customerEndpoint = customerEndpoint;
        this.restTemplate = restTemplate;
    }

    /**
     * Retrieve Customer using Customer API endpoint, in case of error throw exception
     * @param id
     * @return
     */
    public Customer retrieveCustomer(long id) {
        try {
            return restTemplate.getForObject(customerEndpoint, Customer.class, id);
        } catch (RestClientException rec) {
            log.error(LogConstants.LOG_LEASEAPI_001, rec.getMessage());

            List errorList = new ArrayList<>();
            errorList.add(new ErrorMessage(ErrorMessages.INTERNAL_SERVER_ERROR.toString(),
                    ErrorMessages.INTERNAL_SERVER_ERROR.getValue()));
            throw new LeaseApiException(new ErrorWrapper(errorList), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}