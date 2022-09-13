package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native") //genero un valor id automatico y nativo de mi base de datos
    @GenericGenerator(name = "native", strategy = "native") // generacion del ID de forma automatica.
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;
    private TransactionType type;
    private Double amount;
    private String description;
    private LocalDateTime date;

    public Transaction () {}

    public Transaction (Account account,TransactionType type, Double amount, String description, LocalDateTime date){
        this.account = account;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
