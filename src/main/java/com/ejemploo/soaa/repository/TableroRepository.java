package com.ejemploo.soaa.repository;


import com.ejemploo.soaa.model.Producto;
import com.ejemploo.soaa.model.Tablero;
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
public class TableroRepository implements ITableroRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Tablero> findAll(){
        String SQL = "SELECT t.id_tablero, t.progreso, c.nombre FROM tablero t JOIN cliente c ON t.id_cliente = c.id_cliente WHERE t.estado = 1;";
        return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Tablero.class));
    }

    @Override
    public int save(Tablero tablero){
        String SQL = "INSERT INTO tablero(progreso,id_cliente) values (?,?)";
        return jdbcTemplate.update(SQL, new Object[]{tablero.getProgreso(),tablero.getId_cliente()});
    }

    @Override
    public int update(Tablero tablero){
        String SQL = "UPDATE tablero SET progreso=?, id_cliente=? where id_tablero=?";
        return jdbcTemplate.update(SQL, new Object[]{tablero.getProgreso(),tablero.getId_cliente(), tablero.getId_tablero()});
    }

    @Override
    public int deleteById(int id_tablero){
        String SQL = "UPDATE tablero SET estado = 0 WHERE id_tablero=?";
        return jdbcTemplate.update(SQL, new Object[]{id_tablero});
    }

    @Override
    public Tablero findById(int id_tablero) {
        String sql = "SELECT * FROM tablero WHERE id_tablero = ?";
        return jdbcTemplate.query(sql, new Object[]{id_tablero}, new ResultSetExtractor<Tablero>() {
            @Override
            public Tablero extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    Tablero tablero = new Tablero();
                    tablero.setId_tablero(rs.getInt("id_tablero"));
                    tablero.setProgreso(rs.getString("progreso"));
                    tablero.setId_cliente(rs.getInt("id_cliente"));
                    return tablero;
                }
                return null;
            }
        });
    }
}
