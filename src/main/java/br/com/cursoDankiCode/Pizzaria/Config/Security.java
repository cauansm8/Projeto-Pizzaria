package br.com.cursoDankiCode.Pizzaria.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity

public class Security {

    private final Filter filter;

    public Security(Filter filter) {
        this.filter = filter;
    }

    @Bean
    public SecurityFilterChain filtroSeguranca(HttpSecurity http) throws Exception {                  ///  filtro de segurança + documentação Swagger
        http.csrf(csrf -> csrf.disable())                                                                                                 /// desativa a proteção à ataques csrf (ataques às APIs STATEFULL -> guardam o estado do User)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))                /// torna a API stateless (não guarda estado do USER) - se não, todos os métodos de user + pizzas estariam indisponíveis E o método de login também
                        .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST, "/login").permitAll()          ///  define que o único método permitido sem a autenticação do TOKEN -> o método post LOGIN
                                /// documentação SWAGGER (permitindo o acesso livre aos links da documentação do SWAGGER -> o SpringSecurity bloqueia o acesso por padrão)
                                .requestMatchers("/v3/api-docs/**").permitAll()             /// Endpoint que fornece a especificação OpenAPI em JSON
                                .requestMatchers("/swagger-ui.html").permitAll()            /// Página principal da interface gráfica do Swagger UI
                                .requestMatchers("/swagger-ui/**").permitAll()              /// Todos os recursos internos do Swagger UI, como estilos e scripts
                                .anyRequest().authenticated())                                        ///  para qualquer outra requisição (tirando login), precisa estar autenticado
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);                 /// passa o filtro UsernamePass... antes do filtro filter (do Spring Security)

        return http.build();

    }

    @Bean
    /// colocando métodos de autenticação na API
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    /// colocando criptografia do tipo BCrypt para senhas na API
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
