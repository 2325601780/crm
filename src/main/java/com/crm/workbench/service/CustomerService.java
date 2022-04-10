package com.crm.workbench.service;

import com.crm.workbench.domain.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getCustomerName(String name);
}
