package com.ejemploo.soaa.repository;
import com.ejemploo.soaa.model.Devolucion;
import com.ejemploo.soaa.service.IDevolucionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DevolucionService implements IDevolucionService {
    @Autowired
    IDevolucionRepository iDevolucionRepository;

    @Override
    public List<Devolucion> findAll() {
        List<Devolucion> list;
        try {
            list = iDevolucionRepository.findAll();
        }catch (Exception sex){
            throw sex;
        }
        return list;
    }

    @Override
    public int save(Devolucion devolucion) {
        int row;
        try {
            row = iDevolucionRepository.save(devolucion);
        }catch (Exception sex){
            throw sex;
        }

        return row;
    }

    @Override
    public int update(Devolucion devolucion) {
        int row;
        try {
            row = iDevolucionRepository.update(devolucion);
        }catch (Exception sex){
            throw sex;
        }

        return row;
    }

    @Override
    public int deleteById(int id_empleado) {
        int row;
        try {
            row = iDevolucionRepository.deleteById(id_empleado);
        }catch (Exception sex){
            throw sex;
        }

        return row;
    }

}
