package com.ejemploo.soaa.repository;

import com.ejemploo.soaa.model.Cliente;
import com.ejemploo.soaa.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AlmacenRepository implements IAlmacenRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public List<Producto> findAll() {
        String SQL = "SELECT p.id_producto, p.name, p.marca, p.precio, p.cantidad, pr.nombre_proveedor FROM producto p INNER JOIN proveedor pr ON p.id_proveedor = pr.id_proveedor WHERE p.estado = '1';";
        return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Producto.class));
    }

    @Override
    public int save(Producto producto) {
        String SQL = "INSERT INTO producto (name, marca, precio, cantidad, id_proveedor) VALUES (?,?,?,?,?)";
        return jdbcTemplate.update(SQL, new Object[]{producto.getName(), producto.getMarca(), producto.getPrecio(), producto.getCantidad(), producto.getId_proveedor()});
    }

    @Override
    public int update(Producto producto) {
        String SQL = "UPDATE producto SET name=?,marca=?,cantidad=?,precio=?, id_proveedor=? WHERE id_producto = ?";
        return jdbcTemplate.update(SQL, new Object[]{producto.getName(),producto.getMarca(),producto.getCantidad(),producto.getPrecio(), producto.getId_proveedor(),producto.getId_producto()});
    }

    @Override
    public int deleteByName(String name) {
        String SQL = "UPDATE producto SET estado= 0 WHERE name=?";
        return jdbcTemplate.update(SQL, new Object[]{name});
    }

    @Override
    public Producto findByIdProducto(int id_producto) {
        String sql = "SELECT * FROM producto WHERE id_producto = ?";
        return jdbcTemplate.query(sql, new Object[]{id_producto}, new ResultSetExtractor<Producto>() {
            @Override
            public Producto extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    Producto producto = new Producto();
                    producto.setId_producto(rs.getInt("id_producto"));
                    producto.setMarca(rs.getString("marca"));
                    producto.setCantidad(rs.getInt("cantidad"));
                    producto.setPrecio(rs.getBigDecimal("precio"));
                    producto.setName(rs.getString("name"));
                    producto.setId_proveedor(rs.getInt("id_proveedor"));
                    return producto;
                }
                return null;
            }
        });
    }

    @Override
    public Optional<Producto> findById(int id_producto) {
        String sql = "SELECT id_producto, name, precio, cantidad FROM producto WHERE id_producto = ?";
        List<Producto> productos = jdbcTemplate.query(sql, new Object[]{id_producto}, (rs, rowNum) -> {
            Producto producto = new Producto();
            producto.setId_producto(rs.getInt("id_producto"));
            producto.setName(rs.getString("name"));
            producto.setPrecio(rs.getBigDecimal("precio"));
            producto.setCantidad(rs.getInt("cantidad"));
            return producto;
        });
        if (productos.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(productos.get(0));
}

    @Override
    public List<Producto> findByNameIgnoreCaseContaining(String name) {
        try {
            String SQL = "SELECT * FROM producto WHERE UPPER(name) LIKE UPPER(?)";
            return jdbcTemplate.query(SQL, new Object[]{"%" + name + "%"}, new BeanPropertyRowMapper<>(Producto.class));
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

}
