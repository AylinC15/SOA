package com.ejemploo.soaa.repository;

import com.ejemploo.soaa.model.Producto;

import java.util.List;

public interface IAlmacenRepository {

    public List<Producto> findAll();
    public int save(Producto producto);
    public int update(Producto producto);
    public int deleteByName (String name);
    Producto findById(int id_producto);
}
