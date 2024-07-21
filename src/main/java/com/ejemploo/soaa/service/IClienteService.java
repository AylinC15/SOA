package com.ejemploo.soaa.service;

import com.ejemploo.soaa.model.Cliente;


import java.util.List;

public interface IClienteService {

    public List<Cliente> findAll();
    public int save(Cliente cliente);
    public int update(Cliente cliente);
    public int deleteByName (String name);
    Cliente findByRuc(String ruc);
    Cliente findByNombreIgnoreCase(String nombre);
    Cliente findById(int id);
}
