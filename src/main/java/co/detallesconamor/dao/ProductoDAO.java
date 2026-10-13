package co.detallesconamor.dao;

import co.detallesconamor.config.DatabaseConnection;
import co.detallesconamor.model.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Acceso a datos mediante JDBC y consultas parametrizadas. */
public class ProductoDAO {
    public List<Producto> listar() throws SQLException {
        String sql = "SELECT id, nombre, descripcion, precio, stock FROM productos ORDER BY id DESC";
        List<Producto> productos = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) productos.add(mapear(rs));
        }
        return productos;
    }

    public Producto buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, nombre, descripcion, precio, stock FROM productos WHERE id = ?";
        try (Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? mapear(rs) : null; }
        }
    }

    public void insertar(Producto p) throws SQLException {
        String sql = "INSERT INTO productos (nombre, descripcion, precio, stock) VALUES (?, ?, ?, ?)";
        try (Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getNombre()); ps.setString(2, p.getDescripcion()); ps.setBigDecimal(3, p.getPrecio()); ps.setInt(4, p.getStock()); ps.executeUpdate();
        }
    }

    public boolean actualizar(Producto p) throws SQLException {
        String sql = "UPDATE productos SET nombre = ?, descripcion = ?, precio = ?, stock = ? WHERE id = ?";
        try (Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getNombre()); ps.setString(2, p.getDescripcion()); ps.setBigDecimal(3, p.getPrecio()); ps.setInt(4, p.getStock()); ps.setInt(5, p.getId()); return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        try (Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement("DELETE FROM productos WHERE id = ?")) {
            ps.setInt(1, id); return ps.executeUpdate() > 0;
        }
    }

    private Producto mapear(ResultSet rs) throws SQLException {
        return new Producto(rs.getInt("id"), rs.getString("nombre"), rs.getString("descripcion"), rs.getBigDecimal("precio"), rs.getInt("stock"));
    }
}
