package com.ejemploo.soaa.controller;
import com.ejemploo.soaa.model.Venta;
import com.ejemploo.soaa.repository.VentaService;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
@Controller
@RequestMapping("/venta")
public class VentaController {
    @Autowired
    private VentaService ventaService;

    @PostMapping
    public void save(@RequestBody Venta venta) {
        ventaService.save(venta);
    }

    @GetMapping
    public List<Venta> findAll() {
        return ventaService.findAll();
    }

    /*
    @GetMapping("/reporte/{id_venta}")
    public ResponseEntity<Resource> generateReport(@PathVariable int id_venta) {
        Optional<Venta> optionalVenta = ventaService.findById(id_venta);
        if (!optionalVenta.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Venta venta = optionalVenta.get();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc, PageSize.A4);

            PdfFont font = PdfFontFactory.createFont("Helvetica", "Cp1252", true);
            document.setFont(font);

            // Título
            Paragraph titulo = new Paragraph("FACTURA ELECTRÓNICA")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(14)
                    .setBold();
            document.add(titulo);

            // Número de factura
            Paragraph numeroFactura = new Paragraph("F002-00003848")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(12);
            document.add(numeroFactura);

            document.add(new Paragraph("\n")); // Espacio en blanco

            // Información del cliente
            Table tableCliente = new Table(new float[]{1, 2});
            tableCliente.addCell(new Cell().add(new Paragraph("Señores:").setBold()));
            tableCliente.addCell(new Cell().add(new Paragraph(venta.getClienteNombre())));
            tableCliente.addCell(new Cell().add(new Paragraph("RUC:").setBold()));
            tableCliente.addCell(new Cell().add(new Paragraph(venta.getRuc())));
            tableCliente.addCell(new Cell().add(new Paragraph("Fecha:").setBold()));
            tableCliente.addCell(new Cell().add(new Paragraph(venta.getFecha().toString())));
            document.add(tableCliente);

            document.add(new Paragraph("\n")); // Espacio en blanco

            // Tabla de productos
            Table tableProductos = new Table(new float[]{3, 4, 1, 1, 1});
            tableProductos.setWidth(PageSize.A4.getWidth() - document.getLeftMargin() - document.getRightMargin());
            tableProductos.addHeaderCell(new Cell().add(new Paragraph("ID Producto").setBold()).setBackgroundColor(ColorConstants.LIGHT_GRAY));

            tableProductos.addHeaderCell(new Cell().add(new Paragraph("Producto").setBold()).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            tableProductos.addHeaderCell(new Cell().add(new Paragraph("Cant.").setBold()).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            tableProductos.addHeaderCell(new Cell().add(new Paragraph("Precio").setBold()).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            tableProductos.addHeaderCell(new Cell().add(new Paragraph("Importe").setBold()).setBackgroundColor(ColorConstants.LIGHT_GRAY));

            // Añadir los detalles del producto
            tableProductos.addCell(new Cell().add(new Paragraph(String.valueOf(venta.getId_producto()))));
            tableProductos.addCell(new Cell().add(new Paragraph(venta.getProductoNombre())));
            tableProductos.addCell(new Cell().add(new Paragraph(String.valueOf(venta.getCantidad_venta()))));
            tableProductos.addCell(new Cell().add(new Paragraph(venta.getTotal_pagar().toString())));
            tableProductos.addCell(new Cell().add(new Paragraph(venta.getTotal_pagar().multiply(new BigDecimal(venta.getCantidad_venta())).toString())));
            document.add(tableProductos);

            document.add(new Paragraph("\n")); // Espacio en blanco

            // Información del vendedor
            Paragraph vendedor = new Paragraph("VENDEDOR(A): " + venta.getEmpleadoNombre())
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFontSize(12);
            document.add(vendedor);

            document.add(new Paragraph("\n")); // Espacio en blanco

            // Mensaje de agradecimiento
            Paragraph mensaje = new Paragraph("Representación impresa de la factura electrónica\nGracias por su preferencia")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(10);
            document.add(mensaje);

            document.close();

            ByteArrayResource resource = new ByteArrayResource(out.toByteArray());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=factura.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

     */

