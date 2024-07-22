package com.ejemploo.soaa.repository;
import com.ejemploo.soaa.model.Venta;
import java.util.List;
import java.util.Optional;

public interface IVentaRepository {
    List<Venta> findAll();
    public int save(Venta venta);
    public int update(Venta venta);
    public int deleteById(int id);
    Optional<Venta> findById(int id_venta);
    List<Venta> findByEmpleadoId(int idEmpleado);
}
