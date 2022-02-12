package com.henry.repository.secondary;

import com.henry.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface SecondaryRepository extends MongoRepository<User, String> {
}
