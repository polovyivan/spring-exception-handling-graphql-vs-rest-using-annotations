package com.polovyi.ivan.controller;

import com.polovyi.ivan.dto.CreateCustomerRequest;
import com.polovyi.ivan.dto.CustomerResponse;
import com.polovyi.ivan.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public record CustomerRESTController(CustomerService customerService) {

    @GetMapping(path = "/v1/customers/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse getAllCustomers(@PathVariable String customerId) {
        return customerService.getCustomersById(customerId);
    }

    @PostMapping(path = "/v1/customers")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCustomer(@Valid @RequestBody CreateCustomerRequest createCustomerRequest,
            UriComponentsBuilder uriBuilder, HttpServletResponse response) {
        String customerId = customerService.createCustomer(createCustomerRequest).getId();
        response.addHeader("location", uriBuilder.path("/v1/customers/{id}").buildAndExpand(customerId).toUriString());
    }

}
