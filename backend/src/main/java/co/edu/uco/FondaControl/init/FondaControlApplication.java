package co.edu.uco.FondaControl.init;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Clase principal que arranca la aplicación Spring Boot.
 */
@SpringBootApplication
@ComponentScan(basePackages = {
		"co.edu.uco.FondaControl.api", // <- esto es lo que importa
		"co.edu.uco.FondaControl.init"
})
public class FondaControlApplication {

	public static void main(String[] args) {
		SpringApplication.run(FondaControlApplication.class, args);
	}
}

