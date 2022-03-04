package org.generation.blogPessoal.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "tb_tema")
public class Tema {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
    
    @NotNull
	private String descricao;
	
   
    /**
	 * English:
	 * Using Entity Relationship, 'one to many' inside it we will use the 'mappedBy' parameter to know which class/table/attribute
	 * we are mapping, in this case we are mapping the 'theme' attribute of the posts table. The other parameter is the 'Cascade', used to indicate
	 * that if by chance we are going to change something like: delete/update referring to this theme, all posts that belong to the theme will suffer
	 * change, so if we delete something from this theme, all posts related to that theme will be deleted.
	 * using as a parameter the property that we are going to ignore within 'post', when it reaches 'theme' it stops displaying information.
	 * 
	 * Portuguese:
	 * Utilizando Relacionamento entre Entity, 'um para muitos' dentro dela usaremos o parametro 'mappedBy' para saber qual classe/tabela/atributo
	 * estamos mapeando, no caso estamos mapeando o atributo 'tema' da tabela de postagens. O outro parametro é o 'Cascade', usado para indicar
	 * que se por acaso formos alterar alguma coisa como: delete/update referente a este tema, todas as postagens que pertence ao tema sofrerá 
	 * alteração, logo se deletarmos algo desse tema, todas as postagens relacionada a esse tema será deleta.
	 * utilizando como parametro a propriedade que vamos ignorar dentro de 'postagem', quando chegar em 'tema' ele para de apresentar informação.
	 * 
	 * 
	 * @author Samuel
	 */ 
    @OneToMany(mappedBy = "tema", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("tema")
	private List<Postagem> postagem;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Postagem> getPostagem() {
		return postagem;
	}

	public void setPostagem(List<Postagem> postagem) {
		this.postagem = postagem;
	}
	
	
}
