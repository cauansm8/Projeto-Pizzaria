package br.com.cursoDankiCode.Pizzaria.Pizza;



import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service

public class PizzaService {

    private final PizzaRepository pizzaRepository;

    private final ModelMapper modelMapper;

    public PizzaService(PizzaRepository pizzaRepository, ModelMapper modelMapper) {
        this.pizzaRepository = pizzaRepository;
        this.modelMapper = modelMapper;
    }

    public PizzaDTO criarPizza(PizzaDTO dto){
        Pizza pizza = modelMapper.map(dto, Pizza.class);
        pizzaRepository.save(pizza);
        return modelMapper.map(pizza, PizzaDTO.class);
    }


    public Page<PizzaDTO> buscarTodos(Pageable paginacao) {
        return pizzaRepository.findAll(paginacao).map(p -> modelMapper.map(p, PizzaDTO.class));

    }

    public PizzaDTO buscarPorId(Long ID) {
        Pizza pizza = pizzaRepository.findById(ID).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(pizza, PizzaDTO.class);

    }

    public PizzaDTO atualizar(Long id, PizzaDTO dto) {
        Pizza pizza = modelMapper.map(dto, Pizza.class);
        pizza.setId(id);
        pizzaRepository.save(pizza);
        return modelMapper.map(pizza, PizzaDTO.class);
    }

    public void deletar(Long id) {
        Pizza pizza = pizzaRepository.findById(id).orElseThrow(EntityNotFoundException:: new);
        pizzaRepository.delete(pizza);

    }
}