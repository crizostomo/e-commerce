package com.developer.ecommerce.ws.v1;

import com.developer.ecommerce.model.request.CustomerRequest;
import com.developer.ecommerce.model.response.CustomerResponse;
import com.developer.ecommerce.persistence.repository.CustomerRepository;
import com.developer.ecommerce.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Autowired
    private CustomerService customerService;

    @ApiOperation("API Responsible for creating customers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Customer ok"),
            @ApiResponse(code = 500, message = "It happened an error in creating the customer")
    })
    @PostMapping
    public ResponseEntity<CustomerResponse> create(@RequestBody CustomerRequest customerRequest){
        LOGGER.info("Request received");
        return ResponseEntity.ok(customerService.create(customerRequest));
    }

    @ApiOperation("API Responsible for listing customers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Customer-Listing ok"),
            @ApiResponse(code = 500, message = "It happened an error in listing the customers")
    })
    @GetMapping
    public ResponseEntity<Page<CustomerResponse>> getAll(Pageable pageable){
        LOGGER.info("Searching registers");
        Page<CustomerResponse> customerResponses = customerService.getAll(pageable);
        return ResponseEntity.ok(customerResponses);
    }

    @ApiOperation("API Responsible for updating customers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Customer-updating ok"),
            @ApiResponse(code = 500, message = "It happened an error in updating the customer")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(@PathVariable("id") Long id,@RequestBody CustomerRequest customerRequest){
        LOGGER.info("Initializing updating");
        Optional<CustomerResponse> update = customerService.update(id, customerRequest);
        if(!update.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(update.get());

    }

    @ApiOperation("API Responsible for searching a unique customer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Customer-searching ok"),
            @ApiResponse(code = 500, message = "It happened an error in searching the customer")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(@PathVariable("id") Long id){
        LOGGER.info("Starting searches for the unique register");
        Optional<CustomerResponse> customerResponse = customerService.get(id);
        if(!customerResponse.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customerResponse.get());
    }

    @ApiOperation("API Responsible for searching a unique customer customized")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Product ok"),
            @ApiResponse(code = 500, message = "It happened an error in creating the product")
    })
    @GetMapping("/search")
    public ResponseEntity<List<CustomerResponse>> findBySearch(@RequestParam(value = "q", required = false) String q,
                                                           @RequestParam(value = "min_age", required = false) Integer min_age,
                                                           @RequestParam(value = "max_age", required = false) Integer max_age) {
        LOGGER.info("Searching part of the query");
        List<CustomerResponse> customerResponses = customerService.findBySearch(q.toUpperCase(), min_age, max_age);
        return ResponseEntity.ok(customerResponses);
    }

    @ApiOperation("API Responsible for deleting a unique customer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Deleting ok"),
            @ApiResponse(code = 500, message = "It happened an error in deleting the customer")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        LOGGER.info("Starting the deleting process");
        if(customerService.delete(id)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
