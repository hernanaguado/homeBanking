package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AccountRepository  extends JpaRepository<Account, Long> { //va a trabajar con un todos los objetos de tipo Account y la clave primaria id

    Account findByNumber(String number);

}
