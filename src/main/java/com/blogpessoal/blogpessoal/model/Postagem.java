package com.blogpessoal.blogpessoal.model;


import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


//para o JPA reconhecer essa classe é preciso fazer algumas anotaçoes @ para poder gerar uma ligação entre a classe e o BD.

@Entity
@Table(name = "postagem") // está dizendo que essa entidade(classe) vai criar dentro do banco de datos uma tabela com o nome postagem
public class Postagem {
	
	@Id // falando que o Id vai ser a chave primaria na tabela postagem
	@GeneratedValue(strategy = GenerationType.IDENTITY) // valor gerado e a estratégia será criar uma identidade que irá se transformar em uma chave primaria (sem duplicidade) para o Id (como um auto incremente)
	private long id;
	
	@NotNull // define que o usuário é obrigado a colocar alguma coisa no campo título // tem que criar uma dependência
	@Size(min = 5, max = 100) //define quantidade minima e maxima de caracteres
	private String titulo;
	
	@NotNull 
	@Size(min = 10, max = 500)
	private String conteudo;
	
	@UpdateTimestamp //indica que é um atibuto temporal e qual tipo de atibuto eu quero
	private LocalDateTime date ;
	//@Temporal(TemporalType.TIMESTAMP) //indica que é um atibuto temporal e qual tipo de atibuto eu quero
	//private Date date = new java.sql.Date(System.currentTimeMillis()); //captura exatamente a data, hora, minuto, segundo e milesimo de segundo
	
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Tema tema;
	
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Usuario usuario;
	
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
	public String getConteudo() {
		return conteudo;
	}
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	public Tema getTema() {
		return tema;
	}
	public void setTema(Tema tema) {
		this.tema = tema;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	

}
