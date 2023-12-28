package com.rimal.petstore.service;

import com.rimal.petstore.dao.CustomerDao;
import com.rimal.petstore.dao.EmployeeDao;
import com.rimal.petstore.dao.PetStoreDao;
import com.rimal.petstore.entity.Customer;
import com.rimal.petstore.entity.Employee;
import com.rimal.petstore.entity.PetStore;
import com.rimal.petstore.model.PetStoreData;
import com.rimal.petstore.model.PetStoreData.PetStoreCustomer;
import com.rimal.petstore.model.PetStoreData.PetStoreEmployee;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetStoreService {

  @Autowired
  private PetStoreDao petStoreDao;
  @Autowired
   private EmployeeDao employeeDao;
  @Autowired
  private CustomerDao customerDao;

  public PetStoreData savePetStore(PetStoreData petStoreData) {
    // Step a: Find or create PetStore object
    PetStore petStore = findOrCreatePetStore(petStoreData.getPetStoreId());

    // Step b: Copy fields from PetStoreData to PetStore
    copyPetStoreFields(petStore, petStoreData);

    // Step c: Save the PetStore object and return a new PetStoreData object
    PetStore savedPetStore = petStoreDao.save(petStore);
    return new PetStoreData(savedPetStore); // Assumes constructor taking PetStore as a parameter
  }

  private PetStore findOrCreatePetStore(Long petStoreId) {
    if (petStoreId == null) {
      return new PetStore(); // Create a new PetStore if ID is null
    } else {
      return petStoreDao.findPetStoreByPetStoreId(petStoreId)
          .orElseThrow(() -> new NoSuchElementException("No pet store found with ID: " + petStoreId));
    }
  }

  private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
    // Copy matching fields from PetStoreData to PetStore
    petStore.setName(petStoreData.getName());
    petStore.setAddress(petStoreData.getAddress());
    petStore.setCity(petStoreData.getCity());
    petStore.setState(petStoreData.getState());
    petStore.setZip(petStoreData.getZip());
    petStore.setPhone(petStoreData.getPhone());
    // Do not copy customers or employees
  }

  @Transactional
  public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
    PetStore petStore = findPetStoreById(petStoreId);
    Long employeeId = petStoreEmployee.getEmployeeId();
    Employee employee = findOrCreateEmployee(petStoreId, employeeId);
    copyEmployeeFields(employee, petStoreEmployee);
    employee.setPetStore(petStore);
    petStore.getEmployees().add(employee);
    Employee dbEmployee = employeeDao.save(employee);
    return new PetStoreEmployee(dbEmployee); // Assuming a constructor or a method to convert Employee to PetStoreEmployee
  }

  private PetStore findPetStoreById(Long petStoreId) {
    return petStoreDao.findPetStoreByPetStoreId(petStoreId)
        .orElseThrow(() -> new NoSuchElementException("No pet store found with ID: " + petStoreId));
  }

  private Employee findOrCreateEmployee(Long petStoreId, Long employeeId) {
    return employeeId == null ? new Employee() : findEmployeeById(petStoreId, employeeId);
  }

  private Employee findEmployeeById(Long petStoreId, Long employeeId) {
    Employee employee = employeeDao.findById(employeeId).orElseThrow(
        () -> new NoSuchElementException("Employee not found with ID: " + employeeId)
    );
    if (!employee.getPetStore().getPetStoreId().equals(petStoreId)) {
      throw new IllegalArgumentException("Employee does not belong to the given pet store.");
    }
    return employee;
  }

  private void copyEmployeeFields(Employee employee, PetStoreData.PetStoreEmployee petStoreEmployee) {
    // Copying fields from PetStoreEmployee DTO to Employee entity
    employee.setFirstName(petStoreEmployee.getFirstName());
    employee.setLastName(petStoreEmployee.getLastName());
    employee.setPhone(petStoreEmployee.getPhone());
    employee.setJobTitle(petStoreEmployee.getJobTitle());
  }

  @Transactional
  public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreData.PetStoreCustomer petStoreCustomer) {
    PetStore petStore = findPetStoreById(petStoreId);
    Long customerId = petStoreCustomer.getCustomerId();
    Customer customer = findOrCreateCustomer(petStoreId, customerId);
    copyCustomerFields(customer, petStoreCustomer,petStore);
    customer.getPetStores().add(petStore);
    petStore.getCustomers().add(customer);// Add the pet store to the customer
    customer.setPetStores(Set.of(petStore));
    petStore.getCustomers().add(customer); // Add the customer to the pet store
    Customer dbCustomer = customerDao.save(customer);
    petStoreDao.save(petStore);
    return new PetStoreData.PetStoreCustomer(dbCustomer); // Convert to DTO
  }


  private void copyCustomerFields(Customer customer, PetStoreData.PetStoreCustomer petStoreCustomer, PetStore petStore) {
    customer.setFirstName(petStoreCustomer.getFirstName());
    customer.setLastName(petStoreCustomer.getLastName());
    customer.setEmail(petStoreCustomer.getEmail());

  }


  private Customer findOrCreateCustomer(Long petStoreId, Long customerId) {
    return customerId == null ? new Customer() : findCustomerById(petStoreId, customerId);
  }
  private Customer findCustomerById(Long petStoreId, Long customerId) {
    // Fetch the customer by ID using the customerDao
    Customer customer = customerDao.findById(customerId)
        .orElseThrow(() -> new NoSuchElementException("Customer not found with ID: " + customerId));

    // Check if the customer is associated with the given pet store
    boolean isAssociatedWithStore = customer.getPetStores().stream()
        .anyMatch(store -> store.getPetStoreId().equals(petStoreId));

    if (!isAssociatedWithStore) {
      throw new IllegalArgumentException("Customer with ID " + customerId + " is not associated with pet store ID " + petStoreId);
    }

    return customer;
  }


