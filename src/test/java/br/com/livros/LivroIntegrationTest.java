package br.com.livros;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.livros.api.controller.EndpointLivros;
import br.com.livros.api.controller.ManipuladorDeExceptions;
import br.com.livros.dominio.modelo.Livro;
import br.com.livros.dominio.repositorio.RepositorioDeLivros;
import br.com.livros.dominio.servico.ServicoDeDominioLivro;
import br.com.livros.helpers.Stubs;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("/teste.sql")
@Transactional
public class LivroIntegrationTest {
	
	private static final String URL_BASE_API_LIVROS = "/api/v1/livros/";
	
	private Livro primeiroLivro;
	private Livro segundoLivro;
	private Livro terceiroLivro;	
	private MockMvc mockMvc;
	private JacksonTester<Livro> jsonLivro;
	
	@Autowired
	private RepositorioDeLivros repositorioDeLivros;
	
	@Autowired
	private ServicoDeDominioLivro servicoDeDominioLivro;
	
	@Before
    public void setup() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(new EndpointLivros(servicoDeDominioLivro))
        		.setControllerAdvice(new ManipuladorDeExceptions())
        		.build();

        var livros = repositorioDeLivros.findAll();        
        primeiroLivro = livros.get(0);
        segundoLivro = livros.get(1);
        terceiroLivro = livros.get(2);
    }
	
	@Test
	public void deve_Cadastrar_Um_Livro_Com_Sucesso() throws Exception {
		var livro = Stubs.livro("it");		
		var livroJson = jsonLivro.write(livro).getJson();
		var requestBuilder = MockMvcRequestBuilders.post(URL_BASE_API_LIVROS)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(livroJson);
		
		var esperado = Stubs.livro("it");
		esperado.setId(7L);
		var esperadoJson = jsonLivro.write(esperado).getJson();
		
		mockMvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andExpect(content().json(esperadoJson));
	}
	
	@Test
	public void deve_Alterar_Um_Livro_Com_Sucesso() throws Exception {
		var livro = Stubs.livro("it");
		livro.setId(segundoLivro.getId());
		livro.setIsbn("esse valor não existe");
		var livroJson = jsonLivro.write(livro).getJson();
		var url = URL_BASE_API_LIVROS + segundoLivro.getId().toString(); 
		var requestBuilder = MockMvcRequestBuilders.put(url)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(livroJson);
		
		mockMvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andExpect(content().json(livroJson));
	}
	
	@Test
	public void deve_Excluir_Um_Livro_Com_Sucesso() throws Exception {
		var url = URL_BASE_API_LIVROS + primeiroLivro.getId().toString(); 
		var requestBuilder = MockMvcRequestBuilders.delete(url)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(requestBuilder).andExpect(status().isOk());
	}
	
	@Test
	public void deve_Listar_Um_Livro_Com_Sucesso() throws Exception {
		var livroJson = jsonLivro.write(segundoLivro).getJson();
		var url = URL_BASE_API_LIVROS + "isbn/" + segundoLivro.getIsbn() + "?ordem=id";
		var requestBuilder = MockMvcRequestBuilders.get(url)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		livroJson = livroJson.replace("1221361200000", "\"2008-09-14\"");
		
		mockMvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andExpect(content().json("[" + livroJson + "]"));
	}
	
	@Test
	public void deve_Falhar_Ao_Cadastrar_Um_Livro_Com_Isbn_Repetido() throws Exception {
		var livro = Stubs.livro("it");		
		livro.setIsbn(segundoLivro.getIsbn());
		
		var livroJson = jsonLivro.write(livro).getJson();
		var requestBuilder = MockMvcRequestBuilders.post(URL_BASE_API_LIVROS)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(livroJson);
		
		mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());
	}
	
	@Test
	public void deve_Falhar_Ao_Alterar_Um_Livro_Com_Isbn_Repetido() throws Exception {
		var livro = Stubs.livro("it");		
		livro.setIsbn(terceiroLivro.getIsbn());
		
		var livroJson = jsonLivro.write(livro).getJson();
		var url = URL_BASE_API_LIVROS + segundoLivro.getId().toString(); 
		var requestBuilder = MockMvcRequestBuilders.put(url)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(livroJson);
		
		mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());
	}
	
	@Test
	public void deve_Falhar_Ao_Alterar_Um_Livro_Inexistente() throws Exception {
		var livro = Stubs.livro("it");		
		var livroJson = jsonLivro.write(livro).getJson();
		var url = URL_BASE_API_LIVROS + "1234567"; 
		var requestBuilder = MockMvcRequestBuilders.put(url)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(livroJson);
		
		mockMvc.perform(requestBuilder).andExpect(status().isNotFound());
	}
	
	@Test
	public void deve_Falhar_Ao_Excluir_Um_Livro_Inexistente() throws Exception {
		var url = URL_BASE_API_LIVROS + "1234567"; 
		var requestBuilder = MockMvcRequestBuilders.delete(url)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(requestBuilder).andExpect(status().isNotFound());
	}
	
	@Test
	public void deve_Falhar_Ao_Listar_Um_Livro_Inexistente() throws Exception {
		var url = URL_BASE_API_LIVROS + "isbn/inexistente?ordem=id"; 
		var requestBuilder = MockMvcRequestBuilders.get(url)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(requestBuilder).andExpect(status().isNotFound());
	}
	
}