package org.martin.compra.repositorio;

import java.sql.SQLException;
import java.util.List;

/*
*<T> Es un parametro generico que permite que la interfaz sea utilizada
* como se desee o cualquier tipo de objeto(entidad ) que se desee manejar
 */
public interface Repository <T>{
    List<T> Listar() throws SQLException;  //Permite listar todos los datos de la base datos
    T porId(int id) throws SQLException;   //Permite buscar un elemento de base por su identificador
    void guardar(T t) throws SQLException;  //Permite crear un nuevo registro de la base de datos
    void eliminar(int id) throws SQLException;  //Permite eliminar por medio de identificador un objeto

}
