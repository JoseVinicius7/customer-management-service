package br.com.app.customer_management_service.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import br.com.app.customer_management_service.api.model.CustomerDTO;
import br.com.app.customer_management_service.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerControllerTest {
    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void customersGet_ShouldReturnListOfCustomers() {

        List<CustomerDTO> customerList = new ArrayList<>();
        CustomerDTO customer = new CustomerDTO();
        customer.setId(UUID.randomUUID());
        customer.setName("Ronildo");
        customerList.add(customer);

        when(customerService.getCustomers()).thenReturn(customerList);

        ResponseEntity<List<CustomerDTO>> response = customerController.customersGet();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Ronildo", response.getBody().getFirst().getName());
        verify(customerService).getCustomers();
    }

    @Test
    void customersPost_ShouldAddCustomer() {

        CustomerDTO customerToAdd = new CustomerDTO();
        customerToAdd.setName("Ronildo");

        CustomerDTO addedCustomer = new CustomerDTO();
        addedCustomer.setId(UUID.randomUUID());
        addedCustomer.setName("Ronildo");

        when(customerService.addCustomer(any(CustomerDTO.class))).thenReturn(addedCustomer);

        ResponseEntity<CustomerDTO> response = customerController.customersPost(customerToAdd);

        assertEquals(200, response.getStatusCodeValue());

        assertEquals(addedCustomer.getId(), response.getBody().getId());
        assertEquals("Ronildo", response.getBody().getName());

        verify(customerService).addCustomer(customerToAdd);
    }

}
