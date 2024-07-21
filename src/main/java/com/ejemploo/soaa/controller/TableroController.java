package com.ejemploo.soaa.controller;

import com.ejemploo.soaa.model.Empleado;
import com.ejemploo.soaa.model.ServiceResponse;
import com.ejemploo.soaa.model.Tablero;

import com.ejemploo.soaa.service.ITableroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
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

    /*
    @PostMapping("/guardar")
    public ResponseEntity<ServiceResponse> save(@RequestBody Tablero tablero){
        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iTableroService.save(tablero);
        if (result == 1){
            serviceResponse.setMessage("Tablero registrado");
        }
        return new ResponseEntity<>(serviceResponse,HttpStatus.OK);
    }*/

    @PostMapping("/guardar")
    public String save(@ModelAttribute Tablero tablero, Model model) {
        int result = iTableroService.save(tablero);
        if (result == 1) {
            model.addAttribute("message", "Cliente guardado correctamente");
        } else {
            model.addAttribute("message", "Error al guardar el cliente");
        }
        return "redirect:/tablero";
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

    @GetMapping("/borrar/{id_tablero}")
    public String delete(@PathVariable int id_tablero, RedirectAttributes redirectAttributes) {
        int result = iTableroService.deleteById(id_tablero);
        if (result == 1) {
            redirectAttributes.addFlashAttribute("message", "Cliente borrado correctamente");
        } else {
            redirectAttributes.addFlashAttribute("message", "Error al borrar el cliente");
        }
        return "redirect:/tablero"; // Redirige a la vista de lista de clientes
    }

}
