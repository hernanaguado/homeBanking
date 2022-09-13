package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;

import java.time.LocalDateTime;

public class PdfDTO {

    private LocalDateTime fromDate;

    private LocalDateTime finalDate;

    private String account;

    public PdfDTO() {
    }

    public PdfDTO(LocalDateTime fromDate, LocalDateTime finalDate, String account) {
        this.fromDate = fromDate;
        this.finalDate = finalDate;
        this.account = account;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateTime getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(LocalDateTime finalDate) {
        this.finalDate = finalDate;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
