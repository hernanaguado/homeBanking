package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;
import java.util.List;




@SpringBootApplication
public class HomeBankingApplication {

	public static void main(String[] args) {SpringApplication.run(HomeBankingApplication.class, args);} //metodo para inicializar la app.
	@Bean // pasa los repositoris a la base de datos
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return (args) -> {

			Client client1 =  new Client("Melba","Morel","melba@gmail.com", passwordEncoder.encode("123")); // lo pongo en una variable para poder relacionar luego en la cuenta
			Client client2 =  new Client("ADMIN","ADMIN","hernanAguado@admin123.com",passwordEncoder.encode("123")); // lo pongo en una variable para poder relacionar luego en la cuenta

			Account cuenta1 = new Account("VIN001", 5000.0,LocalDateTime.now(),client1,true,AccountType.CURRENT); //constructor 1
			Account cuenta2 = new Account("VIN002", 7500.0,LocalDateTime.now().plusDays(1),true,AccountType.CURRENT); //constructor 2

			Transaction transaction1 = new Transaction(cuenta1,TransactionType.DEBIT,-5000.00,"alquiler",LocalDateTime.now());
			Transaction transaction2 = new Transaction(cuenta2,TransactionType.CREDIT,1000.00,"comida fin de año",LocalDateTime.now());

			Loan Hipotecario = new Loan ("Mortgage",500000.00, List.of(12,24,36,48,60));
			Loan Personal = new Loan ("Personal",100000.00, List.of(6, 12, 24));
			Loan Automotriz = new Loan ("Car",300000.00, List.of(6,12,24,36));

			ClientLoan loan1 = new ClientLoan(400000.00,60,client1,Hipotecario);
			ClientLoan loan2 = new ClientLoan(50000.00,12,client1,Personal);
			ClientLoan loan3 = new ClientLoan(100000.00,24,client2,Personal);
			ClientLoan loan4 = new ClientLoan(200000.00,36,client2,Automotriz);

			Card card1 = new Card (client1, (client1.getFirstName() + " " + client1.getLastName()), "8978 4578 9685 6258", 356, LocalDateTime.now(), LocalDateTime.now(),CardColor.GOLD, CardType.DEBIT,true);
			Card card2 = new Card (client1, (client1.getFirstName() + " " + client1.getLastName()), "1381 6731 6774 1489", 420, LocalDateTime.now(), LocalDateTime.now().plusYears(5),CardColor.TITANIUM, CardType.CREDIT, true);
			Card card3 = new Card (client2, (client2.getFirstName() + " " + client2.getLastName()), "3547 5478 6538 2796", 420, LocalDateTime.now(), LocalDateTime.now().plusYears(5),CardColor.SILVER, CardType.CREDIT,true);

			client1.addAccount(cuenta2); //asociando usando el metodo addAccount


			clientRepository.save(client1);
			clientRepository.save(client2);

			accountRepository.save(cuenta1);
			accountRepository.save(cuenta2);

			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);

			loanRepository.save(Hipotecario);
			loanRepository.save(Personal);
			loanRepository.save(Automotriz);

			clientLoanRepository.save(loan1);
			clientLoanRepository.save(loan2);
			clientLoanRepository.save(loan3);
			clientLoanRepository.save(loan4);

			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);

		};
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
}
