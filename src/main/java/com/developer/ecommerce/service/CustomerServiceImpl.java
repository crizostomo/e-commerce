package com.developer.ecommerce.service;

import com.developer.ecommerce.model.request.CustomerRequest;
import com.developer.ecommerce.model.response.CustomerResponse;
import com.developer.ecommerce.persistence.entity.Customer;
import com.developer.ecommerce.persistence.repository.CustomerRepository;
import com.developer.ecommerce.utils.QueueUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.developer.ecommerce.utils.QueueUtils.*;
import static org.springframework.util.Assert.*;
import static org.springframework.util.Assert.notNull;

@Service
public class CustomerServiceImpl implements CustomerService{

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public CustomerResponse create(CustomerRequest customerRequest) {
        LOGGER.info("Creating a product register");
        notNull(customerRequest, "Invalid Request");
        Customer customer = customerRepository.saveAndFlush(modelMapper.map(customerRequest, Customer.class));
        rabbitTemplate.convertAndSend(QUEUE_NAME, customer.getId());
        return (modelMapper.map(customer, CustomerResponse.class));
    }

    @Override
    public Page<CustomerResponse> getAll(Pageable pageable) {
        LOGGER.info("Searching all registers");
        notNull(pageable, "Invalid info");
        return customerRepository.findAll(pageable).map(customer -> modelMapper.map(customer, CustomerResponse.class));
    }

    @Override
    public Optional<CustomerResponse> update(Long id, CustomerRequest customerRequest) {
        LOGGER.info("Updating a customer register");
        notNull(id, "Invalid id");
        return customerRepository.findById(id)
                .map(customer -> {
                    customer.setName(customerRequest.getName());
                    customer.setZipCode(customerRequest.getZipCode());
                    customer.setEmail(customerRequest.getEmail());
                    customer.setAge(customerRequest.getAge());
                    customer.setStatus(customerRequest.getStatus());
                    return modelMapper.map(customerRepository.saveAndFlush(customer), CustomerResponse.class);
                });
    }

    @Override
    public Optional<CustomerResponse> get(Long id) {
        LOGGER.info("Searching register");
        notNull(id, "Invalid id");
        Optional<Customer> byId = customerRepository.findById(id);
        if(byId.isPresent()){
            CustomerResponse map = modelMapper.map(byId.get(),CustomerResponse.class);
            return Optional.of(map);
        }
        return Optional.empty();
    }

    @Override
    public List<CustomerResponse> findBySearch(@RequestParam(value = "q", required = false) String q,
                                           @RequestParam(value = "min_age", required = false) Integer min_age,
                                           @RequestParam(value = "max_age", required = false) Integer max_age) {
        LOGGER.info("Searching pieces of information");
        return this.customerRepository.findBySearch(q.toUpperCase(), min_age, max_age)
                .stream()
                .map(CustomerResponse::converter)
                .collect(Collectors.toList());
    }

    @Override
    public boolean delete(Long id) {
        LOGGER.info("Deleting a customer");
        notNull(id, "Invalid id");

        try{
            customerRepository.deleteById(id);
            return true;
        } catch (Exception e){
            LOGGER.warn("Error when removing register {}", id);
        }

        return false;
    }

    @RabbitListener(queues = QUEUE_NAME)
    private void subscribe(Long id){
        Optional<Customer> customer = customerRepository.findById(id);

        if(customer.isPresent()){
            customer.get().setStatus("DONE");
            customerRepository.save(customer.get());
        }
    }
}
