package com.ejemploo.soaa.model;


import lombok.Data;

@Data
public class Cliente {

    int id_cliente;
    String name;
    int telefono;
    int RUC;
    String direccion;
    int estado;

}
