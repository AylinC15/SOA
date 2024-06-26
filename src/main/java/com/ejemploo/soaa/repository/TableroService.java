package com.ejemploo.soaa.repository;

import com.ejemploo.soaa.model.Tablero;
import com.ejemploo.soaa.service.ITableroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableroService implements ITableroService {
    @Autowired
    private ITableroRepository iTableroRepository;

    @Override
    public List<Tablero> findAll(){
        List<Tablero> list;
        try{
            list = iTableroRepository.findAll();
        } catch (Exception ex){
            throw ex;
        }
        return list;
    }

    @Override
    public int save(Tablero tablero){
        int row;
        try {
            row = iTableroRepository.save(tablero);
        } catch (Exception ex){
            throw ex;
        }
        return row;
    }

    @Override
    public int update(Tablero tablero){
        int row;
        try {
            row = iTableroRepository.update(tablero);
        } catch (Exception ex){
            throw ex;
        }
        return row;
    }

    @Override
    public int deleteById(int id_tablero){
        int row;
        try {
            row = iTableroRepository.deleteById(id_tablero);
        } catch (Exception ex){
            throw ex;
        }
        return row;
    }

    @Override
    public Tablero findById(int id_tablero){
        Tablero tablero;
        try {
            tablero = iTableroRepository.findById(id_tablero);
        } catch (Exception ex) {
            throw ex;
        }
        return tablero;
    }

}
