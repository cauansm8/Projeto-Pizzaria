package br.com.cursoDankiCode.Pizzaria.Config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configurations {

    @Bean
    public ModelMapper modelMapper(){           /// colocando o ModelMapper na API (dto <-> entidade)
        return new ModelMapper();
    }
}
