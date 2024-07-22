package com.ejemploo.soaa.service;

import com.ejemploo.soaa.model.Cliente;
import com.ejemploo.soaa.model.Empleado;

import java.util.List;

public interface IEmpleadoService {
    public List<Empleado> findAll();
    public int save(Empleado empleado);
    public int update(Empleado empleado);
    public int deleteByNameEmpleado (String name);
    Empleado findByDni(String dni);
    Empleado findByNombreIgnoreCase(String nombre);
    Empleado findById(int id_empleado);
}
