package com.ejemploo.soaa.repository;

import com.ejemploo.soaa.model.Cliente;
import com.ejemploo.soaa.model.Producto;
import com.ejemploo.soaa.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService implements IClienteService {

    @Autowired
    private IClienteRepository iClienteRepository;

    @Override
    public List<Cliente> findAll() {
        List<Cliente> list;
        try {
            list = iClienteRepository.findAll();
        }catch (Exception sex){
            throw sex;
        }
        return list;
    }

    @Override
    public int save(Cliente cliente) {
        int row;
        try {
            row = iClienteRepository.save(cliente);
        }catch (Exception sex){
            throw sex;
        }

        return row;
    }

    @Override
    public int update(Cliente cliente) {
        int row;
        try {
            row = iClienteRepository.update(cliente);
        }catch (Exception sex){
            throw sex;
        }

        return row;
    }

    @Override
    public int deleteByName(String name) {
        int row;
        try {
            row = iClienteRepository.deletebyName(name);
        }catch (Exception sex){
            throw sex;
        }

        return row;
    }

    @Override
    public Cliente findByRuc(String ruc) {
        Cliente cliente;
        try {
            cliente = iClienteRepository.findByRuc(ruc);
        } catch (Exception ex) {
            throw ex;
        }
        return cliente;
    }

    @Override
    public Cliente findByNombreIgnoreCase(String nombre) {
        Cliente cliente;
        try {
            cliente = iClienteRepository.findByNombreIgnoreCase(nombre);
        } catch (Exception ex) {
            throw ex;
        }
        return cliente;
    }

}
