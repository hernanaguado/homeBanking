package com.mindhub.homebanking.repositories.configuration;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User; // buscamos un usuario por el nombre en nuestro repositorio y, si se encuentra, creamos y devolvemos un objeto. con el nombre de usuario almacenado, la contraseña almacenada para ese usuario y el rol o roles que tiene el usuario.
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration //le indica a spring que debe crear un objeto de este tipo cuando se está iniciando la aplicación, para que cuando se configure el módulo de spring utilice ese objeto ya creado.
public class WebAuthentication extends GlobalAuthenticationConfigurerAdapter { //La clase WebAuthentication se extiende de GlobalAuthenticationConfigurerAdapter para poder sobrescribir el método su método init.

    @Autowired //Generamos una instancia del repositorio.(inyeccion de dependencia)
    ClientRepository clientRepository;



    @Override // indica qe se va a sobre escribir
    public void init(AuthenticationManagerBuilder auth) throws Exception {                          //GlobalAuthenticationConfigurerAdapter  es el objeto que utiliza Spring Security para saber cómo buscará los detalles del usuario.

        auth.userDetailsService(inputName-> {

            Client client = clientRepository.findByEmail(inputName);                              // busca dentro del repositio la coincidencia  por email, pasando por parametro de busqueda "inputName".

            if (client != null) {                                                                   // si la busqueda es dif a null quiere decir que matcho con algo

                if (client.getEmail().contains("admin123")){
                    return new User(client.getEmail(), client.getPassword(),
                            AuthorityUtils.createAuthorityList("ADMIN"));               // AUTORIZACION GENERADA O ROL GENERADO EN ESTE CASO LLAMADO "CLIENT".
                } else {
                return new User(client.getEmail(), client.getPassword(),                           // retorna un usuario a travez de session id.
                        AuthorityUtils.createAuthorityList("CLIENT"));                   // AUTORIZACION GENERADA O ROL GENERADO EN ESTE CASO LLAMADO "CLIENT".
                }
            } else {
                throw new UsernameNotFoundException("Unknown user: " + inputName);                 // quiere decir que no matcho con nada entonces arroja un cartel.
            }

        });

    }
    @Bean                                                                                           // genera un objeto de tipo PasswordEncoder en el ApplicationContext para que luego se pueda usar en cualquier parte de la aplicación que se requiera.
    public PasswordEncoder passwordEncoder() {                                                       // Los clientes creados tienen almacenada la contraseña en texto plano, es decir, texto sin cifrar. Para solucionarlo primero se debe crear un PasswordEncoder

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();

    }

}