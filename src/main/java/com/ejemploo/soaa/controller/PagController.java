package com.ejemploo.soaa.controller;

import com.ejemploo.soaa.model.*;
import com.ejemploo.soaa.repository.*;
import com.ejemploo.soaa.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    public String showModal(Model model) {
        model.addAttribute("producto", new Producto());
        return "modal";  // Esta es la vista del modal
    }

    @Autowired
    private IAlmacenService iAlmacenService;

    @GetMapping("/modal/{id}")
    public String getModalProdcutoEdit(@PathVariable Long id, Model model) {
        Producto producto= iAlmacenService.findById(Math.toIntExact(id));
        model.addAttribute("producto", producto);
        return "modal";
    }

    @GetMapping("/modalCliente")
    public String showModal2(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "modals/modalCliente";  // Vista para el segundo modal
    }

    @Autowired
    private IClienteService iClienteService;

    @GetMapping("/modalCliente/{id}")
    public String getModalClienteEdit(@PathVariable Long id, Model model) {
        Cliente cliente = iClienteService.findById(Math.toIntExact(id));
        model.addAttribute("cliente", cliente);
        return "modals/modalCliente";
    }


    @GetMapping("/modalDevolucion")
    public String showModal3(Model model) {
        model.addAttribute("devolucion", new Devolucion());
        return "modals/modalDevolucion";  // Vista para el segundo modal
    }

    @Autowired
    private IDevolucionService iDevolucionService;

    @GetMapping("/modalDevolucion/{id}")
    public String getModalDevolucionEdit(@PathVariable Long id, Model model) {
        Devolucion devolucion = iDevolucionService.findById(Math.toIntExact(id));
        model.addAttribute("devolucion", devolucion);
        return "modals/modalDevolucion";
    }


    @GetMapping("/modalEmpleado")
    public String showModal4(Model model) {
        model.addAttribute("empleado", new Empleado());
        return "modals/modalEmpleado";  // Vista para el segundo modal
    }

    @Autowired
    private IEmpleadoService iEmpleadoService;

    @GetMapping("/modalEmpleado/{id}")
    public String getModalEmpleadoEdit(@PathVariable Long id, Model model) {
        Empleado empleado = iEmpleadoService.findById(Math.toIntExact(id));
        model.addAttribute("empleado", empleado);
        return "modals/modalEmpleado";
    }

    @GetMapping("/modalServicio")
    public String showModal5(Model model) {
        model.addAttribute("servicio", new Servicio());
        return "modals/modalServicio";  // Vista para el segundo modal
    }

    @GetMapping("/modalProveedor")
    public String showModal6(Model model) {
        model.addAttribute("proveedor", new Proveedor());
        return "modals/modalProveedor";  // Vista para el segundo modal
    }

    @Autowired
    private IProveedorService iProveedorService;

    @GetMapping("/modalProveedor/{id}")
    public String getModalProveedorEdit(@PathVariable Long id, Model model) {
        Proveedor proveedor= iProveedorService.findById(Math.toIntExact(id));
        model.addAttribute("proveedor", proveedor);
        return "modals/modalProveedor";
    }

    @GetMapping("/modalTablero")
    public String showModal7(Model model) {
        model.addAttribute("tablero", new Tablero());
        return "modals/modalTablero";  // Vista para el segundo modal
    }

    @Autowired
    private ITableroService iTableroService;

    @GetMapping("/modalTablero/{id}")
    public String getModalTableroEdit(@PathVariable Long id, Model model) {
        Tablero tablero= iTableroService.findById(Math.toIntExact(id));
        model.addAttribute("tablero", tablero);
        return "modals/modalTablero";
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

    @GetMapping("/clientes")
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

    @Autowired
    private IAlmacenRepository iAlmacenRepository;

    @GetMapping("/productos")
    public String listarProductos(Model model){
        model.addAttribute("productos", iAlmacenRepository.findAll());
        return "productos";
    }

    @Autowired
    private IServicioRepository iServicioRepository;

    @GetMapping("/servicios")
    public String listarServicios(Model model){
        model.addAttribute("servicios", iServicioRepository.findAll());
        return "servicios";
    }

    @Autowired
    private IDevolucionRepository iDevolucionRepository;

    @GetMapping("/devoluciones")
    public String listarDevolucion(Model model){
        model.addAttribute("devoluciones", iDevolucionRepository.findAll());
        return "devoluciones";
    }



    /*@Autowired
    private VentaRepository iVentaRepository;

    @GetMapping("/ventas")
    public String listarVenta(Model model){
        model.addAttribute("ventas", iVentaRepository.findAll());
        return "ventas";
    }*/

}
