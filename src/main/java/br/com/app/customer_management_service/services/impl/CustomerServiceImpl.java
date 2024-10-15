package br.com.app.customer_management_service.services.impl;

import br.com.app.customer_management_service.api.model.CustomerDTO;
import br.com.app.customer_management_service.entities.CustomerEntity;
import br.com.app.customer_management_service.handler.APIException;
import br.com.app.customer_management_service.repository.CustomerRepository;
import br.com.app.customer_management_service.services.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Log4j2
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerDTO addCustomer(CustomerDTO customerDTO) {
        log.debug("Adding a new customer: {}", customerDTO);
        validateCustomerDTO(customerDTO);
        CustomerEntity customer = createCustomerEntity(customerDTO);
        CustomerEntity savedCustomer = customerRepository.save(customer);
        log.info("Customer saved successfully");
        return convertToDTO(savedCustomer);
    }

    @Override
    public List<CustomerDTO> getCustomers() {
        log.info("Fetching all customers from the repository.");
        List<CustomerEntity> customerEntityList = customerRepository.findAll();
        log.info("Number of customers retrieved: {}", customerEntityList.size());
        return convertToDTOs(customerEntityList);
    }

    @Override
    public CustomerDTO getCustomerById(String customerId) {
        log.debug("Fetching customer with ID: {}", customerId);
        CustomerEntity customer = customerRepository.findById(UUID.fromString(customerId))
                .orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND,"Cliente não encontrado com ID: " + customerId));
        return convertToDTO(customer);
    }

    @Override
    public CustomerDTO updateCustomer(String customerId, CustomerDTO customerDTO) {
        log.debug("Updating customer with ID: {}", customerId);
        validateCustomerDTO(customerDTO);

        CustomerEntity existingCustomer = customerRepository.findById(UUID.fromString(customerId))
                .orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND,"Cliente não encontrado com ID: " + customerId));

        existingCustomer.setName(customerDTO.getName());
        existingCustomer.setCpf(customerDTO.getCpf());
        existingCustomer.setEmail(customerDTO.getEmail());
        existingCustomer.setPhone(customerDTO.getPhone());
        existingCustomer.setAddress(customerDTO.getAddress());
        existingCustomer.setDateTimeLastChange(LocalDateTime.now());

        CustomerEntity updatedCustomer = customerRepository.save(existingCustomer);
        log.info("Customer updated successfully with ID: {}", updatedCustomer.getId());
        return convertToDTO(updatedCustomer);
    }

    @Override
    public void deleteCustomer(String customerId) {
        log.debug("Deleting customer with ID: {}", customerId);
        CustomerEntity customer = customerRepository.findById(UUID.fromString(customerId))
                .orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND,"Cliente não encontrado com ID: " + customerId));
        customerRepository.delete(customer);
        log.info("Customer deleted successfully with ID: {}", customerId);
    }

    private List<CustomerDTO> convertToDTOs(List<CustomerEntity> customerEntities) {
        return customerEntities.stream()
                .map(this::convertToDTO)
                .toList();
    }


    private void validateCustomerDTO(CustomerDTO customerDTO) {
        if (customerDTO.getName() == null || customerDTO.getName().isEmpty()) {
            log.error("Validation failed: Pet name is required.");
            throw APIException.build(HttpStatus.BAD_REQUEST,"É necessario informar o nome do cliente");
        }
        if (customerDTO.getEmail() == null || customerDTO.getEmail().isEmpty()) {
            log.error("Validation failed: Customer email is required.");
            throw APIException.build(HttpStatus.BAD_REQUEST,"O email do cliente é obrigatorio");
        }
        if (customerDTO.getPhone() == null || customerDTO.getPhone().isEmpty()){
            log.error("Validation failed: Customer telefone is required.");
            throw APIException.build(HttpStatus.BAD_REQUEST,"O telefone do cliente é obrigatorio");
        }
            log.debug("Validation passed for customer: {}", customerDTO);
    }

    private CustomerDTO convertToDTO(CustomerEntity savedCustomer) {
        CustomerDTO reponseCustomerDTO = new CustomerDTO();
        reponseCustomerDTO.setId(savedCustomer.getId());
        reponseCustomerDTO.setName(savedCustomer.getName());
        reponseCustomerDTO.setCpf(savedCustomer.getCpf());
        reponseCustomerDTO.setEmail(savedCustomer.getEmail());
        reponseCustomerDTO.setPhone(savedCustomer.getPhone());
        reponseCustomerDTO.setAddress(savedCustomer.getAddress());
        reponseCustomerDTO.setCreateDate(String.valueOf(savedCustomer.getDateTimeRegistration()));
        reponseCustomerDTO.setUpdateDate(String.valueOf(savedCustomer.getDateTimeLastChange()));
        return reponseCustomerDTO;
    }

    private CustomerEntity createCustomerEntity(CustomerDTO customerDTO) {
        return CustomerEntity.builder()
                .name(customerDTO.getName())
                .cpf(customerDTO.getCpf())
                .email(customerDTO.getEmail())
                .phone(customerDTO.getPhone())
                .address(customerDTO.getAddress())
                .dateTimeRegistration(LocalDateTime.now())
                .build();
    }
}
