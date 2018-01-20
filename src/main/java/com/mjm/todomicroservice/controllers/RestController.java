package com.mjm.todomicroservice.controllers;


import com.mjm.todomicroservice.doas.UserDao;
import com.mjm.todomicroservice.entities.ToDo;
import com.mjm.todomicroservice.entities.User;
import com.mjm.todomicroservice.utilities.ToDoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private UserDao userDao;

    @RequestMapping("/hello")
    public String sayHello(){
        return "hello";
    }

    @RequestMapping("/user")
    public User getUser(){
        return new User("lucky@lucky.com", "Lucky Guy", "axxxxxx");
    }

    @RequestMapping(value = "/todoInput1", method = RequestMethod.POST)
    public String todoInput1( ToDo toDo) {
        return toDo.toString();
    }

    @RequestMapping(value = "/todoInput2", method = RequestMethod.POST)
    public String todoInput2(@Valid ToDo toDo) {
        return toDo.toString();
    }

    //Data binding with JSR-303 validation and binding results
    @RequestMapping(value = "/todoInput3", method = RequestMethod.POST)
    public String todoInput3(@Valid ToDo toDo, BindingResult result) {
        if(result.hasErrors()){
            return "There is an error in call to todoInput3: \n" + result.toString();
        }
        return toDo.toString();
    }

    //Data binding with Spring Validator
    @RequestMapping(value = "/todoInput4", method = RequestMethod.POST)
    public String todoInput4(ToDo toDo, BindingResult result) {
        ToDoValidator validator = new ToDoValidator();
        validator.validate(toDo, result);
        if(result.hasErrors()){
            return "There is an error in call to todoInput3: \n" + result.toString();
        }
        return toDo.toString();
    }


    //Data binding with JSR-303 and Spring Validator
    @RequestMapping(value = "/todoInput5", method = RequestMethod.POST)
    public String todoInput5(@Valid ToDo toDo, BindingResult result) {
        ToDoValidator validator = new ToDoValidator();
        validator.validate(toDo, result);
        if(result.hasErrors()){
            return "There is an error in call to todoInput3: \n" + result.toString();
        }
        return toDo.toString();
    }
}
