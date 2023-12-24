package com.rimal.petstore.controller;


import com.rimal.petstore.model.PetStoreData;
import com.rimal.petstore.service.PetStoreService;
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

}
