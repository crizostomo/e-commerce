package com.developer.ecommerce.persistence.repository;

import com.developer.ecommerce.persistence.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Repository
@RestControllerAdvice
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByNameContains(String name);

    @Query("select u from Customer u where (:q is null or UPPER(u.name) like %:q% or UPPER(u.email) like %:q%)" +
            "and (:min_age is null or u.age >= :min_age) and (:max_age is null or u.age <= :max_age)")
    List<Customer> findBySearch(@Param("q") String q, @Param("min_age") Integer min_age, @Param("max_age") Integer max_age);
}
