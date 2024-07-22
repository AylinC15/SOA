package com.ejemploo.soaa.repository;

import com.ejemploo.soaa.model.Producto;
import com.ejemploo.soaa.model.Servicio;
import com.ejemploo.soaa.model.Venta;

import com.ejemploo.soaa.service.IVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class VentaService implements IVentaService {
    @Autowired
    private IVentaRepository iVentaRepository;

    @Autowired
    private IAlmacenRepository iProductoRepository;

    @Override
    public List<Venta> findAll() {
        try {
            return iVentaRepository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public int save(Venta venta) {
        try {
            // Obtener el producto para calcular el total
            Optional<Producto> productoOpt = iProductoRepository.findById(venta.getId_producto());
            if (productoOpt.isPresent()) {
                Producto producto = productoOpt.get();
                BigDecimal totalPagar = producto.getPrecio().multiply(BigDecimal.valueOf(venta.getCantidad_venta()));
                venta.setTotal_pagar(totalPagar);
            }
            return iVentaRepository.save(venta);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public int update(Venta venta) {
        try {
            // Obtener el producto para calcular el total
            Optional<Producto> productoOpt = iProductoRepository.findById(venta.getId_producto());
            if (productoOpt.isPresent()) {
                Producto producto = productoOpt.get();
                BigDecimal totalPagar = producto.getPrecio().multiply(BigDecimal.valueOf(venta.getCantidad_venta()));
                venta.setTotal_pagar(totalPagar);
            }
            return iVentaRepository.update(venta);
        } catch (Exception e) {
            throw e;
        }
    }


    @Override
    public int deleteById(int id_venta) {
        try {
            return iVentaRepository.deleteById(id_venta);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Optional<Venta> findById(int id_venta) {
        try {
            return iVentaRepository.findById(id_venta);
        } catch (Exception e) {
            throw e;
        }
    }

    


    @Override
    public List<Venta> findByEmpleadoId(int id_empleado) {
        try {
            return iVentaRepository.findByEmpleadoId(id_empleado);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Venta findByIdE(int id_servicio){
        Venta venta;
        try {
            venta= iVentaRepository.findByIdE(id_servicio);
        }catch (Exception ex){
            throw ex;
        }
        return venta;
    }

}


