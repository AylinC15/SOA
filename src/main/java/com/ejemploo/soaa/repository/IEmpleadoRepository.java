package com.ejemploo.soaa.repository;

import com.ejemploo.soaa.model.Empleado;

import java.util.List;

public interface IEmpleadoRepository {
    public List<Empleado> findAll();
    public int save(Empleado empleado);
    public int update(Empleado empleado);
    public  int deletebyNameEmpleado(String name);
    Empleado findByDni(String dni);
    Empleado findByNombreIgnoreCase(String nombre);
}
