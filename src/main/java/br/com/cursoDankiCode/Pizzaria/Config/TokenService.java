package br.com.cursoDankiCode.Pizzaria.Config;


import br.com.cursoDankiCode.Pizzaria.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class TokenService {

    public String createToken (Usuario user){                                                   /// cria um token de segurança para o User recebido no login
        try {
            Algorithm algorithm = Algorithm.HMAC256("1234");                             /// algoritmo de assinatura e validação de tokens JWT do tipo HMAC256  (pesquise: JWT.io)
                                                                                                    ///     + senha secreta (o recomendado é esconder esta senha nas variáveis de ambiente)
            LocalDateTime dataExpiracao = LocalDateTime.now().plusHours(2);                     /// tempo limite de uso das requisições (2 horas a partir de logado)

            return JWT.create().                                                                ///  cria um token adicionando algumas informações
                    withIssuer("DankiCode Pizzas")                                              ///  entidade responsável pelo token
                    .withSubject(user.getLogin())                                               ///  nome do usuario -> armazena o login do user dentro do token
                    .withExpiresAt(dataExpiracao.toInstant(ZoneOffset.of("-03:00")))    ///  fuso-horário (pensando em tempo limite de uso da requisição)
                    .sign(algorithm);                                                           /// assinatura do tipo de token
        }

        catch (JWTCreationException e){                                                         /// mensagem de erro
            throw new RuntimeException("Erro ao criar token", e);
        }

    }

    public String buscaLoginToken(String token){                                                /// verifica se existe um user com esse token!!
        try {
            Algorithm algoritmo = Algorithm.HMAC256("1234");                             /// algoritmo de assinatura e validação de tokens JWT

            return JWT.require(algoritmo)                                                       /// configura o verificador do token
                    .withIssuer("DankiCode Pizzas")                                             /// mesmo responsável
                    .build()
                    .verify(token)                                                              /// verifica o token
                    .getSubject();                                                              /// retorna o login do user

        } catch (JWTVerificationException ex){                                                  /// mensagem de erro
            throw new RuntimeException("Token incorreto");
        }
    }


}
