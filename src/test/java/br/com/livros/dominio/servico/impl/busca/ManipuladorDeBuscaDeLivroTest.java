package br.com.livros.dominio.servico.impl.busca;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ManipuladorDeBuscaDeLivroTest {
	
	private ManipuladorDeBuscaDeLivro manipuladorComProximo;
	private ManipuladorDeBuscaDeLivro manipuladorSemProximo;
	private String valor;
	
	@Before
	public void setUp() throws Exception {
		valor = null;
		manipuladorSemProximo = new ManipuladorDeBuscaDeLivro("a", x -> valor = x);
		manipuladorComProximo = new ManipuladorDeBuscaDeLivro("b", x -> valor = x, manipuladorSemProximo);
	}
	
	@Test
	public void deve_Manipular_O_Valor_Com_Sucesso_Quando_O_Atributo_For_Equivalente() throws Exception {
		manipuladorComProximo.manipular("a", "teste");
		assertEquals("teste", valor);
		
		manipuladorComProximo.manipular("b", "outro teste");
		assertEquals("outro teste", valor);
	}
	
	@Test
	public void deve_Delegar_A_Manipulacao_Do_O_Valor_Com_Sucesso_Quando_O_Atributo_Nao_For_Equivalente_E_Houver_Manipulador() throws Exception {
		manipuladorComProximo.manipular("x", "teste");
		assertNull(valor);
	}

	@Test
	public void nao_Deve_Falhar_Ao_Analisar_O_Atributo_E_Ele_Nao_For_Equivalente_Sem_Manipulador() throws Exception {
		manipuladorComProximo.manipular("x", "outro teste");
		assertNull(valor);
	}
	
}