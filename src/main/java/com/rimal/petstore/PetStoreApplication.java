package com.rimal.petstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PetStoreApplication {

  public static void main(String[] args) {
    SpringApplication.run(PetStoreApplication.class, args);
    System.out.println("app started");
  }

}
