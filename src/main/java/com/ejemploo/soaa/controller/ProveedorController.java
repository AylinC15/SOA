package com.ejemploo.soaa.controller;

import com.ejemploo.soaa.model.Cliente;
import com.ejemploo.soaa.model.Proveedor;
import com.ejemploo.soaa.model.ServiceResponse;
import com.ejemploo.soaa.service.IProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedor")
@CrossOrigin("*")
public class ProveedorController {
    @Autowired
    private IProveedorService iProveedorService;

    @GetMapping("/lista")
    public ResponseEntity<List<Proveedor>> list(){
        var result = iProveedorService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/guardar")
    public ResponseEntity<ServiceResponse> save(@RequestBody Proveedor proveedor){

    ServiceResponse serviceResponse = new ServiceResponse();
    int result = iProveedorService.save(proveedor);
        if (result == 1){
        serviceResponse.setMessage("Proveedor guardado correctamente");
    }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @PostMapping("/actualizar")
    public ResponseEntity<ServiceResponse> update(@RequestBody Proveedor proveedor){

        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iProveedorService.update(proveedor);
        if (result == 1){
            serviceResponse.setMessage("Proveedor actualizado correctamente");
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @GetMapping("/borrar/{nombre_proveedor}")
    public ResponseEntity<ServiceResponse> update(@PathVariable String nombre_proveedor){

        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iProveedorService.deleteByName(nombre_proveedor);
        if (result == 1){
            serviceResponse.setMessage("Proveedor borrado correctamente");
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

}
