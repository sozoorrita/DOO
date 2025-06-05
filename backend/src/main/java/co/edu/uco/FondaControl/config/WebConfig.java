package co.edu.uco.FondaControl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// Aplica CORS a todos los endpoints de /api/v1/**
		registry.addMapping("/api/v1/**")
				.allowedOrigins("http://localhost:4200")   // Origen permitido
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
				.allowedHeaders("*")
				.allowCredentials(true);                   // ← habilita envío de cookies
	}
}
