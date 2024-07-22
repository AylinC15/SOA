package com.ejemploo.soaa.controller;

import com.ejemploo.soaa.model.*;
import com.ejemploo.soaa.service.IProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
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

    /*@PostMapping("/guardar")
    public ResponseEntity<ServiceResponse> save(@RequestBody Proveedor proveedor){

    ServiceResponse serviceResponse = new ServiceResponse();
    int result = iProveedorService.save(proveedor);
        if (result == 1){
        serviceResponse.setMessage("Proveedor guardado correctamente");
    }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }*/

    @PostMapping("/guardar")
    public String save(@ModelAttribute Proveedor proveedor, Model model) {
        int result = iProveedorService.save(proveedor);
        if (result == 1) {
            model.addAttribute("message", "Proveedor guardado correctamente");
        } else {
            model.addAttribute("message", "Error al guardar el proveedor");
        }
        return "redirect:/proveedor";
    }


    /*@PostMapping("/actualizar")
    public ResponseEntity<ServiceResponse> update(@RequestBody Proveedor proveedor){

        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iProveedorService.update(proveedor);
        if (result == 1){
            serviceResponse.setMessage("Proveedor actualizado correctamente");
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }*/

    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute Proveedor proveedor, RedirectAttributes redirectAttributes) {
        System.out.println("ID recibido en el controlador: " + proveedor.getId_proveedor());

        if (proveedor.getId_proveedor() == 0) {
            redirectAttributes.addFlashAttribute("error", "ID de devolución no válido");
            return "redirect:/proveedor";
        }

        int result = iProveedorService.update(proveedor);
        if (result > 0) {
            redirectAttributes.addFlashAttribute("mensaje", "Devolución actualizada correctamente");
        } else {
            redirectAttributes.addFlashAttribute("error", "No se pudo actualizar la devolución");
        }
        return "redirect:/proveedor";
    }

    /*
    @GetMapping("/borrar/{nombre_proveedor}")
    public ResponseEntity<ServiceResponse> update(@PathVariable String nombre_proveedor){

        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iProveedorService.deleteByName(nombre_proveedor);
        if (result == 1){
            serviceResponse.setMessage("Proveedor borrado correctamente");
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }*/

    @GetMapping("/buscarProveedor")
    public ResponseEntity<?> buscarProveedor(@RequestParam(required = false) String termino) {
        System.out.println("Búsqueda - Término: " + termino);

        List<Proveedor> proveedores = new ArrayList<>();

        try {
            if (termino == null || termino.trim().isEmpty()) {
                // Si el término está vacío, devolver todos los proveedores
                proveedores = iProveedorService.findAll();
            } else {
                proveedores = iProveedorService.findByNombreIgnoreCaseContaining(termino);
            }

            System.out.println("Proveedores encontrados: " + proveedores.size());
            return new ResponseEntity<>(proveedores, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error en la búsqueda: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK); // Devolver una lista vacía en caso de error
        }
    }


    public String delete(@PathVariable String nombre_proveedor, RedirectAttributes redirectAttributes) {
        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iProveedorService.deleteByName(nombre_proveedor);
        if (result == 1) {
            redirectAttributes.addFlashAttribute("message", "Producto borrado correctamente");
        } else {
            redirectAttributes.addFlashAttribute("message", "Error al borrar el producto");
        }
        return "redirect:/proveedor"; // Redirige a la vista de lista de productos
    }

}
