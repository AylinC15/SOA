package com.ejemploo.soaa.service;
import com.ejemploo.soaa.model.Producto;
import com.ejemploo.soaa.model.Proveedor;
import java.util.List;

public interface IProveedorService {
    public List<Proveedor> findAll();
    public int save(Proveedor proveedor);
    public int update(Proveedor proveedor);
    public int deleteByName(String nombre_proveedor);
    Proveedor findByNombreIgnoreCase(String nombre_proveedor);
    Proveedor findById(int id_proveedor);
}
