package com.mscar.lease.connector;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.mscar.lease.model.Customer;
import com.mscar.lease.model.ErrorMessages;
import com.mscar.lease.model.ErrorMessage;
import com.mscar.lease.model.ErrorWrapper;
import com.mscar.lease.exception.LeaseApiException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class CustomerApiConnectorTests {

    private static CustomerApiConnector customerApiConnector;

    private static RestTemplate restTemplate;

    @BeforeAll
    static void setUp() {
        restTemplate = mock(RestTemplate.class);
        customerApiConnector = new CustomerApiConnector(
                "http://localhost:8083/api/customer/{id}",restTemplate);
    }

    @Test
    void retrieveCustomer() {
        Customer customer = new Customer();
        customer.setEmail("test@test.com");
        customer.setHouseNumber(10);
        customer.setId(1L);
        customer.setName("MyName");
        customer.setPlace("Amsterdam");
        customer.setPhoneNumber(684481228);
        customer.setZipcode("1188EC");
        customer.setStreet("Test Street");
        when(restTemplate.getForObject(anyString(), ArgumentMatchers.<Class<Customer>>any(),
                anyLong())).thenReturn(customer);

        Customer retObj = customerApiConnector.retrieveCustomer(1L);
        assertNotNull(retObj);
        assertEquals("MyName",customer.getName());
        assertEquals(1L,customer.getId());
        //all possible attributes of return object should be asserted.
    }

    @Test
    void retrieveCustomer_throwRestClientException() {

        when(restTemplate.getForObject(anyString(), ArgumentMatchers.<Class<Customer>>any(),
                anyLong())).thenThrow(new RestClientException("Technical Error Occurred"));

        LeaseApiException exception = assertThrows(LeaseApiException.class, () ->
                customerApiConnector.retrieveCustomer(1L)
        );
        ErrorWrapper errorWrapper = exception.getErrors();
        ErrorMessage errorMessage = errorWrapper.getErrors().get(0);
        assertEquals(errorMessage.getCode(),ErrorMessages.INTERNAL_SERVER_ERROR.toString());
        assertEquals(errorMessage.getMessage(),ErrorMessages.INTERNAL_SERVER_ERROR.getValue());
    }
}