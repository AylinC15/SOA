package com.ejemploo.soaa.repository;

import com.ejemploo.soaa.model.Producto;
import com.ejemploo.soaa.model.Venta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class VentaRepository implements IVentaRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Venta> findAll() {
        String sql = "SELECT v.id_venta, v.fecha, v.cantidad_venta, v.total_pagar, c.nombre AS cliente_nombre, c.ruc, e.name AS empleado_nombre, p.name AS producto_nombre, p.id_producto FROM venta v INNER JOIN cliente c ON v.id_cliente = c.id_cliente INNER JOIN empleado e ON v.id_empleado = e.id_empleado INNER JOIN producto p ON v.id_producto = p.id_producto";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRowToVenta(rs));
    }

    @Override
    public int save(Venta venta) {
        String SQL = "INSERT INTO venta (fecha, cantidad_venta, total_pagar, id_cliente, id_empleado, id_producto) VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(SQL, new Object[]{venta.getFecha(),venta.getCantidad_venta(),venta.getTotal_pagar(),venta.getId_cliente(),venta.getId_empleado(),venta.getId_producto()});
    }

    @Override
    public int update(Venta venta) {
        String sql = "UPDATE venta SET fecha = ?, cantidad_venta = ?, total_pagar = ?, estado = ?, id_cliente = ?, id_empleado = ?, id_producto = ? WHERE id_venta = ?";
        return jdbcTemplate.update(sql, venta.getFecha(), venta.getCantidad_venta(), venta.getTotal_pagar(), venta.getEstado(), venta.getId_cliente(), venta.getId_empleado(), venta.getId_producto(), venta.getId_venta());
    }

    @Override
    public int deleteById(int id_venta) {
        String sql = "DELETE FROM venta WHERE id_venta = ?";
        return jdbcTemplate.update(sql, id_venta);
    }

    public Optional<Venta> findById(int id_venta) {
        String sql = "SELECT v.id_venta, v.fecha, v.cantidad_venta, v.total_pagar, c.nombre AS cliente_nombre, c.ruc, e.name AS empleado_nombre, p.name AS producto_nombre, p.id_producto FROM venta v INNER JOIN cliente c ON v.id_cliente = c.id_cliente INNER JOIN empleado e ON v.id_empleado = e.id_empleado INNER JOIN producto p ON v.id_producto = p.id_producto WHERE v.id_venta = ?";
        List<Venta> ventas = jdbcTemplate.query(sql, new Object[]{id_venta}, (rs, rowNum) -> mapRowToVenta(rs));
        if (ventas.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(ventas.get(0));
    }

    @Override
    public List<Venta> findByEmpleadoId(int idEmpleado) {
        String sql = "SELECT v.id_venta, v.fecha, v.cantidad_venta, v.total_pagar, c.nombre AS cliente_nombre, c.ruc, e.name AS empleado_nombre, p.name AS producto_nombre, p.id_producto FROM venta v INNER JOIN cliente c ON v.id_cliente = c.id_cliente INNER JOIN empleado e ON v.id_empleado = e.id_empleado INNER JOIN producto p ON v.id_producto = p.id_producto WHERE v.id_empleado = ?";
        return jdbcTemplate.query(sql, new Object[]{idEmpleado}, (rs, rowNum) -> mapRowToVenta(rs));
    }

    private Venta mapRowToVenta(ResultSet rs) throws SQLException {
        Venta venta = new Venta();
        venta.setId_venta(rs.getInt("id_venta"));
        venta.setFecha(rs.getDate("fecha"));
        venta.setCantidad_venta(rs.getInt("cantidad_venta"));
        venta.setTotal_pagar(rs.getBigDecimal("total_pagar"));
        venta.setId_producto(rs.getInt("id_producto"));
        venta.setClienteNombre(rs.getString("cliente_nombre"));
        venta.setRuc(rs.getString("ruc"));
        venta.setEmpleadoNombre(rs.getString("empleado_nombre"));
        venta.setProductoNombre(rs.getString("producto_nombre"));
        return venta;
    }

    private Producto mapProducto(ResultSet rs) throws SQLException {
        Producto producto = new Producto();
        producto.setId_producto(rs.getInt("id_producto"));
        producto.setName(rs.getString("name"));
        producto.setPrecio(rs.getBigDecimal("precio"));
        producto.setCantidad(rs.getInt("cantidad"));
        return producto;
    }
}
