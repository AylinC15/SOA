package com.ejemploo.soaa.repository;

import com.ejemploo.soaa.model.Servicio;
import com.ejemploo.soaa.service.IServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioService implements IServicioService {
    @Autowired
    private IServicioRepository iServicioRepository;

    @Override
    public List<Servicio> findAll() {
        List<Servicio> list;
        try {
            list = iServicioRepository.findAll();
        }catch (Exception sex){
            throw sex;
        }
        return list;
    }

    @Override
    public int save(Servicio servicio) {
        int row;
        try {
            row = iServicioRepository.save(servicio);
        }catch (Exception sex){
            throw sex;
        }

        return row;
    }

    @Override
    public int update(Servicio servicio) {
        int row;
        try {
            row = iServicioRepository.update(servicio);
        }catch (Exception sex){
            throw sex;
        }

        return row;
    }

    @Override
    public int deleteByName(String tipo_servicio) {
        int row;
        try {
            row = iServicioRepository.deleteByName(tipo_servicio);
        }catch (Exception sex){
            throw sex;
        }
        return row;
    }

    @Override
    public List<Servicio> findByEmpleadoId(int id_empleado) {
        return iServicioRepository.findByEmpleadoId(id_empleado); // Implementación del nuevo método
    }

}
