package com.ejemploo.soaa.controller;

import com.ejemploo.soaa.model.Cliente;
import com.ejemploo.soaa.model.Devolucion;
import com.ejemploo.soaa.model.ServiceResponse;
import com.ejemploo.soaa.service.IDevolucionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/devolucion")
public class DevolucionController {
    @Autowired
    private IDevolucionService iDevolucionService;

    @GetMapping("/lista")
    public ResponseEntity<List<Devolucion>> list(){
        var result = iDevolucionService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /*@PostMapping("/guardar")
    public ResponseEntity<ServiceResponse> save(@RequestBody Devolucion devolucion){

        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iDevolucionService.save(devolucion);
        if (result == 1){
            serviceResponse.setMessage("Devolucion guardada correctamente");
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }*/

    @PostMapping("/guardar")
    public String save(@ModelAttribute Devolucion devolucion, Model model) {
        int result = iDevolucionService.save(devolucion);
        if (result == 1) {
            model.addAttribute("message", "Devolucion guardada correctamente");
        } else {
            model.addAttribute("message", "Error al guardar la devolucion");
        }
        return "redirect:/devoluciones";
    }


    /*@PostMapping("/actualizar")
    public ResponseEntity<ServiceResponse> update(@RequestBody Devolucion devolucion){

        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iDevolucionService.update(devolucion);
        if (result == 1){
            serviceResponse.setMessage("Devolucion actualizada correctamente");
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }*/



    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute Devolucion devolucion, RedirectAttributes redirectAttributes) {
        System.out.println("ID recibido en el controlador: " + devolucion.getId_devolucion());

        if (devolucion.getId_devolucion() == 0) {
            redirectAttributes.addFlashAttribute("error", "ID de devolución no válido");
            return "redirect:/devoluciones";
        }

        int result = iDevolucionService.update(devolucion);
        if (result > 0) {
            redirectAttributes.addFlashAttribute("mensaje", "Devolución actualizada correctamente");
        } else {
            redirectAttributes.addFlashAttribute("error", "No se pudo actualizar la devolución");
        }
        return "redirect:/devoluciones";
    }
    /*@GetMapping("/borrar/{id_devolucion}")
    public ResponseEntity<ServiceResponse> update(@PathVariable int id_devolucion){

        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iDevolucionService.deleteById(id_devolucion);
        if (result == 1){
            serviceResponse.setMessage("Devolucion borrada correctamente");
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }*/

    @GetMapping("/borrar/{id_devolucion}")
    public String delete(@PathVariable int id_devolucion, RedirectAttributes redirectAttributes) {
        int result = iDevolucionService.deleteById(id_devolucion);
        if (result == 1) {
            redirectAttributes.addFlashAttribute("message", "Cliente borrado correctamente");
        } else {
            redirectAttributes.addFlashAttribute("message", "Error al borrar el cliente");
        }
        return "redirect:/devoluciones"; // Redirige a la vista de lista de clientes
    }

    @GetMapping("/buscarDevolucion")
    public ResponseEntity<?> buscarDevolucion(@RequestParam(required = false) String producto) {
        System.out.println("Búsqueda - Producto: " + producto);

        List<Devolucion> devoluciones = new ArrayList<>();

        try {
            if (producto == null || producto.trim().isEmpty()) {
                devoluciones = iDevolucionService.findAll();
            } else {
                devoluciones = iDevolucionService.findByProductoIgnoreCaseContaining(producto);
            }

            System.out.println("Devoluciones encontradas: " + devoluciones.size());
            return new ResponseEntity<>(devoluciones, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error en la búsqueda: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

}
