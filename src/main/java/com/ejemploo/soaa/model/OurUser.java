package com.ejemploo.soaa.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "usuario")
public class OurUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id_usuario;
    @Column(unique = true)
    String email;
    String password;
    String roles;


}
