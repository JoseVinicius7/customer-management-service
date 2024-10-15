package br.com.app.customer_management_service.api;

import br.com.app.customer_management_service.api.model.CustomerDTO;
import br.com.app.customer_management_service.api.model.PetAssociationDTO;
import br.com.app.customer_management_service.api.model.PetDTO;
import br.com.app.customer_management_service.services.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
public class CustomerController implements CustomersApi {

    private final CustomerService customerService;

    @Override
    public ResponseEntity<Void> customersCustomerIdDelete(String customerId) {
        log.debug("Request to delete customer with ID: {}", customerId);
        customerService.deleteCustomer(customerId);
        log.info("Customer with ID: {} deleted successfully.", customerId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<CustomerDTO> customersCustomerIdGet(String customerId) {
        log.debug("Request to fetch customer with ID: {}", customerId);
        CustomerDTO customerDTO = customerService.getCustomerById(customerId);
        log.info("Customer found: {}", customerDTO);
        return ResponseEntity.ok(customerDTO);
    }

    @Override
    public ResponseEntity<List<PetDTO>> customersCustomerIdPetsGet(String customerId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> customersCustomerIdPetsPetIdDelete(String customerId, String petId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> customersCustomerIdPetsPost(PetAssociationDTO body, String customerId) {
        return null;
    }

    @Override
    public ResponseEntity<CustomerDTO> customersCustomerIdPut(CustomerDTO body, String customerId) {
        log.debug("Request to update customer with ID: {} with data: {}", customerId, body);
        CustomerDTO response = customerService.updateCustomer(customerId, body);
        log.info("Customer with ID: {} updated successfully.", customerId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<CustomerDTO>> customersGet() {
        log.info("Fetching all customers.");
        List<CustomerDTO> response = customerService.getCustomers();
        log.info("Number of customers retrieved: {}", response.size());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CustomerDTO> customersPost(@RequestBody @Valid CustomerDTO body) {
        log.debug("Request to add new customer: {}", body);
        CustomerDTO response = customerService.addCustomer(body);
        log.info("Customer added succesfully with ID: {}", response.getId());
        return ResponseEntity.ok(response);
    }
}
