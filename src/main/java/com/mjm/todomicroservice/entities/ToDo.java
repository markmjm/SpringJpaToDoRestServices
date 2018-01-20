package com.mjm.todomicroservice.entities;


import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="todos")
@AllArgsConstructor @NoArgsConstructor
@Data
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    //@Getter @Setter   /Replce with @class @Data annotation
    private Integer id;

    //@Getter @Setter
    @Column(name="description")
    @NotNull
    @NotEmpty
    @NotBlank
    private String description;

    //@Getter @Setter
    @Temporal(TemporalType.DATE)
    @Column(name="date")
    private Date date;

    //@Getter @Setter
    @Column(name="priority")
    @NotNull
    @NotEmpty
    @NotBlank
    private String priority;

    //@Getter @Setter
    @Column(name="fk_user")
    @NotNull
    @NotEmpty
    @NotBlank
    private String fkUser;

    @PrePersist
    public void getTimeOperation(){
        this.date = new Date();
    }

}
