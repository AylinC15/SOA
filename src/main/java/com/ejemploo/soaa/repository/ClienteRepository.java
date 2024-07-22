package com.ejemploo.soaa.repository;

import com.ejemploo.soaa.model.Cliente;
import com.ejemploo.soaa.model.Devolucion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository
public class ClienteRepository implements IClienteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Cliente> findAll() {
        String SQL = "SELECT id_cliente, nombre, telefono, ruc, direccion FROM cliente WHERE estado = 1";
        return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Cliente.class));
    }

    @Override
    public int save(Cliente cliente) {
        String SQL = "INSERT INTO cliente (nombre,telefono, ruc,direccion) VALUES (?,?,?,?)";
        return jdbcTemplate.update(SQL, new Object[]{cliente.getNombre(), cliente.getTelefono(), cliente.getRUC(), cliente.getDireccion()});
    }

    @Override
    public int update(Cliente cliente) {
        String SQL = "UPDATE cliente SET nombre=?, telefono = ?, RUC = ?,direccion= ? WHERE id_cliente = ?";
        return jdbcTemplate.update(SQL, new Object[]{cliente.getNombre(), cliente.getTelefono(), cliente.getRUC(), cliente.getDireccion(), cliente.getId_cliente()});
    }

    @Override
    public int deletebyName(String nombre) {
        String SQL = "UPDATE cliente SET estado= 0 WHERE nombre=?";
        return jdbcTemplate.update(SQL, new Object[]{nombre});
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
    public Cliente findByNombreIgnoreCase(String nombre) {
        try {
            String SQL = "SELECT * FROM cliente WHERE LOWER(nombre) = LOWER(?)";
            return jdbcTemplate.queryForObject(SQL, BeanPropertyRowMapper.newInstance(Cliente.class), nombre);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Cliente findById(int id_cliente) {
        String sql = "SELECT * FROM cliente WHERE id_cliente = ?";
        return jdbcTemplate.query(sql, new Object[]{id_cliente}, new ResultSetExtractor<Cliente>() {
            @Override
            public Cliente extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setId_cliente(rs.getInt("id_cliente"));
                    cliente.setNombre(rs.getString("nombre"));
                    cliente.setDireccion(rs.getString("direccion"));
                    cliente.setRUC(rs.getString("RUC"));
                    cliente.setTelefono(rs.getString("telefono"));
                    return cliente;
                }
                return null;
            }
        });
    }

    @Override
    public List<Cliente> findByRucContaining(String ruc) {
        try {
            String SQL = "SELECT * FROM cliente WHERE RUC LIKE ?";
            return jdbcTemplate.query(SQL, new Object[]{"%" + ruc + "%"}, new BeanPropertyRowMapper<>(Cliente.class));
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Cliente> findByNombreIgnoreCaseContaining(String nombre) {
        try {
            String SQL = "SELECT * FROM cliente WHERE LOWER(nombre) LIKE LOWER(?)";
            return jdbcTemplate.query(SQL, new Object[]{"%" + nombre + "%"}, new BeanPropertyRowMapper<>(Cliente.class));
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }


}
