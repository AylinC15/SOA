package com.ejemploo.soaa.repository;

import com.ejemploo.soaa.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AlmacenRepository implements IAlmacenRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public List<Producto> findAll() {
        String SQL = "SELECT * FROM producto WHERE estado = 1";
        return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Producto.class));
    }

    @Override
    public int save(Producto producto) {
        String SQL = "INSERT INTO producto VALUES (?,?,?,?,?)";
        return jdbcTemplate.update(SQL, new Object[]{producto.getName(),producto.getMarca(),producto.getCantidad(),producto.getPrecio(),producto.getEstado()});
    }

    @Override
    public int update(Producto producto) {
        String SQL = "UPDATE producto SET name=?,marca=?,cantidad=?,precio=?";
        return jdbcTemplate.update(SQL, new Object[]{producto.getName(),producto.getMarca(),producto.getCantidad(),producto.getPrecio(),producto.getEstado()});
    }

    @Override
    public int deleteByName(String name) {
        String SQL = "UPDATE producto SET estado=1 WHERE name=?";
        return jdbcTemplate.update(SQL, new Object[]{name});
    }
}
