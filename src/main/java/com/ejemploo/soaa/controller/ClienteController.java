package com.ejemploo.soaa.controller;

import com.ejemploo.soaa.model.Cliente;
import com.ejemploo.soaa.model.ServiceResponse;
import com.ejemploo.soaa.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin("*")
public class ClienteController {

    @Autowired
    private IClienteService iClienteService;

    @GetMapping("/lista")
    public ResponseEntity<List<Cliente>> list(){
        var result = iClienteService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/guardar")
    public ResponseEntity<ServiceResponse> save(@RequestBody Cliente cliente){

        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iClienteService.save(cliente);
        if (result == 1){
            serviceResponse.setMessage("Cliente guardado correctamente");
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @PostMapping("/actualizar")
    public ResponseEntity<ServiceResponse> update(@RequestBody Cliente cliente){

        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iClienteService.update(cliente);
        if (result == 1){
            serviceResponse.setMessage("Producto actualizado correctamente");
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @PostMapping("/borrar")
    public ResponseEntity<ServiceResponse> update(@PathVariable String name){

        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iClienteService.deleteByName(name);
        if (result == 1){
            serviceResponse.setMessage("Producto borrado correctamente");
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

}
