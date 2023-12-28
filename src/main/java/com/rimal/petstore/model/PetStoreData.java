package com.rimal.petstore.model;

import com.rimal.petstore.entity.Customer;
import com.rimal.petstore.entity.Employee;
import com.rimal.petstore.entity.PetStore;
import jakarta.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PetStoreData {
  private Long petStoreId;
  private String name;
  private String address;
  private String city;
  private String state;
  private String zip;
  private String phone;
  @ManyToMany(mappedBy = "petStores")
  private Set<PetStoreCustomer> customers = new HashSet<>();
  @ManyToMany(mappedBy = "petStore")
  private Set<PetStoreEmployee> employees = new HashSet<>();

  public PetStoreData(PetStore petStore) {
    this.petStoreId = petStore.getPetStoreId();
    this.name = petStore.getName();
    this.address = petStore.getAddress();
    this.city = petStore.getCity();
    this.state = petStore.getState();
    this.zip = petStore.getZip();
    this.phone = petStore.getPhone();
    if(petStore.getCustomers() != null) {
      this.customers = petStore.getCustomers().stream()
          .map(PetStoreCustomer::new)
          .collect(Collectors.toSet());
    }else {
      this.customers = new HashSet<>();
    }
    if (petStore.getEmployees() != null) {
      this.employees = petStore.getEmployees().stream()
          .map(PetStoreEmployee::new)
          .collect(Collectors.toSet());
    } else {
      this.employees = new HashSet<>();
    }
  }

  @Data
  @NoArgsConstructor
  public static class PetStoreCustomer {
    private Long customerId;
    private String firstName;
    private String lastName;
    private String email;

    public PetStoreCustomer(Customer customer) {
      this.customerId = customer.getCustomerId();
      this.firstName = customer.getFirstName();
      this.lastName = customer.getLastName();
      this.email = customer.getEmail();
    }
  }

  @Data
  @NoArgsConstructor
  public static class PetStoreEmployee {
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String phone;
    private String jobTitle;

    public PetStoreEmployee(Employee employee) {
      this.employeeId = employee.getEmployeeId();
      this.firstName = employee.getFirstName();
      this.lastName = employee.getLastName();
      this.phone = employee.getPhone();
      this.jobTitle = employee.getJobTitle();
    }
  }
}
