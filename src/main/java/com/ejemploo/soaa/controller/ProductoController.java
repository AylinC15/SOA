package com.ejemploo.soaa.controller;


import com.ejemploo.soaa.model.Producto;
import com.ejemploo.soaa.model.ServiceResponse;
import com.ejemploo.soaa.service.IAlmacenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
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

    /*@PostMapping("/guardar")
    public ResponseEntity<ServiceResponse> save(@RequestBody Producto producto){

        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iAlmacenService.save(producto);
        if (result == 1){
            serviceResponse.setMessage("Producto guardado correctamente");
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }*/

    @PostMapping("/guardar")
    public String save(@ModelAttribute Producto producto, Model model) {
        int result = iAlmacenService.save(producto);
        if (result == 1) {
            model.addAttribute("message", "Producto guardado correctamente");
        } else {
            model.addAttribute("message", "Error al guardar el producto");
        }
        return "redirect:/productos";
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

    /*@GetMapping("/borrar/{name}")
    public ResponseEntity<ServiceResponse> update(@PathVariable String name){

        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iAlmacenService.deleteByName(name);
        if (result == 1){
            serviceResponse.setMessage("Producto borrado correctamente");
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }*/

    @GetMapping("/borrar/{name}")
    public String delete(@PathVariable String name, RedirectAttributes redirectAttributes) {
        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iAlmacenService.deleteByName(name);
        if (result == 1) {
            redirectAttributes.addFlashAttribute("message", "Producto borrado correctamente");
        } else {
            redirectAttributes.addFlashAttribute("message", "Error al borrar el producto");
        }
        return "redirect:/productos"; // Redirige a la vista de lista de productos
    }


}
