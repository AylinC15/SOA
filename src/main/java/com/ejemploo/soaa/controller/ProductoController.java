package com.ejemploo.soaa.controller;


import com.ejemploo.soaa.model.Producto;
import com.ejemploo.soaa.model.ServiceResponse;
import com.ejemploo.soaa.service.IAlmacenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/almacen")
@CrossOrigin("*")
public class ProductoController {
    @Autowired
    private IAlmacenService iAlmacenService;

    @GetMapping("/lista")
    public ResponseEntity<List<Producto>> list(){
        var result = iAlmacenService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/guardar")
    public ResponseEntity<ServiceResponse> save(@RequestBody Producto producto){

        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iAlmacenService.save(producto);
        if (result == 1){
            serviceResponse.setMessage("Producto guardado correctamente");
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @PostMapping("/actualizar")
    public ResponseEntity<ServiceResponse> update(@RequestBody Producto producto){

        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iAlmacenService.update(producto);
        if (result == 1){
            serviceResponse.setMessage("Producto actualizado correctamente");
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @PostMapping("/borrar")
    public ResponseEntity<ServiceResponse> update(@PathVariable String name){

        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iAlmacenService.deleteByName(name);
        if (result == 1){
            serviceResponse.setMessage("Producto borrado correctamente");
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

}
