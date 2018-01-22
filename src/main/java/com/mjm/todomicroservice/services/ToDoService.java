package com.mjm.todomicroservice.services;

import com.mjm.todomicroservice.entities.ToDo;

import java.util.List;

public interface ToDoService {
    public List<ToDo> getToDos(String email);
    public ToDo addToDo(ToDo toDo);
    public void deleteToDo(Integer id);
}
