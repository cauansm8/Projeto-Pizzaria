package br.com.cursoDankiCode.Pizzaria.usuario;


import br.com.cursoDankiCode.Pizzaria.Config.CriptografiaPassword;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;



    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;

    }


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findByLogin(login);
    }

    public DadosUser criarUser(DadosUser dto){
        Usuario user = modelMapper.map(dto, Usuario.class);

        String criptPassword = CriptografiaPassword.criptografia(user.getPassword());

        user.setPassword(criptPassword);

        userRepository.save(user);
        return modelMapper.map(user, DadosUser.class);
    }

    public ReturnLogin getUser(@NotNull Long id) {

        Usuario user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        return modelMapper.map (user, ReturnLogin.class);

    }
}
