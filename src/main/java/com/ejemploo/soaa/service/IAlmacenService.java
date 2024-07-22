package com.ejemploo.soaa.service;

import com.ejemploo.soaa.model.Empleado;
import com.ejemploo.soaa.model.Producto;

import java.util.List;

public interface IAlmacenService {

    public List<Producto> findAll();
    public int save(Producto producto);
    public int update(Producto producto);
    public int deleteByName (String name);
    Producto findByIdProducto(int id_empleado);


}
