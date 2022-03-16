package com.developer.ecommerce.model.response;

import com.developer.ecommerce.persistence.entity.Customer;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CustomerResponse {

    private Long id;
    private String name;
    private String zipCode;
    private String email;
    private String status;
    private Integer age;

    public static CustomerResponse converter(Customer c){
        var customer = new CustomerResponse();
        customer.setId(c.getId());
        customer.setName(c.getName());
        customer.setZipCode(c.getZipCode());
        customer.setEmail(c.getEmail());
        customer.setAge(c.getAge());
        customer.setStatus(c.getStatus());
        return customer;
    }
}
