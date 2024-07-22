package com.ejemploo.soaa.repository;

import com.ejemploo.soaa.model.Cliente;
import com.ejemploo.soaa.model.Producto;
import com.ejemploo.soaa.service.IAlmacenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlmacenService implements IAlmacenService {

    @Autowired
    private IAlmacenRepository iAlmacenRepository;

    @Override
    public List<Producto> findAll() {
        List<Producto> list;
        try {
            list = iAlmacenRepository.findAll();
        }catch (Exception sex){
            throw sex;
        }
        return list;
    }

    @Override
    public int save(Producto producto) {
        int row;
        try {
            row = iAlmacenRepository.save(producto);
        }catch (Exception sex){
            throw sex;
        }

        return row;
    }

    @Override
    public int update(Producto producto) {
        int row;
        try {
            row = iAlmacenRepository.update(producto);
        }catch (Exception sex){
            throw sex;
        }

        return row;
    }



    @Override
    public int deleteByName(String name) {
        int row;
        try {
            row = iAlmacenRepository.deleteByName(name);
        }catch (Exception sex){
            throw sex;
        }
        return row;
    }

    @Override
    public Producto findById(int id_producto){
        Producto producto;
        try {
            producto = iAlmacenRepository.findById(id_producto);
        }catch (Exception ex){
            throw ex;
        }
        return producto;
    }
}
