package com.example.expensetrackerapp.repository;

public class ReceiptEntity {
    private int id;
    private String invoiceId;
    private int companyId;
    private int totalAmount;
    private String category;
    private String lastUpdatedTime;

    public ReceiptEntity(String invoiceId, int companyId, int totalAmount, String category, String lastUpdatedTime) {
        this.invoiceId = invoiceId;
        this.companyId = companyId;
        this.totalAmount = totalAmount;
        this.category = category;
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public String getCategory() {
        return category;
    }

    public String getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    @Override
    public String toString() {
        return "ReceiptEntity{" +
                "id=" + id +
                ", invoiceId='" + invoiceId + '\'' +
                ", companyId=" + companyId +
                ", totalAmount=" + totalAmount +
                ", category='" + category + '\'' +
                ", lastUpdatedTime='" + lastUpdatedTime + '\'' +
                '}';
    }
}
