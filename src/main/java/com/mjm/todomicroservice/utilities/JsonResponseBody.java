package com.mjm.todomicroservice.utilities;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class JsonResponseBody {

    private int server;
    private Object response;
}

//http response -> java object ResponseEntity<JsonResponseBody>

// Containing ...
//header (jwt)
//body  - html page or JsonMessage(JsonResponseBody(int server, Object response)
//
//JsonMessage library
//Add io.jsonwebtoken to POM