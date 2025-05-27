package co.edu.uco.FondaControl.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


@Component
public class PruebaConexionNeon implements CommandLineRunner {

    private final DataSource dataSource;

    public PruebaConexionNeon(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) {
        System.out.println("🔌 Iniciando prueba de conexión a Neon PostgreSQL...");

        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT version()")
        ) {
            if (resultSet.next()) {
                System.out.println("✅ Conexión exitosa a Neon. Versión del servidor: " + resultSet.getString(1));
            } else {
            	
                System.out.println("⚠️ Conexión establecida, pero sin resultados.");
            }
        } catch (Exception e) {
            System.err.println("❌ Error al conectar con Neon PostgreSQL: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

