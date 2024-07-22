package com.ejemploo.soaa.repository;

import com.ejemploo.soaa.model.Cliente;
import com.ejemploo.soaa.model.Devolucion;
import com.ejemploo.soaa.model.Empleado;
import com.ejemploo.soaa.service.IEmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService implements IEmpleadoService {
    @Autowired
    private IEmpleadoRepository iEmpleadoRepository;

    @Override
    public List<Empleado> findAll(){
        List<Empleado> list;
        try {
            list = iEmpleadoRepository.findAll();
        } catch (Exception ex){
            throw ex;
        }
        return list;
    }

    @Override
    public int save(Empleado empleado){
        int row;
        try{
            row = iEmpleadoRepository.save(empleado);
        } catch (Exception ex){
            throw ex;
        }
        return row;
    }

    @Override
    public int update(Empleado empleado){
        int row;
        try{
            row = iEmpleadoRepository.update(empleado);
        } catch (Exception ex){
            throw ex;
        }
        return row;
    }

    @Override
    public int deleteByNameEmpleado(String name) {
        int row;
        try {
            row = iEmpleadoRepository.deletebyNameEmpleado(name);
        }catch (Exception sex){
            throw sex;
        }
        return row;
    }

    @Override
    public Empleado findByDni(String dni) {
        Empleado empleado;
        try {
            empleado = iEmpleadoRepository.findByDni(dni);
        } catch (Exception ex) {
            throw ex;
        }
        return empleado;
    }

    @Override
    public Empleado findByNombreIgnoreCase(String nombre) {
        Empleado empleado;
        try {
            empleado = iEmpleadoRepository.findByNombreIgnoreCase(nombre);
        } catch (Exception ex) {
            throw ex;
        }
        return empleado;
    }

    @Override
    public Empleado findById(int id_empleado) {
        Empleado empleado = iEmpleadoRepository.findById(id_empleado);
        if (empleado == null) {
            throw new RuntimeException("Devolución no encontrada con id: " + id_empleado);
        }
        return empleado;
    }


}
