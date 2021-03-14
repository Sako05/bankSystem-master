package account;

import customer.Customer;

import java.util.Scanner;

/**
 * Created by Ashkan Amiri
 * Date:  2020-12-15
 * Time:  17:44
 * Project: bankSystem
 * Copyright: MIT
 */
public class CreditAccount extends Account {

    public CreditAccount(long accountNumber, double balance, String date) {
        super(accountNumber, balance, date);
    }

    public CreditAccount() {
    }

    @Override
    public void setRate() {
    }

    public double requestCreditCard(Customer customer) {
        System.out.println("Enter your amount request!  ");
        Scanner scanner = new Scanner(System.in);
        double balanceReq = scanner.nextDouble();
        if (checkApprovalBalanceReq(balanceReq, customer)) {
            System.out.println("Your request is approved");
            return balanceReq;
        } else {
            System.out.println(" You  haven't enough credit for your request!");
            return 0;
        }
    }

    public boolean checkApprovalBalanceReq(double balanceReq, Customer customer) {

        if (customer.getSalary() >= 50000 && balanceReq <= 100000) {
            return true;
        } else if (customer.getSalary() >= 30000 && balanceReq <= 50000) {
            return true;
        } else return customer.getSalary() >= 10000 && balanceReq <= 20000;
    }
}
