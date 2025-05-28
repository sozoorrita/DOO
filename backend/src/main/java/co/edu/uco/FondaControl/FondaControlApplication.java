package co.edu.uco.FondaControl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "co.edu.uco.FondaControl")
public class FondaControlApplication {
    public static void main(String[] args) {
        SpringApplication.run(FondaControlApplication.class, args);
    }
}
