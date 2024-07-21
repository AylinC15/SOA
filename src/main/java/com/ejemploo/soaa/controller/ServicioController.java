package com.ejemploo.soaa.controller;



import com.ejemploo.soaa.model.Cliente;
import com.ejemploo.soaa.model.ServiceResponse;
import com.ejemploo.soaa.model.Servicio;
import com.ejemploo.soaa.service.IServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/api/servicio")
public class ServicioController {
    @Autowired
    private IServicioService iServicioService;

    @GetMapping("/lista")
    public ResponseEntity<List<Servicio>> list(){
        var result = iServicioService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /*@PostMapping("/guardar")
    public ResponseEntity<ServiceResponse> save(@RequestBody Servicio servicio){

        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iServicioService.save(servicio);
        if (result == 1){
            serviceResponse.setMessage("Servicio guardado correctamente");
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }*/

    @PostMapping("/guardar")
    public String save(@ModelAttribute Servicio servicio, Model model) {
        int result = iServicioService.save(servicio);
        if (result == 1) {
            model.addAttribute("message", "Cliente guardado correctamente");
        } else {
            model.addAttribute("message", "Error al guardar el cliente");
        }
        return "redirect:/servicios";
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

    /*@GetMapping("/borrar/{tipo_servicio}")
    public ResponseEntity<ServiceResponse> update(@PathVariable String tipo_servicio){

        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iServicioService.deleteByName(tipo_servicio);
        if (result == 1){
            serviceResponse.setMessage("Servicio borrado correctamente");
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }*/

    @GetMapping("/borrar/{tipo_servicio}")
    public String delete(@PathVariable String tipo_servicio, RedirectAttributes redirectAttributes) {
        int result = iServicioService.deleteByName(tipo_servicio);
        if (result == 1) {
            redirectAttributes.addFlashAttribute("message", "servicio borrado correctamente");
        } else {
            redirectAttributes.addFlashAttribute("message", "Error al borrar el servicio");
        }
        return "redirect:/servicios"; // Redirige a la vista de lista de clientes
    }


}
