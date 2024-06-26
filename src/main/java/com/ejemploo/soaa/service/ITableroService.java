package com.ejemploo.soaa.service;

import com.ejemploo.soaa.model.Tablero;

import java.util.List;

public interface ITableroService {
    public List<Tablero> findAll();
    public int save(Tablero tablero);
    public int update(Tablero tablero);
    public int deleteById(int id_tablero);
    Tablero findById(int id_tablero);
}
