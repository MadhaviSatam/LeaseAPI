package com.mscar.lease;

import com.mscar.lease.data.Lease;
import com.mscar.lease.exception.LeaseApiException;
import com.mscar.lease.model.*;
import com.mscar.lease.repository.LeaseRepository;
import com.mscar.lease.service.LeaseRateService;
import com.mscar.lease.utils.LeaseValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

/**
 * Lease REST controller
 */
@Slf4j
@RestController
@RequestMapping("/api/lease")
public class LeaseController {

    @Autowired
    private LeaseValidator leaseValidator;
    @Autowired
    private LeaseRateService leaseRateService;

    @Autowired
    private LeaseRepository leaseRepository;

    /**
     * GET endpoint to calculate and provide lease rate based on car and contract details
     *
     * @param requestLeaseRate
     * @return lease rate
     */
    @GetMapping("/leaserate")
    HashMap<String, BigDecimal> getLeaseRate(@RequestBody RequestLeaseRate requestLeaseRate) {
        leaseValidator.validateLeaseRateInput(requestLeaseRate);
        var leaseRate = BigDecimal.valueOf(leaseRateService.calculateLeaseRate(requestLeaseRate)).setScale(2, RoundingMode.HALF_UP);

        return new HashMap<String, BigDecimal>() {{
            put("leaseRate", leaseRate);
        }};
    }

    /**
     * Retrieve Lease details from H2 database for provided lease id
     *
     * @param id
     * @return Lease
     */
    @GetMapping("/{id}")
    Lease getLease(@PathVariable Long id) {
        return leaseRepository.findById(id).orElseThrow(() ->
                new LeaseApiException(HttpStatus.NOT_FOUND.value()));
    }

    /**
     * Create new Lease in H2 database
     *
     * @param lease
     * @return Lease
     */
    @PostMapping("")
    Lease createLease(@Valid  @RequestBody Lease lease) {
        return leaseRepository.save(lease);
    }

    /**
     * Update Lease in H2 database for provided lease id
     *
     * @param inputLease
     * @param id
     * @return Lease
     */
    @PutMapping("/{id}")
    Lease updateLease(@Valid @RequestBody Lease inputLease, @PathVariable Long id) {

        return leaseRepository.findById(id)
                .map(lease -> {
                    lease.setCustomerId(inputLease.getCustomerId());
                    lease.setStartDate(inputLease.getStartDate());
                    lease.setEndDate(inputLease.getEndDate());
                    lease.setInterestRate(inputLease.getInterestRate());
                    return leaseRepository.save(lease);
                }).orElseThrow(() ->
                        new LeaseApiException(HttpStatus.NOT_FOUND.value()));
    }

    /**
     * Delete Lease from H2 database for provided Lease id
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    void deleteLease(@PathVariable Long id) {
        leaseRepository.deleteById(id);
    }
}
