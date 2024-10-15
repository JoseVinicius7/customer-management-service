package br.com.app.customer_management_service.services;


import br.com.app.customer_management_service.api.model.CustomerDTO;

import java.util.List;

public interface CustomerService {

    CustomerDTO addCustomer(CustomerDTO body);

    List<CustomerDTO> getCustomers();

    CustomerDTO getCustomerById(String customerId);
}
