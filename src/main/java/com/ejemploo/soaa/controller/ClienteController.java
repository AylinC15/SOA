package com.ejemploo.soaa.controller;

import com.ejemploo.soaa.model.Cliente;
import com.ejemploo.soaa.model.Devolucion;
import com.ejemploo.soaa.model.Producto;
import com.ejemploo.soaa.model.ServiceResponse;
import com.ejemploo.soaa.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/api/cliente")
public class ClienteController {

    @Autowired
    private IClienteService iClienteService;


    @GetMapping("/lista")
    public ResponseEntity<List<Cliente>> list(){
        var result = iClienteService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

 

    /*@PostMapping("/guardar")
    public ResponseEntity<ServiceResponse> save(@ModelAttribute Cliente cliente){

        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iClienteService.save(cliente);
        if (result == 1){
            serviceResponse.setMessage("Cliente guardado correctamente");
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }*/

    @PostMapping("/guardar")
    public String save(@ModelAttribute Cliente cliente, Model model) {
        int result = iClienteService.save(cliente);
        if (result == 1) {
            model.addAttribute("message", "Cliente guardado correctamente");
        } else {
            model.addAttribute("message", "Error al guardar el cliente");
        }
        return "redirect:/clientes";
    }

    /*
    @PostMapping("/actualizar")
    public ResponseEntity<ServiceResponse> update(@RequestBody Cliente cliente){

        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iClienteService.update(cliente);
        if (result == 1){
            serviceResponse.setMessage("Cliente actualizado correctamente");
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }*/

    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute Cliente cliente, RedirectAttributes redirectAttributes) {
        System.out.println("ID recibido en el controlador: " + cliente.getId_cliente());

        if (cliente.getId_cliente() == 0) {
            redirectAttributes.addFlashAttribute("error", "ID de devolución no válido");
            return "redirect:/clientes";
        }

        int result = iClienteService.update(cliente);
        if (result > 0) {
            redirectAttributes.addFlashAttribute("mensaje", "Devolución actualizada correctamente");
        } else {
            redirectAttributes.addFlashAttribute("error", "No se pudo actualizar la devolución");
        }
        return "redirect:/clientes";
    }

    /*
    @GetMapping("/borrar/{name}")
    public ResponseEntity<ServiceResponse> update(@PathVariable String name){

        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iClienteService.deleteByName(name);
        if (result == 1){
            serviceResponse.setMessage("Cliente borrado correctamente");
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }*/

    @GetMapping("/borrar/{nombre}")
    public String delete(@PathVariable String nombre, RedirectAttributes redirectAttributes) {
        int result = iClienteService.deleteByName(nombre);
        if (result == 1) {
            redirectAttributes.addFlashAttribute("message", "Cliente borrado correctamente");
        } else {
            redirectAttributes.addFlashAttribute("message", "Error al borrar el cliente");
        }
        return "redirect:/clientes"; // Redirige a la vista de lista de clientes
    }


        @GetMapping("/buscarPorRuc/{ruc}")
    public ResponseEntity<Cliente> obtenerClientePorRuc(@PathVariable String ruc) {
        Cliente cliente = iClienteService.findByRuc(ruc);
        if (cliente != null) {
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/buscarPorNombre/{name}")
    public ResponseEntity<Cliente> obtenerClientePorNombre(@PathVariable String name) {
        Cliente cliente = iClienteService.findByNombreIgnoreCase(name);
        if (cliente != null) {
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
