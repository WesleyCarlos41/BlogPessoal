package com.generation.blogpessoal.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//transforma o objeto criando uma tabela no banco de dados
@Entity

//Dá um nomr para a tabela do banco de dados
@Table(name= "tb_postagem")
public class Postagem {
	
	// Define a coluna de id como chave primaria
	@Id
	
	// equivalente ao Auto_inclement no mysql
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	@NotNull
	@Size(min = 5, max = 100, message="O campo deveve ter no minino 5 caracteres e no maximo 100 caracteres")
	public String titulo;
	
	@NotNull
	public String texto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
}
