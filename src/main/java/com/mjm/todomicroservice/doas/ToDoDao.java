package com.mjm.todomicroservice.doas;

import com.mjm.todomicroservice.entities.ToDo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ToDoDao extends CrudRepository<ToDo, Integer> {

    //name strategy
    List<ToDo> findByFkUser(String email);

}
