package br.com.cursoDankiCode.Pizzaria.usuario;


import br.com.cursoDankiCode.Pizzaria.Config.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {


    private final AuthenticationManager autenticador;

    private final TokenService tokenService;

    public LoginController(AuthenticationManager autenticador, TokenService tokenService) {
        this.autenticador = autenticador;
        this.tokenService = tokenService;
    }

    @PostMapping
    /// método de login - coloque as informações do user - copie o token - use o token nas requisições
    public ResponseEntity validacaoCredenciaisUsuario(@RequestBody @Valid CredenciaisUsuarioDTO credenciais){                                   ///  recebe login e password
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credenciais.getLogin(), credenciais.getPassword()); ///  cria um token para autenticação

        Authentication autenticacao = autenticador.authenticate(token);                                                                         /// faz validação e autenticação do token criado

        return ResponseEntity.ok(tokenService.createToken((Usuario) autenticacao.getPrincipal()));                                              /// chama a função de criar token JWT para as requisições do user (token diferente do token anterior)
    }


}
