package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column (nullable = false)
	private String name;
	
	@Column (name = "taxa_frete", nullable = false) //Não aceita Nulo
	private BigDecimal taxaFrete;
	
	@ManyToOne //Muitos restaurantes possui uma cozinha
	@JoinColumn (name = "cozinha_id" , nullable = false) //Não aceita Nulo
	private Cozinha cozinha;

	 
	
	

}
