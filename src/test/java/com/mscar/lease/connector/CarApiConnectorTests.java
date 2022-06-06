package com.mscar.lease.connector;

import com.mscar.lease.exception.LeaseApiException;
import com.mscar.lease.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarApiConnectorTests {

    private static CarApiConnector carApiConnector;

    private static RestTemplate restTemplate;

    @BeforeAll
    static void setUp() {
        restTemplate = mock(RestTemplate.class);
        carApiConnector = new CarApiConnector(
                "http://localhost:8081/api/car/{id}", restTemplate);
    }

    @Test
    void retrieveCar() {
        var car = Car.builder()
                .make("Ford")
                .model("Ford fiesta")
                .version("1")
                .mileage(1000)
                .nettPrice(20000)
                .grossPrice(24000).build();

        when(restTemplate.getForObject(anyString(), ArgumentMatchers.<Class<Car>>any(),
                anyLong())).thenReturn(car);

        Car retObj = carApiConnector.retrieveCar(1L);
        assertNotNull(retObj);
        assertEquals("Ford", car.getMake());
        assertEquals("1", car.getVersion());
        //all possible attributes of return object should be asserted.
    }

    @Test
    void retrieveCar_throwRestClientException() {

        when(restTemplate.getForObject(anyString(), ArgumentMatchers.<Class<Car>>any(),
                anyLong())).thenThrow(new RestClientException("Technical Error Occurred"));

        LeaseApiException exception = assertThrows(LeaseApiException.class, () ->
                carApiConnector.retrieveCar(1L)
        );
        ErrorWrapper errorWrapper = exception.getErrors();
        ErrorMessage errorMessage = errorWrapper.getErrors().get(0);
        assertEquals(errorMessage.getCode(), ErrorMessages.INTERNAL_SERVER_ERROR.toString());
        assertEquals(errorMessage.getMessage(), ErrorMessages.INTERNAL_SERVER_ERROR.getValue());
    }
}