    @GetMapping("/reporte/{id_venta}")
    public ResponseEntity<Resource> generateReport(@PathVariable int id_venta) {
        Optional<Venta> optionalVenta = ventaService.findById(id_venta);
        if (!optionalVenta.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Venta venta = optionalVenta.get();

        try {
            // Directorio donde se guardará el PDF
            String directoryPath = "C:\\Users\\bruno\\OneDrive\\Escritorio"; // Cambia esto a la ruta de tu carpeta
            String fileName = "factura_" + id_venta + ".pdf";
            File file = new File(directoryPath, fileName);
            file.getParentFile().mkdirs(); // Crea los directorios si no existen

            // Crear el PDF
            PdfWriter writer = new PdfWriter(file.getAbsolutePath());
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc, PageSize.A4);

            PdfFont font = PdfFontFactory.createFont("Helvetica", "Cp1252", true);
            document.setFont(font);

            // Título
            Paragraph titulo = new Paragraph("FACTURA ELECTRÓNICA")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(14)
                    .setBold();
            document.add(titulo);

            // Número de factura
            Paragraph numeroFactura = new Paragraph("F002-00003848")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(12);
            document.add(numeroFactura);

            document.add(new Paragraph("\n")); // Espacio en blanco

            // Información del cliente
            Table tableCliente = new Table(new float[]{1, 2});
            tableCliente.addCell(new Cell().add(new Paragraph("Señores:").setBold()));
            tableCliente.addCell(new Cell().add(new Paragraph(venta.getClienteNombre())));
            tableCliente.addCell(new Cell().add(new Paragraph("RUC:").setBold()));
            tableCliente.addCell(new Cell().add(new Paragraph(venta.getRuc())));
            tableCliente.addCell(new Cell().add(new Paragraph("Fecha:").setBold()));
            tableCliente.addCell(new Cell().add(new Paragraph(venta.getFecha().toString())));
            document.add(tableCliente);

            document.add(new Paragraph("\n")); // Espacio en blanco

            // Tabla de productos
            Table tableProductos = new Table(new float[]{3, 4, 1, 1, 1});
            tableProductos.setWidth(PageSize.A4.getWidth() - document.getLeftMargin() - document.getRightMargin());
            tableProductos.addHeaderCell(new Cell().add(new Paragraph("ID Producto").setBold()).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            tableProductos.addHeaderCell(new Cell().add(new Paragraph("Producto").setBold()).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            tableProductos.addHeaderCell(new Cell().add(new Paragraph("Cant.").setBold()).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            tableProductos.addHeaderCell(new Cell().add(new Paragraph("Precio").setBold()).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            tableProductos.addHeaderCell(new Cell().add(new Paragraph("Importe").setBold()).setBackgroundColor(ColorConstants.LIGHT_GRAY));

            // Añadir los detalles del producto
            tableProductos.addCell(new Cell().add(new Paragraph(String.valueOf(venta.getId_producto()))));
            tableProductos.addCell(new Cell().add(new Paragraph(venta.getProductoNombre())));
            tableProductos.addCell(new Cell().add(new Paragraph(String.valueOf(venta.getCantidad_venta()))));
            tableProductos.addCell(new Cell().add(new Paragraph(venta.getTotal_pagar().toString())));
            tableProductos.addCell(new Cell().add(new Paragraph(venta.getTotal_pagar().multiply(new BigDecimal(venta.getCantidad_venta())).toString())));
            document.add(tableProductos);

            document.add(new Paragraph("\n")); // Espacio en blanco

            // Información del vendedor
            Paragraph vendedor = new Paragraph("VENDEDOR(A): " + venta.getEmpleadoNombre())
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFontSize(12);
            document.add(vendedor);

            document.add(new Paragraph("\n")); // Espacio en blanco

            // Mensaje de agradecimiento
            Paragraph mensaje = new Paragraph("Representación impresa de la factura electrónica\nGracias por su preferencia")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(10);
            document.add(mensaje);

            document.close();

            // Leer el archivo generado y convertirlo a un recurso
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(file.toPath()));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
