package com.rimal.petstore.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import lombok.Data;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "customer")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long customerId;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "email")
  private String email;

  @ManyToMany(mappedBy = "customers", cascade = CascadeType.PERSIST)
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private Set<PetStore> petStores = new HashSet<>();
}




