package com.ejemploo.soaa.repository;
import com.ejemploo.soaa.model.Proveedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProveedorRepository implements IProveedorRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Proveedor> findAll() {
        String SQL = "SELECT id_proveedor, nombre_proveedor, telefono from proveedor WHERE estado = 1";
        return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Proveedor.class));
    }

    @Override
    public int save(Proveedor proveedor) {
        String SQL = "INSERT INTO proveedor (nombre_proveedor, telefono) VALUES (?, ?)";
        return jdbcTemplate.update(SQL, new Object[]{proveedor.getNombre_proveedor(), proveedor.getTelefono()});
    }

    @Override
    public int update(Proveedor proveedor){
        String SQL = "UPDATE proveedor SET nombre_proveedor=?, telefono=? WHERE id_proveedor=?";
        return jdbcTemplate.update(SQL, new Object[]{proveedor.getNombre_proveedor(),proveedor.getTelefono(),proveedor.getId_proveedor()});
    }

    @Override
    public int deleteByName(String nombre_proveedor){
        String SQL = "UPDATE proveedor SET estado = 0 WHERE nombre_proveedor=?";
        return  jdbcTemplate.update(SQL, new Object[]{nombre_proveedor});
    }

    @Override
    public Proveedor findByNombreIgnoreCase(String nombre_proveedor){
        try {
            String SQL = "SELECT * FROM proveedor WHERE UPPER(nombre_proveedor) = UPPER(?)";
            return jdbcTemplate.queryForObject(SQL, BeanPropertyRowMapper.newInstance(Proveedor.class),nombre_proveedor);
        } catch (EmptyResultDataAccessException e){
            return null;
        }

    }
}
