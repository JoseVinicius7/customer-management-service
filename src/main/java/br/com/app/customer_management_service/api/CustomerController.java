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
        return null;
    }

    @Override
    public ResponseEntity<CustomerDTO> customersCustomerIdGet(String customerId) {
        return null;
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
        return null;
    }

    @Override
    public ResponseEntity<List<CustomerDTO>> customersGet() {
        return null;
    }

    @Override
    public ResponseEntity<CustomerDTO> customersPost(@RequestBody @Valid CustomerDTO body) {
        log.debug("Request to add new pet: {}", body);
        CustomerDTO response = customerService.addCustomer(body);
        log.info("Customer added succesfully with ID: {}", response.getId());
        return ResponseEntity.ok(response);
    }
}
