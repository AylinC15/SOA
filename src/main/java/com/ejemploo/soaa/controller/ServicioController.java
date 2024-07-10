package com.ejemploo.soaa.controller;



import com.ejemploo.soaa.model.Cliente;
import com.ejemploo.soaa.model.ServiceResponse;
import com.ejemploo.soaa.model.Servicio;
import com.ejemploo.soaa.service.IServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicio")
public class ServicioController {
    @Autowired
    private IServicioService iServicioService;

    @GetMapping("/lista")
    public ResponseEntity<List<Servicio>> list(){
        var result = iServicioService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/guardar")
    public ResponseEntity<ServiceResponse> save(@RequestBody Servicio servicio){

        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iServicioService.save(servicio);
        if (result == 1){
            serviceResponse.setMessage("Servicio guardado correctamente");
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @PostMapping("/actualizar")
    public ResponseEntity<ServiceResponse> update(@RequestBody Servicio servicio){

        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iServicioService.update(servicio);
        if (result == 1){
            serviceResponse.setMessage("Servicio actualizado correctamente");
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @GetMapping("/borrar/{tipo_servicio}")
    public ResponseEntity<ServiceResponse> update(@PathVariable String tipo_servicio){

        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iServicioService.deleteByName(tipo_servicio);
        if (result == 1){
            serviceResponse.setMessage("Servicio borrado correctamente");
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

}
