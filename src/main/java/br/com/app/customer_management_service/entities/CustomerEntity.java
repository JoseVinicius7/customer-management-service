package br.com.app.customer_management_service.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Table(name = "tbg_customer")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "address")
    private String address;
    @Column(name = "dateTimeRegistration")
    private LocalDateTime dateTimeRegistration;
    @Column(name = "dateTimeLastChange")
    private LocalDateTime dateTimeLastChange;

}
