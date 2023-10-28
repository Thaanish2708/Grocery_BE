package com.grocery;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Grocery App Documentation",
				description = "Spring boot Grocery App APIs documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Nidin S",
						email = "nidinsree@gmail.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Github Spring boot Grocery app documentation",
				url = "https://github.com/Thaanish2708/Grocery_BE/tree/master"
		)
)
public class GroceryAppApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(GroceryAppApplication.class, args);
	}

}
