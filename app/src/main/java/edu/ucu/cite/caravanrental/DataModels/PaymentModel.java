package edu.ucu.cite.caravanrental.DataModels;

public class PaymentModel {
    String id, transaction_no, payment_type, amount, confirmation_date, created_at;

    public PaymentModel(String id, String transaction_no, String payment_type, String amount, String confirmation_date, String created_at) {
        this.id = id;
        this.transaction_no = transaction_no;
        this.payment_type = payment_type;
        this.amount = amount;
        this.confirmation_date = confirmation_date;
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public String getTransaction_no() {
        return transaction_no;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public String getAmount() {
        return amount;
    }

    public String getConfirmation_date() {
        return confirmation_date;
    }

    public String getCreated_at() {
        return created_at;
    }
}
