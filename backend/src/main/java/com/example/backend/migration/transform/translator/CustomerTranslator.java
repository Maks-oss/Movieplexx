package com.example.backend.migration.transform.translator;

import com.example.backend.data.nosql.CustomerDocument;
import com.example.backend.data.sql.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("customer_translator")
public class CustomerTranslator implements ItemTranslator<Customer, CustomerDocument>{
    @Override
    public List<CustomerDocument> transformData(List<Customer> input) {
        return input.stream().map(customer -> {
            var customerDocument = new CustomerDocument();
            BeanUtils.copyProperties(customer, customerDocument);
            return customerDocument;
        }).collect(Collectors.toList());
    }
}
