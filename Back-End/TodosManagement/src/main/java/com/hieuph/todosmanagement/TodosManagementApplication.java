package com.hieuph.todosmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class TodosManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodosManagementApplication.class, args);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedMethods("*")
						.allowedHeaders("*")
						.allowedOrigins("http://localhost:3000", "http://localhost:3006")
						.allowCredentials(true)
						.exposedHeaders("Access-Control-Expose-Headers", "Content-Range");;
			}
		};
	}

}
