package com.mscar.lease;

import com.mscar.lease.exception.LeaseApiException;
import com.mscar.lease.repository.LeaseRepository;
import org.junit.jupiter.api.Assertions;
import com.mscar.lease.data.Lease;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LeaseControllerTests {
    @InjectMocks
    LeaseController leaseController;

    @Mock
    LeaseRepository leaseRepository;

    @Test
    public void testRetrieveLeaseDetails() {
        var lease = createLeaseData();

        when(leaseRepository.findById(1L)).thenReturn(Optional.of(lease));

        var result = leaseController.getLease(1L);

        assertEquals(2.1, result.getInterestRate());
        assertEquals("12-02-2020", result.getStartDate());
    }

    @Test
    public void testCreateLease() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        var lease = createLeaseData();

        when(leaseRepository.save(any())).thenReturn(lease);

        var result = leaseController.createLease(lease);

        assertEquals(12, result.getCustomerId());
    }

    @Test
    public void testUpdateLease() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        var lease = createLeaseData();
        lease.setInterestRate(3.4);

        when(leaseRepository.findById(1L)).thenReturn(Optional.of(lease));
        when(leaseRepository.save(any())).thenReturn(lease);

        var result = leaseController.updateLease(lease, 1L);

        assertEquals(3.4, result.getInterestRate());
    }

    @Test
    public void testDeleteLease() {

        doNothing().when(leaseRepository).deleteById(1L);

        leaseController.deleteLease(1L);

        verify(leaseRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testRetrieveLeaseDetails_Exception() {

        when(leaseRepository.findById(2L)).thenThrow(new LeaseApiException(HttpStatus.NOT_FOUND.value()));

        LeaseApiException thrown = Assertions
                .assertThrows(LeaseApiException.class, () -> {
                    var result = leaseController.getLease(2L);
                }, "CarApiException error was expected");

        assertEquals(HttpStatus.NOT_FOUND.value(), thrown.getStatus());
    }

    @Test
    public void testDeleteLease_Exception() {

        doThrow(new LeaseApiException(HttpStatus.NOT_FOUND.value())).when(leaseRepository).deleteById(2L);

        LeaseApiException thrown = Assertions
                .assertThrows(LeaseApiException.class, () -> {
                   leaseController.deleteLease(2L);
                }, "LeaseApiException error was expected");

        assertEquals(HttpStatus.NOT_FOUND.value(), thrown.getStatus());
    }

    private Lease createLeaseData()
    {
        return Lease.builder()
                .customerId(12)
                .carId(1)
                .startDate("12-02-2020")
                .endDate("12-02-2021")
                .interestRate(2.1).build();
    }
}

