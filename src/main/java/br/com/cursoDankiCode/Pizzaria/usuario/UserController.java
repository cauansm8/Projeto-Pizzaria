package br.com.cursoDankiCode.Pizzaria.usuario;



import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController

@RequestMapping ("/user")

@SecurityRequirement(name = "bearer-key")

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<DadosUser> cadastrarUser(@RequestBody @Valid DadosUser dto, UriComponentsBuilder uriBuilder){
        DadosUser userDTO = userService.criarUser(dto);
        URI endereco = uriBuilder.path("/user/{id}").buildAndExpand(userDTO.getId()).toUri();
        return ResponseEntity.created(endereco).body(userDTO);

    }

    @GetMapping ("/{id}")
    public ResponseEntity<ReturnLogin> getUser(@PathVariable @NotNull Long id){

        ReturnLogin returnLogin = userService.getUser(id);

        return ResponseEntity.ok(returnLogin);
    }
}
