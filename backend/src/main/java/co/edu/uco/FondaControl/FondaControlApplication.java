package co.edu.uco.FondaControl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "co.edu.uco.FondaControl"})
public class FondaControlApplication {
    public static void main(String[] args) {
        SpringApplication.run(FondaControlApplication.class, args);
    }
}
