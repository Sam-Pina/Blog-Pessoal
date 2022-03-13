package org.generation.blogPessoal.configuration;

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
	
	/**
	 * English:
	 * This Spring Core annotation indicates that a method produces a bean to be managed by the Spring container. 
	 * This is commonly used in classes annotated with the @Configuration annotation in Java based configuration in Spring. 
	 * Basically when you put the @Bean annotation, you are telling Spring that you want to create this object and 
	 * make it available for other classes to use it as a dependency, for example.
	 * 
	 * Portuguese:
     * Esta anotação do Spring Core indica que um método produz um bean a ser gerenciado pelo contêiner do Spring. 
     * Isso é comumente usado nas classes anotadas com a anotação @Configuration na configuração baseada em Java no Spring. 
     * Basicamente quando você coloca a anotação @Bean, você está dizendo para Spring que quer criar esse objeto e 
     * deixar ele disponível para outras classes utilizarem ele como dependência, por exemplo.
     * 
     * @author Samuel
     * @since final version 03/13/22(eng); versão final 13/03/22(pt-br);
     */
	@Bean
	public OpenAPI springBlogPessoalOpenAPI() {
		return new OpenAPI()
				.info(new Info()
					.title("Projeto Blog Pessoal")
					.description("Projeto Blog Pessoal - Generation Brasil 2022")
					.version("v0.0.1")
				.license(new License()
					.name("Generation Brasil")
					.url("https://brazil.generation.org/"))
				.contact(new Contact()
					.name("Github Samuel")
					.url("https://github.com/Sam-Pina")
					.email("lesmak.ss@gmail.com")))
				.externalDocs(new ExternalDocumentation()
					.description("Github Project")
					.url("https://github.com/Sam-Pina/Blog-Pessoal"));
	}
	

    /**
     * A Classe OpenApiCustomiser permite personalizar o Swagger, baseado na 
     * Especificação OpenAPI. O Método abaixo, personaliza todas as mensagens 
     * HTTP Responses (Respostas das requisições) do Swagger.
     */
	
	@Bean
	public OpenApiCustomiser customerGlobalHeaderOpenApiCustomiser() {

		return openApi -> {
			openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {

				ApiResponses apiResponses = operation.getResponses();

				apiResponses.addApiResponse("200", createApiResponse("Sucesso!"));
				apiResponses.addApiResponse("201", createApiResponse("Objeto Persistido!"));
				apiResponses.addApiResponse("204", createApiResponse("Objeto Excluído!"));
				apiResponses.addApiResponse("400", createApiResponse("Erro na Requisição!"));
				apiResponses.addApiResponse("401", createApiResponse("Acesso Não Autorizado!"));
				apiResponses.addApiResponse("404", createApiResponse("Objeto Não Encontrado!"));
				apiResponses.addApiResponse("500", createApiResponse("Erro na Aplicação!"));

			}));
		};
	}

	private ApiResponse createApiResponse(String message) {

		return new ApiResponse().description(message);

	}

}
