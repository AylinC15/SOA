package com.ejemploo.soaa.controller;

import com.ejemploo.soaa.model.Empleado;
import com.ejemploo.soaa.model.ServiceResponse;
import com.ejemploo.soaa.service.IEmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleado")
@CrossOrigin("*")

public class EmpleadoController {

    @Autowired
    private IEmpleadoService iEmpleadoService;

    @GetMapping("/lista")
    public ResponseEntity<List<Empleado>> list(){
        var result = iEmpleadoService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/guardar")
    public ResponseEntity<ServiceResponse> save(@RequestBody Empleado empleado){
        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iEmpleadoService.save(empleado);
        if (result == 1){
            serviceResponse.setMessage("Empleado guardado correctamente");
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @PostMapping("/actualizar")
    public ResponseEntity<ServiceResponse> update(@RequestBody Empleado empleado){
        ServiceResponse serviceResponse = new ServiceResponse();
            int result = iEmpleadoService.update(empleado);
            if (result == 1){
                serviceResponse.setMessage("Dato de empleado actualizado");
            }
            return  new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @GetMapping("/borrar/{name}")
    public ResponseEntity<ServiceResponse> update(@PathVariable String name) {
        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iEmpleadoService.deleteByNameEmpleado(name);
        if (result == 1) {
            serviceResponse.setMessage("Empleado borrado correctamente");
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @GetMapping("/buscarPorDni/{dni}")
    public ResponseEntity<Empleado> obtenerClientePorDni(@PathVariable String dni){
        Empleado empleado = iEmpleadoService.findByDni(dni);
        if (empleado != null ){
            return new ResponseEntity<>(empleado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/buscarPorNombre/{name}")
    public ResponseEntity<Empleado> obtenerClientePorNombre(@PathVariable String name) {
        Empleado empleado = iEmpleadoService.findByNombreIgnoreCase(name);
        if (empleado != null){
            return new ResponseEntity<>(empleado, HttpStatus.OK);
        } else {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }
}
