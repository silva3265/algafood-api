package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@RestController
@RequestMapping("/cozinhas") //Todas as requisições "/cozinhas" vao cair nessa requisição
public class CozinhaController {
	
	@Autowired
	private CozinhaRepository cozinhaRepository; //Variavel de Instancia
	
	@GetMapping 
	public List<Cozinha> listar(){ //Metodo 
		return cozinhaRepository.listar(); //Chamando metodo listar do Repositorio, e devolvemos a lista de cozinhas
	}
	
 
}
