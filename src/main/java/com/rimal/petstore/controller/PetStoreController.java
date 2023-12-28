package com.rimal.petstore.controller;


import com.rimal.petstore.model.PetStoreData;
import com.rimal.petstore.model.PetStoreData.PetStoreCustomer;
import com.rimal.petstore.model.PetStoreData.PetStoreEmployee;
import com.rimal.petstore.service.PetStoreService;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {

  @Autowired
  private PetStoreService petStoreService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public PetStoreData createPetStore(@RequestBody PetStoreData petStoreData) {
    log.info("Creating new pet store: {}", petStoreData);
    // Call the service method to save the pet store data
    // It's assumed that the service method returns the saved PetStoreData
    // This might include generated IDs or other data modified/added during the save process
    return petStoreService.savePetStore(petStoreData);
  }
  @PutMapping("/{petStoreId}")
  public PetStoreData updatePetStore(@PathVariable Long petStoreId, @RequestBody PetStoreData petStoreData) {
    log.info("Updating pet store with ID {}: {}", petStoreId, petStoreData);
    petStoreData.setPetStoreId(petStoreId); // Set the pet store ID from the path variable
    return petStoreService.savePetStore(petStoreData); // Save the updated pet store data
  }

  @PostMapping("/{petStoreId}/employee")
  @ResponseStatus(HttpStatus.CREATED)
  public PetStoreEmployee addEmployeeToStore(@PathVariable Long petStoreId, @RequestBody PetStoreEmployee petStoreEmployee) {
    log.info("Adding employee to store with ID {}:{}", petStoreId, petStoreEmployee);

    // Assume PetStoreService has a method saveEmployee that accepts PetStoreEmployee and petStoreId
    return petStoreService.saveEmployee(petStoreId, petStoreEmployee);
  }
  @PostMapping("/{petStoreId}/customer")
  @ResponseStatus(HttpStatus.CREATED)
  public PetStoreData.PetStoreCustomer addCustomerToStore(@PathVariable Long petStoreId, @RequestBody PetStoreCustomer petStoreCustomer) {
    log.info("Adding customer to store with ID {}:{}", petStoreId, petStoreCustomer);

    // Assume PetStoreService has a method saveCustomer that accepts PetStoreCustomer and petStoreId
    return petStoreService.saveCustomer(petStoreId, petStoreCustomer);
  }
  @GetMapping
  public List<PetStoreData> getAllPetStores() {
    return petStoreService.retrieveAllPetStores();
  }
@GetMapping("/{petStoreId}")
public PetStoreData getPetStoreById(@PathVariable Long petStoreId) {
    return petStoreService.retrievePetStoreById(petStoreId);
  }
  @DeleteMapping("/{petStoreId}")
  public Map<String, String> deletePetStoreById(@PathVariable Long petStoreId) {
    log.info("Deleting pet store with ID: {}", petStoreId);
    petStoreService.deletePetStoreById(petStoreId);
    return Collections.singletonMap("message", "Pet store deleted successfully");
  }


}
