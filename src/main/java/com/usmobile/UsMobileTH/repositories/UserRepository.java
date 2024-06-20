package com.usmobile.UsMobileTH.repositories;

import com.usmobile.UsMobileTH.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepository
 *
 * This class defines the database queries for  the 'user' collection.
 */
@Repository
public interface UserRepository extends MongoRepository<User,String> {
// Empty
}
