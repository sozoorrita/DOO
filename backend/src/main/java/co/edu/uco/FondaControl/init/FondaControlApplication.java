package co.edu.uco.FondaControl.init;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"co.edu.uco.FondaControl.api",
		"co.edu.uco.FondaControl.config"  // Solo carga este DAO
})
public class FondaControlApplication {

	public static void main(String[] args) {
		SpringApplication.run(FondaControlApplication.class, args);
	}
}
