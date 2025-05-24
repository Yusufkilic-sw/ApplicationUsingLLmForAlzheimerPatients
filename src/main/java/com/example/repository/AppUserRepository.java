package com.example.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.model.AppUser;


public interface AppUserRepository extends MongoRepository<AppUser, String>{

}
