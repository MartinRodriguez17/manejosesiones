package org.martin.compra.services;

import org.martin.compra.models.Productos;
import java.util.List;

public interface ProductoService {
    // Método para obtener la lista de productos
    List<Productos>listar();
}