package com.mindhub.homebanking.dtos;


import com.mindhub.homebanking.models.Loan;




public class LoanAplicationDTO {

    private long id;

    private double amount;

    private Integer payments;

    private String accountDestination;

    public LoanAplicationDTO() {
    }

    public LoanAplicationDTO(Loan loan, double amount, Integer payments, String accountDestination) {
        this.id = loan.getId();
        this.amount = amount;
        this.payments = payments;
        this.accountDestination = accountDestination;
    }

    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public Integer getPayments() {return payments;}

    public String getAccountDestination() {return accountDestination;}

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public void setAccountDestination(String accountDestination) {
        this.accountDestination = accountDestination;
    }
}
