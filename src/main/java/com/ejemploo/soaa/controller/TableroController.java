package com.ejemploo.soaa.controller;

import com.ejemploo.soaa.model.ServiceResponse;
import com.ejemploo.soaa.model.Tablero;

import com.ejemploo.soaa.service.ITableroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tablero")
public class TableroController {

    @Autowired
    private ITableroService iTableroService;





    @GetMapping("/lista")
    public ResponseEntity<List<Tablero>> list(){
        var result = iTableroService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    /*@GetMapping("/tablero")
    public String listarTablero(Model model) {
        model.addAttribute("tableros", tableroRepository.findAll() );
        return "tablero";
    }*/


    @PostMapping("/guardar")
    public ResponseEntity<ServiceResponse> save(@RequestBody Tablero tablero){
        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iTableroService.save(tablero);
        if (result == 1){
            serviceResponse.setMessage("Tablero registrado");
        }
        return new ResponseEntity<>(serviceResponse,HttpStatus.OK);
    }

    @PostMapping("/actualizar")
    public  ResponseEntity<ServiceResponse> update(@RequestBody Tablero tablero){
        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iTableroService.update(tablero);
        if (result == 1){
            serviceResponse.setMessage("Tablero actualizado con exito");

        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }
}
