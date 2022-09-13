package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Client;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.jpa.repository.JpaRepository;


@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client, Long> { //long es la clave primaria para qe no se te pise en la base de datos de tipo CLient.

    Client findByEmail(String email);

}



