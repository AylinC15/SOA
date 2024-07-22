package com.ejemploo.soaa.repository;

import com.ejemploo.soaa.model.Cliente;

import java.util.List;

public interface IClienteRepository {

    public List<Cliente> findAll();
    public int save(Cliente cliente);
    public int update(Cliente cliente);
    public int deletebyName(String name);
    Cliente findByRuc(String ruc);
    Cliente findByNombreIgnoreCase(String nombre);
    Cliente findById(int id);
    List<Cliente> findByRucContaining(String ruc);
    List<Cliente> findByNombreIgnoreCaseContaining(String nombre);
}
