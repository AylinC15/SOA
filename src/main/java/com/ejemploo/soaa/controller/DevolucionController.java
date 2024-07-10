package com.ejemploo.soaa.controller;





import com.ejemploo.soaa.model.Devolucion;
import com.ejemploo.soaa.model.ServiceResponse;
import com.ejemploo.soaa.service.IDevolucionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devolucion")
public class DevolucionController {
    @Autowired
    private IDevolucionService iDevolucionService;

    @GetMapping("/lista")
    public ResponseEntity<List<Devolucion>> list(){
        var result = iDevolucionService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/guardar")
    public ResponseEntity<ServiceResponse> save(@RequestBody Devolucion devolucion){

        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iDevolucionService.save(devolucion);
        if (result == 1){
            serviceResponse.setMessage("Devolucion guardada correctamente");
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @PostMapping("/actualizar")
    public ResponseEntity<ServiceResponse> update(@RequestBody Devolucion devolucion){

        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iDevolucionService.update(devolucion);
        if (result == 1){
            serviceResponse.setMessage("Devolucion actualizada correctamente");
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @GetMapping("/borrar/{id_devolucion}")
    public ResponseEntity<ServiceResponse> update(@PathVariable int id_devolucion){

        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iDevolucionService.deleteById(id_devolucion);
        if (result == 1){
            serviceResponse.setMessage("Devolucion borrada correctamente");
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

}
