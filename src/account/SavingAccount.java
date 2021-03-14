package account;

/**
 * Created by Ashkan Amiri, Jacaranda Perez, Iryna Gnatenko och Salem Koldzo
 * Date:  2020-11-29
 * Time:  12:21
 * Project: bankSystem
 * Copyright: MIT
 */
public class SavingAccount extends Account implements IInterestRate {
    public long myLong = 1234;

    public SavingAccount() {
    }

    public SavingAccount(long accountNumber, double balance, String date) {
        super(accountNumber, balance, date);
        setRate();
    }

    public void setRate() {
        rate = (getBaseRate() + .5) / 100;
    }

    public void calculateInterestRate() {
        rate = (balance * getBaseRate() / 100 / 365);
        balance = balance + (balance * getBaseRate() / 100 / 365);
    }
}
