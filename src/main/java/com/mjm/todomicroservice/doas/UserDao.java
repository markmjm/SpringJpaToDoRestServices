package com.mjm.todomicroservice.doas;

import com.mjm.todomicroservice.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserDao extends CrudRepository<User, String> {

    //name strategy
    Optional<User> findUserByEmail(String email);

    //same as above but using Query annotation
    @Query(value="select * from users where email= :email ", nativeQuery=true)
    Optional<User> findUserByTheEmail(String email);

    //same as above
    //native method
    User findOne(String email);

}
