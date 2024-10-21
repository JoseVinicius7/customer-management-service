package br.com.app.customer_management_service.services.impl;

import br.com.app.customer_management_service.api.model.CustomerDTO;
import br.com.app.customer_management_service.entities.CustomerEntity;
import br.com.app.customer_management_service.handler.APIException;
import br.com.app.customer_management_service.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CustomerServiceImplTest {
    private CustomerRepository customerRepository;
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        customerService = new CustomerServiceImpl(customerRepository);
    }

    @Test
    void deleteCustomer_ShouldDeleteCustomer_WhenCustomerExists() {
        String customerId = UUID.randomUUID().toString();
        CustomerEntity customer = new CustomerEntity();
        customer.setId(UUID.fromString(customerId));

        when(customerRepository.findById(UUID.fromString(customerId))).thenReturn(Optional.of(customer));

        assertDoesNotThrow(() -> customerService.deleteCustomer(customerId));

        verify(customerRepository, times(1)).delete(customer);
    }

    @Test
    void deleteCustomer_ShouldThrowCustomerNotFoundException_WhenCustomerDoesNotExist() {
        String customerId = UUID.randomUUID().toString();

        when(customerRepository.findById(UUID.fromString(customerId))).thenReturn(Optional.empty());

        APIException exception = assertThrows(APIException.class, () -> customerService.deleteCustomer(customerId));
        assertEquals("Cliente não encontrado com ID: " + customerId, exception.getMessage());
    }

    @Test
    void getCustomerById_ShouldReturnCustomerDTO_WhenCustomerExists() {
        String customerId = UUID.randomUUID().toString();
        CustomerEntity customer = new CustomerEntity();
        customer.setId(UUID.fromString(customerId));
        customer.setName("Ninho");

        when(customerRepository.findById(UUID.fromString(customerId))).thenReturn(Optional.of(customer));

        CustomerDTO result = customerService.getCustomerById(customerId);

        assertNotNull(result);
        assertEquals("Ninho", result.getName());
    }

    @Test
    void getCustomerById_ShouldThrowCustomerNotFoundException_WhenCustomerDoesNotExist() {
        String customerId = UUID.randomUUID().toString();

        when(customerRepository.findById(UUID.fromString(customerId))).thenReturn(Optional.empty());

        APIException exception = assertThrows(APIException.class, () -> customerService.getCustomerById(customerId));
        assertEquals("Cliente não encontrado com ID: " + customerId, exception.getMessage());
    }

    @Test
    void addCustomer_ShouldSaveCustomer_WhenCustomerDTOIsValid() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("Ninho");
        customerDTO.setEmail("Ninho@example.com");
        customerDTO.setPhone("73999999999");

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(UUID.randomUUID());
        customerEntity.setName(customerDTO.getName());
        customerEntity.setEmail(customerDTO.getEmail());
        customerEntity.setPhone(customerDTO.getPhone());

        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(customerEntity);

        CustomerDTO result = customerService.addCustomer(customerDTO);

        assertNotNull(result);
        assertEquals(customerDTO.getName(), result.getName());
        assertEquals(customerDTO.getEmail(), result.getEmail());
        assertEquals(customerDTO.getPhone(), result.getPhone());
    }

    @Test
    void addCustomer_ShouldThrowAPIExceptionException_WhenCustomerDTOIsInvalid() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("");
        customerDTO.setEmail("Ninho@example.com");

        APIException exception = assertThrows(APIException.class, () -> customerService.addCustomer(customerDTO));
        assertEquals("É necessario informar o nome do cliente", exception.getMessage());
    }

    @Test
    void addCustomer_ShouldThrowAPIException_WhenEmailIsInvalid() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("Ninho");
        customerDTO.setEmail("");

        APIException exception = assertThrows(APIException.class, () -> customerService.addCustomer(customerDTO));
        assertEquals("O email do cliente é obrigatorio", exception.getMessage());
    }

    @Test
    void updateCustomer_ShouldUpdateCustomer_WhenCustomerExists() {
        String customerId = UUID.randomUUID().toString();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("Ninho Updated");
        customerDTO.setEmail("Ninhoupdated@example.com");
        customerDTO.setPhone("73999999999");

        CustomerEntity existingCustomer = new CustomerEntity();
        existingCustomer.setId(UUID.fromString(customerId));
        existingCustomer.setName("Ninho");
        existingCustomer.setEmail("Ninho@example.com");
        existingCustomer.setPhone("73999999999");

        when(customerRepository.findById(UUID.fromString(customerId))).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(existingCustomer);

        CustomerDTO result = customerService.updateCustomer(customerId, customerDTO);

        assertNotNull(result);
        assertEquals("Ninho Updated", result.getName());
        verify(customerRepository, times(1)).save(existingCustomer);
    }

    @Test
    void updateCustomer_ShouldThrowCustomerNotFoundException_WhenCustomerDoesNotExist() {
        String customerId = UUID.randomUUID().toString();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("Ninho");
        customerDTO.setEmail("Ninho@example.com");
        customerDTO.setPhone("73999999999");

        when(customerRepository.findById(UUID.fromString(customerId))).thenReturn(Optional.empty());

        APIException exception = assertThrows(APIException.class, () -> customerService.updateCustomer(customerId, customerDTO));
        assertEquals("Cliente não encontrado com ID: " + customerId, exception.getMessage());
    }

    @Test
    void getCustomers_ShouldReturnListOfCustomerDTOs() {
        List<CustomerEntity> customerEntityList = new ArrayList<>();
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(UUID.randomUUID());
        customerEntity.setName("John Doe");
        customerEntity.setEmail("johndoe@example.com");

        customerEntityList.add(customerEntity);

        when(customerRepository.findAll()).thenReturn(customerEntityList);

        List<CustomerDTO> result = customerService.getCustomers();

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("johndoe@example.com", result.get(0).getEmail());
        verify(customerRepository).findAll();
    }
}
