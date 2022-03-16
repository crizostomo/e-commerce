package com.developer.ecommerce.service;

import com.developer.ecommerce.model.request.CustomerRequest;
import com.developer.ecommerce.model.response.CustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    CustomerResponse create(CustomerRequest customerRequest);

    Page<CustomerResponse> getAll(Pageable pageable);

    Optional<CustomerResponse> update(Long id, CustomerRequest customerRequest);

    Optional<CustomerResponse> get(Long id);

    List<CustomerResponse> findBySearch(String q, Integer min_age, Integer max_age);

    boolean delete(Long id);
}
