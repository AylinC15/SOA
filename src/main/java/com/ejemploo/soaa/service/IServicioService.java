package com.ejemploo.soaa.service;
import com.ejemploo.soaa.model.Servicio;
import java.util.List;

public interface IServicioService {
    public List<Servicio> findAll();
    public int save(Servicio servicio);
    public int update(Servicio servicio);
    public int deleteByName (String tipo_servicio);
    List<Servicio> findByEmpleadoId(int id_empleado); // Nuevo método
}
