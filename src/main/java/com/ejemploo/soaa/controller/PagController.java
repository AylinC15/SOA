package com.ejemploo.soaa.controller;

import com.ejemploo.soaa.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagController {

    @GetMapping("/home")
    public String pagInicio() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/modal")
    public String modal() {
        return "modal";
    }

    @Autowired
    private OurUserRepository ourUserRepository;

    @GetMapping("/usuario")
    public String getAllUser(Model model) {
        model.addAttribute("usuarios", ourUserRepository.findAll());
        return "usuario";
    }

    @Autowired
    private ITableroRepository tableroRepository;

    @GetMapping("/tablero")
    public String listarTablero(Model model) {
        model.addAttribute("tableros", tableroRepository.findAll());
        return "tablero"; // Nombre de la vista Thymeleaf (tablero.html)
    }

    @Autowired
    private IClienteRepository clienteRepository;

    @GetMapping("/cliente")
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteRepository.findAll());
        return "cliente"; // Nombre de la vista Thymeleaf (cliente.html)
    }


    @Autowired
    private IEmpleadoRepository iEmpleadoRepository;

    @GetMapping("/empleados")
    public String listarEmpleados(Model model){
        model.addAttribute("empleados", iEmpleadoRepository.findAll());
        return "empleados";
    }

    @Autowired
    private IProveedorRepository iProveedorRepository;

    @GetMapping("/proveedor")
    public String listarProveedores(Model model){
        model.addAttribute("proveedores", iProveedorRepository.findAll());
        return "proveedor";
    }

    /*@Autowired
    private IProductoRepository iProductoRepository;

    @GetMapping("/productos")
    public String listarProductos(Model model){
        model.addAttribute("productos", iEmpleadoRepository.findAll());
        return "productos";
    }*/

}
