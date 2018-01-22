package com.mjm.todomicroservice.services;

import com.mjm.todomicroservice.entities.User;
import com.mjm.todomicroservice.utilities.UserNotInDatabaseException;
import com.mjm.todomicroservice.utilities.UserNotLoggedException;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

public interface LoginService {
    //A container object which may or may not contain a non-null value.
    // If a value is present, isPresent() will return true and get() will return the value.
    public Optional<User> getUserFromDb (String email, String pwd) throws UserNotInDatabaseException;
    String createJwt(String email, String name, Date Date) throws UnsupportedEncodingException;
    Map<String, Object> verifyJwtAndGetData(HttpServletRequest request) throws UnsupportedEncodingException, UserNotLoggedException;
}