@org.springframework.transaction.annotation.Transactional(readOnly = true)
  public List<PetStoreData> retrieveAllPetStores() {
    List<PetStore> petStores = petStoreDao.findAll();
    return petStores.stream()
        .map(this::convertToPetStoreData)
        .collect(Collectors.toList());
  }

  private PetStoreData convertToPetStoreData(PetStore petStore) {
    PetStoreData petStoreData = new PetStoreData();
    // Copy necessary fields from PetStore entity to PetStoreData DTO
    petStoreData.setPetStoreId(petStore.getPetStoreId());
    petStoreData.setName(petStore.getName());
    petStoreData.setAddress(petStore.getAddress());
    petStoreData.setCity(petStore.getCity());
    petStoreData.setState(petStore.getState());
    petStoreData.setZip(petStore.getZip());
    petStoreData.setPhone(petStore.getPhone());
    // Do not copy customers or employees

    petStoreData.setCustomers(null); // Remove customer details
    petStoreData.setEmployees(null); // Remove employee details
    return petStoreData;
  }
  private PetStoreData convertToPetStoreDataWithCustomersAndEmployees(PetStore petStore) {
    // Convert PetStore entity to PetStoreData DTO
    // This method should copy all the necessary fields from the PetStore entity
    // to the PetStoreData DTO, including customer and employee information
    // Example:
    PetStoreData petStoreData = new PetStoreData();
    petStoreData.setPetStoreId(petStore.getPetStoreId());
    petStoreData.setName(petStore.getName());
    petStoreData.setAddress(petStore.getAddress());
    petStoreData.setCity(petStore.getCity());
    petStoreData.setState(petStore.getState());
    petStoreData.setZip(petStore.getZip());
    petStoreData.setPhone(petStore.getPhone());
    // Copy customers
    petStoreData.setCustomers(petStore.getCustomers().stream()
        .map(customer -> {
          PetStoreCustomer petStoreCustomer = new PetStoreCustomer();
          petStoreCustomer.setCustomerId(customer.getCustomerId());
          petStoreCustomer.setFirstName(customer.getFirstName());
          petStoreCustomer.setLastName(customer.getLastName());
          petStoreCustomer.setEmail(customer.getEmail());
          return petStoreCustomer;
        })
        .collect(Collectors.toSet()));
    // Copy employees
    petStoreData.setEmployees(petStore.getEmployees().stream()
        .map(employee -> {
          PetStoreEmployee petStoreEmployee = new PetStoreEmployee();
          petStoreEmployee.setEmployeeId(employee.getEmployeeId());
          petStoreEmployee.setFirstName(employee.getFirstName());
          petStoreEmployee.setLastName(employee.getLastName());
          petStoreEmployee.setPhone(employee.getPhone());
          petStoreEmployee.setJobTitle(employee.getJobTitle());
          return petStoreEmployee;
        })
        .collect(Collectors.toSet()));
    return petStoreData;
  }


  public PetStoreData retrievePetStoreById(Long petStoreId) {
    PetStore petStore = petStoreDao.findPetStoreByPetStoreId(petStoreId)
        .orElseThrow(() -> new NoSuchElementException("No pet store found with ID: " + petStoreId));
    return convertToPetStoreDataWithCustomersAndEmployees(petStore);
  }

  public void deletePetStoreById(Long petStoreId) {
    PetStore petStore = petStoreDao.findById(petStoreId)
        .orElseThrow(() -> new NoSuchElementException("PetStore not found with ID: " + petStoreId));
    petStoreDao.delete(petStore);

  }
}

