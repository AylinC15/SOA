package com.ejemploo.soaa.repository;

import com.ejemploo.soaa.model.Producto;
import com.ejemploo.soaa.model.Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ServicioRepository implements IServicioRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Servicio> findAll(){
        String SQL = "SELECT s.id_servicio,s.tipo_servicio, s.descripcion, s.precio, e.name FROM servicio s INNER JOIN empleado e on s.id_empleado = e.id_empleado WHERE s.estado = 1";
        return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Servicio.class));
    }

    @Override
    public int save(Servicio servicio){
        String SQL = "INSERT INTO servicio (tipo_servicio, descripcion,precio, id_empleado ) VALUES (?,?,?,?)";
        return jdbcTemplate.update(SQL, new Object[]{servicio.getTipo_servicio(),servicio.getDescripcion(),servicio.getPrecio(),servicio.getId_empleado()});
    }

    @Override
    public int update(Servicio servicio){
        String SQL = "UPDATE servicio SET tipo_servicio=?, descripcion=?, precio=?, id_empleado=? WHERE id_servicio=?";
        return jdbcTemplate.update(SQL, new Object[]{servicio.getTipo_servicio(),servicio.getDescripcion(),servicio.getPrecio(),servicio.getId_empleado(),servicio.getId_servicio()});
    }

    @Override
    public int deleteByName(String tipo_servicio){
        String SQL = "UPDATE servicio SET estado = 0 WHERE tipo_servicio=?";
        return jdbcTemplate.update(SQL, new Object[]{tipo_servicio});
    }

    private RowMapper<Servicio> rowMapper = new RowMapper<Servicio>() {
        @Override
        public Servicio mapRow(ResultSet rs, int rowNum) throws SQLException {
            Servicio servicio = new Servicio();
            servicio.setId_servicio(rs.getInt("id_servicio"));
            servicio.setTipo_servicio(rs.getString("tipo_servicio"));
            servicio.setDescripcion(rs.getString("descripcion"));
            servicio.setEstado(rs.getInt("estado"));
            servicio.setId_empleado(rs.getInt("id_empleado"));
            servicio.setPrecio(rs.getBigDecimal("precio")); // Si has añadido esta columna
            servicio.setName(rs.getString("name"));
            return servicio;
        }
    };

    public List<Servicio> findByEmpleadoId(int id_empleado) {
        String sql = "SELECT s.*, e.name FROM servicio s INNER JOIN empleado e ON s.id_empleado = e.id_empleado WHERE s.id_empleado = ?";
        return jdbcTemplate.query(sql, new Object[]{id_empleado}, rowMapper);
    }

    @Override
    public Servicio findById(int id_servicio) {
        String sql = "SELECT * FROM servicio WHERE id_servicio = ?";
        return jdbcTemplate.query(sql, new Object[]{id_servicio}, new ResultSetExtractor<Servicio>() {
            @Override
            public Servicio extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    Servicio servicio = new Servicio();
                    servicio.setId_servicio(rs.getInt("id_servicio"));
                    servicio.setTipo_servicio(rs.getString("tipo_servicio"));
                    servicio.setDescripcion(rs.getString("descripcion"));
                    servicio.setPrecio(rs.getBigDecimal("precio"));
                    return servicio;
                }
                return null;
            }
        });
    }

}
