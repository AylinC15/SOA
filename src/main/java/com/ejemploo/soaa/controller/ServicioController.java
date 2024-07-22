package com.ejemploo.soaa.controller;
import com.ejemploo.soaa.model.ServiceResponse;
import com.ejemploo.soaa.model.Servicio;
import com.ejemploo.soaa.service.IServicioService;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.text.PageSize;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.kernel.colors.ColorConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
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


    @GetMapping("/cotizacion/{id_empleado}")
    public ResponseEntity<ByteArrayResource> generateCotizacion(@PathVariable int id_empleado) {
        List<Servicio> servicios = iServicioService.findByEmpleadoId(id_empleado);
        if (servicios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        String nombreEmpleado = servicios.get(0).getName();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            PdfFont font = PdfFontFactory.createFont("Helvetica", "Cp1252", true);
            document.setFont(font);

            // Título
            Paragraph titulo = new Paragraph("COSTOS DETALLADOS")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(14)
                    .setBold();
            document.add(titulo);

            document.add(new Paragraph("\n"));

            // Añadir el nombre del empleado debajo del título
            Paragraph empleadoInfo = new Paragraph("Cotización preparada por: " + nombreEmpleado)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(12)
                    .setItalic();
            document.add(empleadoInfo);

            document.add(new Paragraph("\n"));

            // Tabla de servicios con 4 columnas
            Table tableServicios = new Table(new float[]{3, 3, 2, 2});
            tableServicios.setWidth(PageSize.A4.getWidth() - document.getLeftMargin() - document.getRightMargin());
            tableServicios.addHeaderCell(new Cell().add(new Paragraph("Producto o servicio").setBold()).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            tableServicios.addHeaderCell(new Cell().add(new Paragraph("Descripción").setBold()).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            tableServicios.addHeaderCell(new Cell().add(new Paragraph("Precio unitario").setBold()).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            tableServicios.addHeaderCell(new Cell().add(new Paragraph("Precio total").setBold()).setBackgroundColor(ColorConstants.LIGHT_GRAY));

            BigDecimal subtotal = BigDecimal.ZERO;
            for (Servicio servicio : servicios) {
                tableServicios.addCell(new Cell().add(new Paragraph(servicio.getTipo_servicio())));
                tableServicios.addCell(new Cell().add(new Paragraph(servicio.getDescripcion())));
                tableServicios.addCell(new Cell().add(new Paragraph(servicio.getPrecio().toString())));
                tableServicios.addCell(new Cell().add(new Paragraph(servicio.getPrecio().toString())));
                subtotal = subtotal.add(servicio.getPrecio());
            }
            document.add(tableServicios);

            document.add(new Paragraph("\n"));

            // Calcular el impuesto y total
            BigDecimal impuesto = subtotal.multiply(new BigDecimal("0.16"));
            BigDecimal total = subtotal.add(impuesto);

            // Agregar subtotales
            Table tableTotales = new Table(2);
            tableTotales.addCell(new Cell().add(new Paragraph("Subtotal:").setBold()));
            tableTotales.addCell(new Cell().add(new Paragraph(subtotal.toString())));
            tableTotales.addCell(new Cell().add(new Paragraph("Impuesto 16%:").setBold()));
            tableTotales.addCell(new Cell().add(new Paragraph(impuesto.toString())));
            tableTotales.addCell(new Cell().add(new Paragraph("Cotización:").setBold()));
            tableTotales.addCell(new Cell().add(new Paragraph(total.toString())));
            document.add(tableTotales);

            // Obtener el tamaño de la página y los márgenes
            Rectangle pageSize = pdfDoc.getDefaultPageSize();
            float margin = 36; // 36 puntos = 0.5 pulgadas, un margen común

            // Calcular el ancho disponible para el texto de firma
            float firmaWidth = 200; // Ancho fijo para la sección de firma

            // Modificar la firma del empleado en la parte inferior izquierda
            Paragraph empleadoFirma = new Paragraph("_____________________")
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFixedPosition(margin, margin + 30, firmaWidth);
            document.add(empleadoFirma);

            Paragraph empleadoNombre = new Paragraph(nombreEmpleado)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFixedPosition(margin, margin, firmaWidth);
            document.add(empleadoNombre);

            document.close();

            ByteArrayResource resource = new ByteArrayResource(out.toByteArray());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cotizacion.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}