package com.henry.repository.primary;

import com.henry.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface PrimaryRepository extends MongoRepository<User, String> {
}
