package com.polovyi.ivan.controller;

import com.polovyi.ivan.dto.CustomerResponse;
import com.polovyi.ivan.service.CustomerService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import javax.validation.constraints.NotNull;

@Controller
public record CustomerGraphQLQueryController(CustomerService customerService) {


    @QueryMapping(name = "customerById")
    public CustomerResponse getCustomerById(@Argument @NotNull String customerId) {
        return customerService.getCustomersById(customerId);
    }
}
