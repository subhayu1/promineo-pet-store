package com.rimal.petstore.dao;

import com.rimal.petstore.model.PetStoreData;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.rimal.petstore.entity.PetStore;

public interface PetStoreDao extends JpaRepository<PetStore, Long> {

  Optional<PetStore> findPetStoreByPetStoreId(Long petStoreId);}
