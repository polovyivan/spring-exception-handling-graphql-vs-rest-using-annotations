package com.polovyi.ivan.service;

import com.polovyi.ivan.dto.CreateCustomerRequest;
import com.polovyi.ivan.dto.CustomerResponse;
import com.polovyi.ivan.entity.CustomerEntity;
import com.polovyi.ivan.exeption.NotFoundException;
import com.polovyi.ivan.exeption.UnprocessableEntityException;
import com.polovyi.ivan.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerResponse getCustomersById(String customerId) {
        log.info("Getting customer by id...");
        return customerRepository.findById(customerId).map(CustomerResponse::valueOf).orElseThrow(() -> {
            log.error("Customer not found!");
            return new NotFoundException();
        });
    }

    public CustomerResponse createCustomer(CreateCustomerRequest createCustomerRequest) {
        log.info("Creating a customer... ");
        validatePhone(createCustomerRequest.getPhoneNumber());
        CustomerEntity customer = CustomerEntity.valueOf(createCustomerRequest);
        return CustomerResponse.valueOf(customerRepository.save(customer));
    }

    private void validatePhone(String phoneNumber) {
        log.info("Validating phone number {} ... ", phoneNumber);
        if (!phoneNumber.chars().allMatch(Character::isDigit)) {
            log.error("Phone number is invalid {}", phoneNumber);
            throw new UnprocessableEntityException("Phone number is invalid!");
        }
    }
}
