package br.com.livros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EntityScan({ "br.com.livros.dominio.modelo" })
@ComponentScan({ 
	"br.com.livros.api.controller", 
	"br.com.livros.dominio.servico"})
@EnableJpaRepositories({ "br.com.livros.dominio.repositorio" })
public class GerenciadorLivrosApplication {
	
	@Bean
    public Docket swagger() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.livros.api.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(informacoesApi());
    }
	
	private ApiInfo informacoesApi() {
		ApiInfoBuilder builder = new ApiInfoBuilder()
				.title("Gerenciador de Livros.")
				.description("Api para realização de um CRUD de Livros utilizando Spring Boot.")
				.version("1.0")
				.contact(new Contact(
						"Rafael Simonelli",
						"https://github.com/Rafael-Simonelli", 
						"rafael.simonelli@gmail.com"));
		
		return builder.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(GerenciadorLivrosApplication.class, args);
	}

}