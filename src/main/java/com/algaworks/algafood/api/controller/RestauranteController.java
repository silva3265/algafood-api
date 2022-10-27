package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

/*1 - Criando a classe RestauranteController
 *1.1Agora vamos anotá-la para ser um Controller Rest, respondendo na rota /restaurantes*/

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

	/*
	 * Vamos também injetar uma propriedade do tipo RestauranteRepository para
	 * fazermos as devidas buscas
	 */

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	/*
	 * 2 - Listando todos os restaurantes 2.1 - Vamos criar o método e mapeá-lo para
	 * que seja possível listarmos todos os restaurantes. Para isso, vamos utilizar
	 * o método listar() do repositório
	 */

	@GetMapping
	public List<Restaurante> listar() {
		return restauranteRepository.listar();
	}

	/*
	 * 3 - Listando por ID 3.1 - Para obter um restaurante por ID, iremos usar o
	 * método buscar() do repositório: 3.2 - Pronto, temos as buscas mapeadas para
	 * Restaurantes!
	 */

	@GetMapping("/{restauranteId}") // Para buscar apenas pelo o Id
	public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {
		Restaurante restaurante = restauranteRepository.buscar(restauranteId);

		if (restaurante != null) {
			return ResponseEntity.ok(restaurante);
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
		try {
			restaurante = cadastroRestaurante.salvar(restaurante);

			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}
	
	/* 1 - Vamos definir a assinatura e anotações referentes ao método que irá atualizar os restaurantes

	Receberemos um parâmetro do tipo Long, que será o ID do restaurante a ser atualizado. Esse parâmetro virá pela URL.

	Também precisaremos do corpo da requisição, ou seja os dados do Restaurante, para atualizarmos.

	O verbo HTTP que iremos utilizar, será o PUT.
	
	1.2 - Utilizamos @PathVariable para especificar que o parâmetro fará parte da URL

	@RequestBody para obter esses valores do body da requisição.

	@PutMapping para mapear nosso endpoint para esse verbo, com esse path.
	
	*/
	
	 @PutMapping("/{restauranteId}")
		public ResponseEntity<?> atualizar(@PathVariable Long restauranteId,
				@RequestBody Restaurante restaurante) {
		 try {
				Restaurante restauranteAtual = restauranteRepository.buscar(restauranteId);
				
				if (restauranteAtual != null) {
					BeanUtils.copyProperties(restaurante, restauranteAtual, "id");
					
					restauranteAtual = cadastroRestaurante.salvar(restauranteAtual);
					return ResponseEntity.ok(restauranteAtual);
				}
				
				return ResponseEntity.notFound().build();
			
			} catch (EntidadeNaoEncontradaException e) {
				return ResponseEntity.badRequest()
						.body(e.getMessage());
			}
	    }
	 
	 /*Fizemos já o tratamento, caso não exista um Restaurante com o ID recebido no parâmetro

	Fazemos o tratamento do objeto e chamamos o método salvar. Se tudo correr bem, 
	
	um código 200 será retornado.*/
}
