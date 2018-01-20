package com.mjm.todomicroservice.entities;


import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

//LOMBOK
//JPA
//VALIDATION JSR-383 - Hibernate validator - DATA BINDING email, name, password -> new User(email, name, password)


@Data
@Entity
@AllArgsConstructor  @NoArgsConstructor
@Table(name="users")
public class User {

    @Id
    @Column(name="email")
    //@Getter @Setter  //Replace with class @Data annotation
    @NotNull @NotEmpty @NotBlank
    @Email
    private String email;

    @Column(name="name")
    //@Getter @Setter
    @NotNull @NotEmpty @NotBlank
    private String name;

    @Column(name="password")
    //@Getter @Setter
    @NotNull @NotEmpty @NotBlank
    private String password;

}
