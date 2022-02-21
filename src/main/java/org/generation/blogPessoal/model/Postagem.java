package org.generation.blogPessoal.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * 
 * @author USER
 *
 */

@Entity
@Table(name = "postagem")
public class Postagem {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
	@NotNull
	@Size(min = 5, max = 100)
	private String titulo;
	
	
	@NotNull
	@Size(min = 10, max = 500)
	private String texto;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date date = new java.sql.Date(System.currentTimeMillis());
	
	/**
	 * English:
	 * Using Entity Relationship, 'many to one' and using as a parameter the property that we are going to ignore
	 * within 'theme', when it reaches 'post' it stops displaying information.
	 * 
	 * Portuguese:
	 * Utilizando Relacionamento entre Entity, 'muitos para um' e utilizando como parametro a propriedade que vamos ignorar
	 * dentro de 'tema', quando chegar em 'postagem' ele para de apresentar informação.
	 * 
	 * @author Samuel
	 */
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Tema tema;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

}
