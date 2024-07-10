package com.ejemploo.soaa.repository;

import com.ejemploo.soaa.model.Servicio;

import java.util.List;

public interface IServicioRepository {

    public List<Servicio> findAll();
    public int save(Servicio servicio);
    public int update(Servicio servicio);
    public int deleteByName(String tipo_servicio);
}
