package com.ejemploo.soaa.model;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;
@Data
public class Servicio {

    int id_servicio;
    String tipo_servicio;
    String descripcion;
    int estado;
    int id_empleado;
    private String name;
    @NotNull()
    private BigDecimal precio;

}
