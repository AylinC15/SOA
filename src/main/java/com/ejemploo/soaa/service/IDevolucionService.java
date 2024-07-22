package com.ejemploo.soaa.service;

import com.ejemploo.soaa.model.Devolucion;

import java.util.List;

public interface IDevolucionService {
    public List<Devolucion> findAll();
    public int save(Devolucion devolucion);
    public int update(Devolucion devolucion);
    public int deleteById(int id_devolucion);
    Devolucion findById(int id_devolucion);
}
