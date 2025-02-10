package br.com.cursoDankiCode.Pizzaria.Pizza;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, Long> {
}