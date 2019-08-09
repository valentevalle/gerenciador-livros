package br.com.livros.dominio.servico.impl.busca;

import java.util.function.Consumer;

/**
 * Manipulador de objetos para auxiliar na busca de livros.
 * 
 * @author Rafael
 */
public class ManipuladorDeBuscaDeLivro {

	private String atributoEsperado;
	private Consumer<String> strategyConsumoValor;
	private ManipuladorDeBuscaDeLivro proximo;
	
	/**
	 * Cria uma nova instância do manipulador sem o próximo manipulador delegado.
	 * 
	 * @param atributoEsperado     - O atributo esperado pelo manipulador.
	 * @param strategyConsumoValor - A estratégia de consumo do valor.
	 */
	public ManipuladorDeBuscaDeLivro(String atributoEsperado, Consumer<String> strategyConsumoValor) {
		this.atributoEsperado = atributoEsperado;
		this.strategyConsumoValor = strategyConsumoValor;
	}

	/**
	 * Cria uma nova instância do manipulador, com o próximo manipulador já delegado.
	 * 
	 * @param atributoEsperado     - O atributo esperado pelo manipulador.
	 * @param strategyConsumoValor - A estratégia de consumo do valor.
	 * @param proximo              - O próximo manipulador da sequência.
	 */
	public ManipuladorDeBuscaDeLivro(String atributoEsperado, Consumer<String> strategyConsumoValor,
			ManipuladorDeBuscaDeLivro proximo) {
		this.atributoEsperado = atributoEsperado;
		this.strategyConsumoValor = strategyConsumoValor;
		this.proximo = proximo;
	}

	/**
	 * Verifica se uma string contém um trecho.
	 * 
	 * @param origem    - A string de origem a ser analisada.
	 * @param fragmento - O fragmento a ser buscado.
	 * @return Se a string contém ou não o que foi indicado.
	 */
	protected boolean stringContemFragmento(String origem, String fragmento) {
		return (origem != null) && origem.contains(fragmento);
	}

	/**
	 * Analisa o atributo indicado e aciona a estratégia de consumo do valor,
	 * ou delega para o próximo manipulador.
	 * 
	 * @param atributo - O nome do atributo a ser analisado.
	 * @param valor    - O valor a ser atribuído.
	 */
	public void manipular(String atributo, String valor) {
		if (stringContemFragmento(atributo, atributoEsperado)) {
			strategyConsumoValor.accept(valor);
		} else if (proximo != null) {
			proximo.manipular(atributo, valor);
		}
	}

}