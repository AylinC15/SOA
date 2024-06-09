package com.ejemploo.soaa.repository;


import com.ejemploo.soaa.model.Empleado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmpleadoRepository implements IEmpleadoRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Empleado> findAll() {
        String SQL = "SELECT * FROM empleado WHERE estado = 1";
        return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Empleado.class));
    }

    @Override
    public int save(Empleado empleado){
        String SQL = "INSERT INTO empleado VALUES (?,?,?,?,?,?)";
        return jdbcTemplate.update(SQL, new Object[]{empleado.getCodigo(), empleado.getName(), empleado.getTelefono(), empleado.getDNI(), empleado.getDireccion(), empleado.getEstado()});
    }

    @Override
    public int update(Empleado empleado) {
        String SQL = "UPDATE empleado SET codigo=?, name=?, telefono=?, DNI=?, direccion=? WHERE id_empleado=?";
        return jdbcTemplate.update(SQL, empleado.getCodigo(), empleado.getName(), empleado.getTelefono(), empleado.getDNI(), empleado.getDireccion(), empleado.getId_empleado());
    }


    public int deletebyNameEmpleado(String name){
        String SQL = "UPDATE empleado SET estado=0 WHERE name=?";
        return jdbcTemplate.update(SQL,new Object[]{name});
    }

    @Override
    public Empleado findByDni(String dni) {
        try {
            String SQL = "SELECT * FROM empleado WHERE DNI = ?";
            return jdbcTemplate.queryForObject(SQL, BeanPropertyRowMapper.newInstance(Empleado.class), dni);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Empleado findByNombreIgnoreCase(String name) {
        try {
            String SQL = "SELECT * FROM empleado WHERE UPPER(name) = UPPER(?)";
            return jdbcTemplate.queryForObject(SQL, BeanPropertyRowMapper.newInstance(Empleado.class), name);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


}
