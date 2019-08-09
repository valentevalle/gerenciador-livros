package br.com.livros.dominio.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Modelo de domínio que representa um Livro.
 * @author Rafael
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@ToString
public class Livro implements Serializable {
	
	private static final long serialVersionUID = -271443485800335244L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID", nullable=false, unique=true)
	private Long id;	
	
	@Column(name="ISBN", nullable=false)
	@NotBlank(message="O ISBN é obrigatório")
	private String isbn;
	
	@Column(name="AUTOR")
	private String autor;
	
	@Column(name="NOME")
	private String nome;
	
	@Column(name="PRECO")
	private Double preco;
	
	@Column(name="DATA_PUBLICACAO")
	private Date dataPublicacao;
	
	@Column(name="URL_IMAGEM_CAPA")
	private String urlImagemCapa;

}