package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity // genero en la base de datos una tabla segun mi clave ID primaria
public class Account {
    @Id // las anotaciones son metaDatos
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native") //genero un valor id automatico. Native = la generacion del id va a estar en mano de la base de datos h2
    @GenericGenerator(name = "native", strategy = "native") // generacion del ID de forma automatica.
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    @OneToMany(mappedBy="account", fetch=FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();
    private String number;
    private Double balance;
    private LocalDateTime creationDate;

    private AccountType accountType;

    private boolean accountState;

    public Account() {}

    public Account(String number, Double balance, LocalDateTime creationDate, Client client, boolean accountState,AccountType accountType) { //CONSTRUCTOR 1, polimorfismo.
        this.number = number;
        this.balance = balance;
        this.creationDate = creationDate;
        this.client = client;
        this.accountState = accountState;
        this.accountType = accountType;
    }

    public Account(String number, Double balance, LocalDateTime creationDate, boolean accountState, AccountType accountType) { //CONSTRUCTOR 2, polimorfismo.
        this.number = number;
        this.balance = balance;
        this.creationDate = creationDate;
        this.accountState = accountState;
        this.accountType = accountType;
    }

    public Long getId() {
        return id;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public Double getBalance() {
        return balance;
    }
    public void setBalance(Double balance) {
        this.balance = balance;
    }
    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Transaction> getTransactions() { return transactions;}


    public boolean isAccountState() {
        return accountState;
    }

    public void setAccountState(boolean accountState) {
        this.accountState = accountState;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
}
