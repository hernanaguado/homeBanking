package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface LoanRepository extends JpaRepository<Loan, Long> { //long es la clave primaria para qe no se te pise en la base de datos de tipo CLient.

    Loan findById(long id);

    Loan findByName(String name);

}
