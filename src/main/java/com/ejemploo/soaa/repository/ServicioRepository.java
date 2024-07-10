package com.ejemploo.soaa.repository;

import com.ejemploo.soaa.model.Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ServicioRepository implements IServicioRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Servicio> findAll(){
        String SQL = "SELECT s.id_servicio,s.tipo_servicio, s.descripcion, e.name FROM servicio s INNER JOIN empleado e on s.id_empleado = e.id_empleado WHERE s.estado = 1";
        return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Servicio.class));
    }

    @Override
    public int save(Servicio servicio){
        String SQL = "INSERT INTO servicio (tipo_servicio, descripcion, id_empleado) VALUES (?,?,?)";
        return jdbcTemplate.update(SQL, new Object[]{servicio.getTipo_servicio(),servicio.getDescripcion(),servicio.getId_empleado()});
    }

    @Override
    public int update(Servicio servicio){
        String SQL = "UPDATE servicio SET tipo_servicio=?, descripcion=?, id_empleado=? WHERE id_servicio=?";
        return jdbcTemplate.update(SQL, new Object[]{servicio.getTipo_servicio(),servicio.getDescripcion(),servicio.getId_empleado(),servicio.getId_servicio()});
    }

    @Override
    public int deleteByName(String tipo_servicio){
        String SQL = "UPDATE servicio SET estado = 0 WHERE tipo_servicio=?";
        return jdbcTemplate.update(SQL, new Object[]{tipo_servicio});
    }

}
