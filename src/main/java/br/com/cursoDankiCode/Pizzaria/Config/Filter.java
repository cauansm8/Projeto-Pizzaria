package br.com.cursoDankiCode.Pizzaria.Config;


import br.com.cursoDankiCode.Pizzaria.usuario.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class Filter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    public Filter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }


    @Override                   ///                     requisicao                  resposta                filtro
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        /// RESUMINDO:  Pega o token - valida (pega o user) - permite/autentica o user no contexto

        String token = buscarToken(request);                                    /// pega o token da requisicao (chama a funcao de baixo)

        if (token != null){
            var usuarioLogin = tokenService.buscaLoginToken(token);             /// retorna o login do user (esta escondido no token - ".getSubject()")
            var usuario = userRepository.findByLogin(usuarioLogin);             /// procura no BD em busca do registro do user e retorna tudo (id, login, password)

            var autenticador = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                                                                                /// recebe User (Login) + Credenciais (não passa nada, pois já estamos usando o JWT então não precisa de senha)
                                                                                ///                        + Permissões (como a API tem somente entidade para USER -> logo define as permissões somente de user)

            SecurityContextHolder.getContext().setAuthentication(autenticador); /// user autenticado para todas as requisições (2 horas)
        }

        /// fluxo de requisição
        filterChain.doFilter(request, response);                                /// basicamente diz que:
                                                                                /// está logado -> permita as requisições
    }                                                                           /// não está logado / expirou o tempo -> bloqueie as requisições

    private String buscarToken(HttpServletRequest request) {                    /// Metodo para pegar o token do cabeçalho e modifica-lo


        var authorization = request.getHeader("Authorization");              /// pega o token do cabeçalho

        if (authorization != null) {
            return authorization.replace("Bearer ", "");      /// remove "Bearer" do token (geralmente vem assim: "Authorization: Bearer eyJhbGciOiJIUzI1NiIs")
                                                                                /// retorna o token (é preciso copiar e depois colar no Postman -> Postman faz o papel de Front-End)
        }
        else {
            return null;                                                         /// retorna nulo se nao tiver um token válido
        }

    }
}
