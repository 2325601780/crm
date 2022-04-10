package com.crm.workbench.service.impl;

import com.crm.utils.SqlSessionUtil;
import com.crm.workbench.dao.CustomerDao;
import com.crm.workbench.domain.Customer;
import com.crm.workbench.service.CustomerService;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    @Override
    public List<Customer> getCustomerName(String name) {
        List<Customer> cList = customerDao.getCustomerName(name);
        return cList;
    }
}
