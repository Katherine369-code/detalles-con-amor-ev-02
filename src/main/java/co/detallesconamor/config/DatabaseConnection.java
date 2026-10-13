package co.detallesconamor.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Centraliza la conexión JDBC con MySQL. Ajustar credenciales para el entorno local. */
public final class DatabaseConnection {
    private static final String URL = System.getenv().getOrDefault(
            "DAC_DB_URL", "jdbc:mysql://localhost:3306/detalles_con_amor?useSSL=false&serverTimezone=UTC");
    private static final String USER = System.getenv().getOrDefault("DAC_DB_USER", "root");
    private static final String PASSWORD = System.getenv().getOrDefault("DAC_DB_PASSWORD", "");

    private DatabaseConnection() { }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
