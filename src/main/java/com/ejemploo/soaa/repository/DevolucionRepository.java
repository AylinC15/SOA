package com.ejemploo.soaa.repository;


import com.ejemploo.soaa.model.Devolucion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class DevolucionRepository implements  IDevolucionRepository{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Devolucion> findAll() {
        String SQL = "SELECT d.id_devolucion, d.fecha, d.producto, c.nombre FROM devolucion d INNER JOIN cliente c on d.id_cliente = c.id_cliente WHERE d.estado = 1";
        return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Devolucion.class));
    }

    @Override
    public int save(Devolucion devolucion){
        String SQL = "INSERT INTO devolucion (fecha, producto, id_cliente) VALUES (?,?,?)";
        return jdbcTemplate.update(SQL, new Object[]{devolucion.getFecha(),devolucion.getProducto(),devolucion.getId_cliente()});
    }

    @Override
    public  int update(Devolucion devolucion){
        String SQL = "UPDATE devolucion SET fecha=?, producto=?, id_cliente=? WHERE id_devolucion =?";
        return jdbcTemplate.update(SQL, new Object[]{devolucion.getFecha(),devolucion.getProducto(),devolucion.getId_cliente(),devolucion.getId_devolucion()});
    }

    @Override
    public int deleteById(int id_devolucion){
        String SQL="UPDATE devolucion SET estado = 0 WHERE id_devolucion =?";
        return jdbcTemplate.update(SQL, new Object[]{id_devolucion});
    }
}
