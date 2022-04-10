package com.crm.workbench.dao;

import com.crm.workbench.domain.Customer;

import java.util.List;

public interface CustomerDao {

    Customer getCustomerByName(String company);

    int save(Customer cus);

    List<Customer> getCustomerName(String name);
}
