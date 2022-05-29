package com.example.expensetrackerapp.repository;

public class CompanyEntity {
    private String companyName;
    private String companyAddress;
    private String companyDescription;
    private String outlet;
    private String lastUpdatedTime;

    public CompanyEntity(String companyName, String companyAddress, String companyDescription, String outlet, String lastUpdatedTime) {
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.companyDescription = companyDescription;
        this.outlet = outlet;
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public String getOutlet() {
        return outlet;
    }

    public String getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    @Override
    public String toString() {
        return "CompanyEntity{" +
                "companyName='" + companyName + '\'' +
                ", companyAddress='" + companyAddress + '\'' +
                ", companyDescription='" + companyDescription + '\'' +
                ", outlet='" + outlet + '\'' +
                ", lastUpdatedTime='" + lastUpdatedTime + '\'' +
                '}';
    }
}
