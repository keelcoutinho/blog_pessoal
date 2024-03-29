package com.blogpessoal.blogpessoal.configuration;

import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;


@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI springBlogpessoalOpenAPI() {
		return new OpenAPI()
				.info(new Info()
					.title("Aplicação do Blog Pessoal")
					.description("Projeto desenvolvido pela Turma DH Porto")
					.version("v.0.1")
				.license(new License()
					.name("Digital House")
					.url("https://digitalhouse.com"))
				.contact(new Contact()
					.name("Treinamento Porto")
					.email("portoseguro@porto.com")))
				.externalDocs(new ExternalDocumentation()
						.description("GitHub")
						.url("https://github.com/portoseguro"));
				 
	}
	
	@Bean
	public OpenApiCustomiser customerGlobalHeaderOpenApiCustomiser() {
		
		return openApi -> {
			openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
				
				ApiResponses apiResponses = operation.getResponses();
				
				apiResponses.addApiResponse("200", createApiResponse("Sucesso!"));
				apiResponses.addApiResponse("201", createApiResponse("Objeto Persistido"));
				apiResponses.addApiResponse("204", createApiResponse("Objeto Excluído"));
				apiResponses.addApiResponse("400", createApiResponse("Erro na Requisição"));
				apiResponses.addApiResponse("401", createApiResponse("Acesso não autorizado"));
				apiResponses.addApiResponse("404", createApiResponse("Objeto não encontrado"));
				apiResponses.addApiResponse("500", createApiResponse("Erro na aplicação"));
			}));
		};
	}
	
	private ApiResponse createApiResponse(String message) {
		
		return new ApiResponse().description(message);
	}
	
}
