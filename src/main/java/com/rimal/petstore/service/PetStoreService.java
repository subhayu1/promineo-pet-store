package com.rimal.petstore.service;

import com.rimal.petstore.dao.PetStoreDao;
import com.rimal.petstore.entity.PetStore;
import com.rimal.petstore.model.PetStoreData;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetStoreService {

  @Autowired
  private PetStoreDao petStoreDao;

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

}

