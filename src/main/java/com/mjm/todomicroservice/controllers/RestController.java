package com.mjm.todomicroservice.controllers;


import com.mjm.todomicroservice.doas.UserDao;
import com.mjm.todomicroservice.entities.ToDo;
import com.mjm.todomicroservice.entities.User;
import com.mjm.todomicroservice.services.LoginService;
import com.mjm.todomicroservice.services.ToDoService;
import com.mjm.todomicroservice.utilities.JsonResponseBody;
import com.mjm.todomicroservice.utilities.ToDoValidator;
import com.mjm.todomicroservice.utilities.UserNotInDatabaseException;
import com.mjm.todomicroservice.utilities.UserNotLoggedException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private LoginService loginService;

    @Autowired
    private ToDoService toDoService;

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
    //Postman Body (form)
    //email
    //password
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<JsonResponseBody> login(@RequestParam("email") String email,
                                                  @RequestParam("password") String pwd){
        try {
            Optional<User> userr = loginService.getUserFromDb(email, pwd);
            User user = userr.get();
            String jwt = loginService.createJwt(user.getEmail(), user.getName(), new Date());
            return ResponseEntity.status(HttpStatus.OK).header("jwt", jwt).body(new JsonResponseBody(HttpStatus.OK.value(), "Success, User Logged in"));
        }catch (UserNotInDatabaseException e ){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(), "Forbidden: " + e.getMessage()));
        } catch (UnsupportedEncodingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST: " + e.getMessage()));

        }
    }
    //Postman Body (form)
    //jwt   ---- you can get this from the intial login and use if not expired.
    @RequestMapping("/showToDos")
    public ResponseEntity<JsonResponseBody> showToDos(HttpServletRequest request){
        try {
            Map<String, Object> userData = loginService.verifyJwtAndGetData(request);
            return ResponseEntity.status(HttpStatus.OK).body(new JsonResponseBody(HttpStatus.OK.value(),
                    toDoService.getToDos((String) userData.get("email"))));
        }catch (UserNotLoggedException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(),
                    "User not logged in: " + e.getMessage()));
        } catch (UnsupportedEncodingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(),
                    "User not logged in: " + e.getMessage()));
        }catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new JsonResponseBody(HttpStatus.GATEWAY_TIMEOUT.value(),
                    "Token Expired: " + e.getMessage()));
        }
    }

    //Postman Body (form)
    //id
    //description
    //date
    //priority
    //fkUser
    //password
    //
    //Postman header
    //jwt
    @RequestMapping(value = "/newToDo", method = RequestMethod.POST)
    public ResponseEntity<JsonResponseBody> newToDo(HttpServletRequest request, @Valid ToDo toDo, BindingResult result){
        ToDoValidator validator = new ToDoValidator();
        validator.validate(toDo, result);
        if(result.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(), "Data not valid:\n" + result.toString()));
        }
        try {
            loginService.verifyJwtAndGetData(request);
            return ResponseEntity.status(HttpStatus.OK).body(new JsonResponseBody(HttpStatus.OK.value(), toDoService.addToDo(toDo)));
        }catch (UserNotLoggedException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(),
                    "User not logged in: " + e.getMessage()));
        } catch (UnsupportedEncodingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(),
                    "User not logged in: " + e.getMessage()));
        }catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new JsonResponseBody(HttpStatus.GATEWAY_TIMEOUT.value(),
                    "Token Expired: " + e.getMessage()));
        }
    }

    @RequestMapping("/deleteToDo/{id}")
    public ResponseEntity<JsonResponseBody> deleteToDo(HttpServletRequest request, @PathVariable(name = "id") Integer id) {

        try {
            loginService.verifyJwtAndGetData(request);
            toDoService.deleteToDo(id);
            return ResponseEntity.status(HttpStatus.OK).body(new JsonResponseBody(HttpStatus.OK.value(), "ToDo Deleted"));
        } catch (UnsupportedEncodingException e) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new JsonResponseBody(HttpStatus.GATEWAY_TIMEOUT.value(),
                    "Token Expired: " + e.getMessage()));
        } catch (UserNotLoggedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(),
                    "User not logged in: " + e.getMessage()));
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new JsonResponseBody(HttpStatus.GATEWAY_TIMEOUT.value(),
                    "Token Expired: " + e.getMessage()));
        }
    }
}
