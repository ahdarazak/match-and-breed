package com.heroku.java.Model;

public class CustomerModel {
    private int customerID;
    private String customerName;
    private String customerEmail;
    private String customerPassword;
    private String customerPhone;
    private String customerUsername;

    public CustomerModel() {
    }

    public CustomerModel(int customerID, String customerName, String customerEmail, String customerPassword,
            String customerPhone, String customerUsername) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPassword = customerPassword;
        this.customerPhone = customerPhone;
        this.customerUsername = customerUsername;
    }

    public CustomerModel(String customerPassword, String customerUsername) {
        this.customerPassword = customerPassword;
        this.customerUsername = customerUsername;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    } 

    
}

