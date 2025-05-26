package co.edu.uco.FondaControl.init;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal que arranca la aplicación Spring Boot.
 */
@SpringBootApplication(scanBasePackages = "co.edu.uco.FondaControl")
public class FondaControlApplication {

	public static void main(String[] args) {
		SpringApplication.run(FondaControlApplication.class, args);
	}
}
