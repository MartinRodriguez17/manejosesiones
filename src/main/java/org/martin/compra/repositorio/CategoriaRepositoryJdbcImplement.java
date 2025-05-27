package org.martin.compra.repositorio;

import org.martin.compra.models.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaRepositoryJdbcImplement implements Repository<Categoria>{

    //Creamos una variable donde vamos a guardar la conexion
    private Connection conn;
    public CategoriaRepositoryJdbcImplement(Connection conn) {
        this.conn = conn;
    }
    @Override
    public List<Categoria> Listar() throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        try (Statement stmt = conn.createStatement()){
        ResultSet rs = stmt.executeQuery("SELECT * from categoria");
            while (rs.next()){
                Categoria c = getCategoria(rs);
                categorias.add(c);
            }
        }
        return categorias;
    }
    @Override
    public Categoria porId(int id) throws SQLException {
        //Creo un objeto tipo nulo
        Categoria categoria = null;
        try(PreparedStatement stmt = conn.prepareStatement(
                "SELECT * from categoria where id = ?")){
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()){
                categoria = getCategoria(rs);
            }
        }
        return categoria;
    }

    @Override
    public void guardar(Categoria categoría) throws SQLException {
        //Declaro una varible de tipo String
        String sql;
        if(categoría.getIdCategoria()>0){
            sql="update categoria set nombre=?, descripcioon=? where idCategoria=?";
        }else{
            sql="inser into categoria (nombre, descripcion, condicion) values(?,?,1)";
        }
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, categoría.getNombre());
            stmt.setString(2, categoría.getDescripcion());
            stmt.setInt(3, categoría.getIdCategoria());
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {

    }
    private static Categoria getCategoria(ResultSet rs) throws SQLException {
        Categoria c = new Categoria();
        c.setNombre(rs.getString("nombre"));
        c.setDescripcion(rs.getString("descripcion"));
        c.setCondicion(rs.getInt("condicion"));
        c.setIdCategoria(rs.getInt("idCategoria"));
        return c;
    }
}

