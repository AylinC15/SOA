package com.ejemploo.soaa.repository;
import com.ejemploo.soaa.model.Producto;
import com.ejemploo.soaa.model.Proveedor;
import com.ejemploo.soaa.service.IProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService implements IProveedorService {
    @Autowired
    private IProveedorRepository iProveedorRepository;

    @Override
    public List<Proveedor> findAll() {
        List<Proveedor> list;
        try {
            list = iProveedorRepository.findAll();
        }catch (Exception sex){
            throw sex;
        }
        return list;
    }

    @Override
    public int save(Proveedor proveedor){
        int row;
        try{
            row = iProveedorRepository.save(proveedor);
        } catch (Exception sex){
            throw sex;
        }
        return row;
    }

    @Override
    public int update(Proveedor proveedor){
        int row;
        try {
            row = iProveedorRepository.update(proveedor);
        } catch (Exception sex){
            throw sex;
        }
        return row;
    }

    @Override
    public int deleteByName(String nombre_proveedor){
        int row;
        try {
            row = iProveedorRepository.deleteByName(nombre_proveedor);
        } catch (Exception sex){
            throw sex;
        }
        return row;
    }

    @Override
    public Proveedor findByNombreIgnoreCase(String nombre_proveedor){
        Proveedor proveedor;
        try {
            proveedor = iProveedorRepository.findByNombreIgnoreCase(nombre_proveedor);
        } catch (Exception ex){
            throw ex;
        }
        return proveedor;
    }

    @Override
    public List<Proveedor> findByNombreIgnoreCaseContaining(String nombre_proveedor) {
        try {
            return iProveedorRepository.findByNombreIgnoreCaseContaining(nombre_proveedor);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public Proveedor findById(int id_proveedor){
        Proveedor proveedor;
        try {
            proveedor = iProveedorRepository.findById(id_proveedor);
        }catch (Exception ex){
            throw ex;
        }
        return proveedor;
    }
}

