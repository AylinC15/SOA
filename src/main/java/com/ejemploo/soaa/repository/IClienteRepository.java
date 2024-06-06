package com.ejemploo.soaa.repository;

import com.ejemploo.soaa.model.Cliente;

import java.util.List;

public interface IClienteRepository {

    public List<Cliente> findAll();
    public int save(Cliente cliente);
    public int update(Cliente cliente);
    public int deletebyName(String name);

}