package com.ejemploo.soaa.repository;


import com.ejemploo.soaa.model.Devolucion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    @Override
    public Devolucion findById(int id_devolucion) {
        String sql = "SELECT * FROM devolucion WHERE id_devolucion = ?";
        return jdbcTemplate.query(sql, new Object[]{id_devolucion}, new ResultSetExtractor<Devolucion>() {
            @Override
            public Devolucion extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    Devolucion devolucion = new Devolucion();
                    devolucion.setId_devolucion(rs.getInt("id_devolucion"));
                    devolucion.setFecha(String.valueOf(rs.getDate("fecha")));
                    devolucion.setProducto(rs.getString("producto"));
                    devolucion.setId_cliente(rs.getInt("id_cliente"));
                    return devolucion;
                }
                return null;
            }
        });
    }

}
