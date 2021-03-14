package customer;

import account.Account;
import account.AccountEnum;
import database.History;

import java.util.List;

/**
 * Created by Ashkan Amiri, Jacaranda Perez, Iryna Gnatenko och Salem Koldzo
 * Date:  2020-11-29
 * Time:  12:20
 * Project: bankSystem
 * Copyright: MIT
 */


public class Customer {
    private int customerId;
    private String firstName;
    private String lastName;
    private Account account;
    private short customerPinCode;
    private AccountEnum accountEnum;
    private double salary;


    public Customer(int customerId, String firstName, String lastName, Account account,
                    short customerPinCode, AccountEnum accountEnum) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.account = account;
        this.customerPinCode = customerPinCode;
        this.accountEnum = accountEnum;
    }

    public Customer() {
    }

    public AccountEnum getAccountEnum() {
        return accountEnum;
    }

    public void setAccountType(AccountEnum accountEnum) {
        this.accountEnum = accountEnum;
    }


    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public short getCustomerPinCode() {
        return customerPinCode;
    }

    public void setCustomerPinCode(short customerPinCode) {
        this.customerPinCode = customerPinCode;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", account=" + account +
                ", customerPinCode=" + customerPinCode +
                ", accountType=" + accountEnum +
                salary +
                '}';
    }

    public String customerRegisterInfoShowToUser() {
        return "Welcome " + firstName + ' ' + lastName +
                "\nYour customerID: " + customerId +
                "\nYour pin code: " + customerPinCode + "\n" +
                "\nYour " + accountEnum.getDescription() + "s number is ---> " + account.getAccountNumber() +
                "\n ------------------------------------" + "\n";

    }

    public String historyToString(int typeOfTransaction, int typeOfAccount, double amount , String desName) {
        return account.getAccountNumber() + "; " +
                AccountEnum.getAccountType(typeOfAccount).getDescription() + "; " +
                AccountEnum.getAccountType(typeOfTransaction).getDescription() + "; " +
                amount + "; " +
                account.getBalance() + "; " + desName + ";"+
                History.getDateNowFormat();
    }

    public String toStringCustomerList(int typeOfAccount) {
        return customerId + ";" + customerPinCode + ";" + firstName + ";" +
                lastName + ";" + getAccount().getAccountNumber() + ";" + account.getBalance() +
                ";" + salary + ";" + AccountEnum.getAccountType(typeOfAccount).getDescription() + ";" +
                History.getDateNowFormat();
    }

    public String[] customerAccountListToString(List<Customer> customerList) {
        String[] s = new String[customerList.size()];
        for (int i = 0; i < customerList.size(); i++) {
            s[i] = customerList.get(i).getAccount().toString();
        }
        return s;
    }
}
