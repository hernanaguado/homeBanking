package com.mindhub.homebanking.dtos;


import com.mindhub.homebanking.models.ClientLoan;


public class ClientLoanDTO {
    private long id;
    private long id_loan;
    private double amount;
    private Integer payments;
    private String name;

    public ClientLoanDTO () { }

    public ClientLoanDTO (ClientLoan clientLoan) {
        this.id= clientLoan.getId();
        this.id_loan = clientLoan.getLoan().getId();
        this.payments = clientLoan.getPayments();
        this.name = clientLoan.getLoan().getName();
        this.amount = clientLoan.getAmount();
    }

    public long getId() {
        return id;
    }

    public long getId_loan() {
        return id_loan;
    }

    public Integer getPayments() {
        return payments;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {return amount;}
}
