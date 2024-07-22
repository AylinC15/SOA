package com.ejemploo.soaa.repository;

import com.ejemploo.soaa.model.Producto;
import com.ejemploo.soaa.model.Proveedor;

import java.util.List;

public interface IProveedorRepository {
    public List<Proveedor> findAll();
    public int save(Proveedor cliente);
    public int update(Proveedor cliente);
    public int deleteByName(String name_proveedor);
    Proveedor findByNombreIgnoreCase(String nombre_proveeor);
    Proveedor findById(int id_proveedor);
    List<Proveedor> findByNombreIgnoreCaseContaining(String nombre_proveedor);
}
