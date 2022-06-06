package com.mscar.lease.repository;

import com.mscar.lease.data.Lease;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA Entity - Lease
 */
public interface LeaseRepository extends JpaRepository<Lease, Long> {

}