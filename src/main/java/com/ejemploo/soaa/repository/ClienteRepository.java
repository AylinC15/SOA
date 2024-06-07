package com.ejemploo.soaa.repository;

import com.ejemploo.soaa.model.Cliente;
import com.ejemploo.soaa.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ClienteRepository implements IClienteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Cliente> findAll() {
        String SQL = "SELECT * FROM cliente WHERE estado = 1";
        return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Cliente.class));
    }

    @Override
    public int save(Cliente cliente) {
        String SQL = "INSERT INTO cliente VALUES (?,?,?,?,?)";
        return jdbcTemplate.update(SQL, new Object[]{cliente.getName(), cliente.getTelefono(), cliente.getRUC(), cliente.getDireccion(), cliente.getEstado()});
    }

    @Override
    public int update(Cliente cliente) {
        String SQL = "UPDATE cliente SET name=?, telefono = ?, RUC = ?,direccion= ? WHERE id_cliente = ?";
        return jdbcTemplate.update(SQL, new Object[]{cliente.getName(), cliente.getTelefono(), cliente.getRUC(), cliente.getDireccion(), cliente.getId_cliente()});
    }

    @Override
    public int deletebyName(String name) {
        String SQL = "UPDATE cliente SET estado= 0 WHERE name=?";
        return jdbcTemplate.update(SQL, new Object[]{name});
    }

    @Override
    public Cliente findByRuc(String ruc) {
        try {
            String SQL = "SELECT * FROM cliente WHERE RUC = ?";
            return jdbcTemplate.queryForObject(SQL, BeanPropertyRowMapper.newInstance(Cliente.class), ruc);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Cliente findByNombreIgnoreCase(String name) {
        try {
            String SQL = "SELECT * FROM cliente WHERE UPPER(name) = UPPER(?)";
            return jdbcTemplate.queryForObject(SQL, BeanPropertyRowMapper.newInstance(Cliente.class), name);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
