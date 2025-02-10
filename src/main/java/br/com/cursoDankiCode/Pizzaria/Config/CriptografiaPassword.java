package br.com.cursoDankiCode.Pizzaria.Config;



import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class CriptografiaPassword {             /// colocando a criptografia de senha na API

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String criptografia(String password){

        return encoder.encode(password);

    }


}
