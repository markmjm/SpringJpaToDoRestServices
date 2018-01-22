package com.mjm.todomicroservice.services;

import com.mjm.todomicroservice.doas.ToDoDao;
import com.mjm.todomicroservice.entities.ToDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoServiceImpl implements ToDoService {

    @Autowired
    ToDoDao toDoDao;

    @Override
    public List<ToDo> getToDos(String email) {
        return toDoDao.findByFkUser(email);
    }

    @Override
    public ToDo addToDo(ToDo toDo) {
        return toDoDao.save(toDo);
    }

    @Override
    public void deleteToDo(Integer id) {
        toDoDao.delete(id);
    }
}
