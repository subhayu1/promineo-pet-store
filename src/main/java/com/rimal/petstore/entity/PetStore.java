package com.rimal.petstore.entity;

import jakarta.persistence.*;
import java.util.Set;
import lombok.Data;

@Data
@Entity
@Table(name = "pet_store")
public class PetStore {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long petStoreId;

  @Column(name = "name")
  private String name;

  @Column(name = "address")
  private String address;

  @Column(name = "city")
  private String city;

  @Column(name = "state")
  private String state;

  @Column(name = "zip")
  private String zip;

  @Column(name = "phone")
  private String phone;

  @OneToMany(mappedBy = "petStore", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Employee> employees;

  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(
      name = "pet_store_customer",
      joinColumns = @JoinColumn(name = "pet_store_id"),
      inverseJoinColumns = @JoinColumn(name = "customer_id"))
  private Set<Customer> customers;
}

