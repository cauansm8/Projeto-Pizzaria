package br.com.cursoDankiCode.Pizzaria.Pizza;





import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RestController

@RequestMapping ("/pizzas")

@SecurityRequirement(name = "bearer-key")


public class PizzaController {

    private final PizzaService pizzaService;

    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @PostMapping
    public ResponseEntity<PizzaDTO> cadastrar(@RequestBody @Valid PizzaDTO dto, UriComponentsBuilder uriBuilder){
        PizzaDTO pizzaDTO = pizzaService.criarPizza(dto);
        URI endereco = uriBuilder.path("/pizzas/{id}").buildAndExpand(pizzaDTO.getId()).toUri();
        return ResponseEntity.created(endereco).body(pizzaDTO);
    }

    @GetMapping
    public ResponseEntity<Page<PizzaDTO>> buscarTodos (@PageableDefault (size = 10)Pageable paginacao){
        Page<PizzaDTO> pizzas = pizzaService.buscarTodos(paginacao);
        return ResponseEntity.ok(pizzas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PizzaDTO> buscarPorId(@PathVariable @NotNull Long id){
        PizzaDTO pizzaDTO = pizzaService.buscarPorId(id);
        return ResponseEntity.ok(pizzaDTO);
    }

    @PutMapping ("/{id}")
    public ResponseEntity<PizzaDTO> atualizar(@PathVariable @NotNull Long id, @RequestBody @Valid PizzaDTO dto){
        PizzaDTO pizzaAtualizada = pizzaService.atualizar(id, dto);

        return ResponseEntity.ok(pizzaAtualizada);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deletar (@PathVariable @NotNull Long id){
        pizzaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
