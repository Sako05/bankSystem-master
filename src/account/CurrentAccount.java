package account;

/**
 * Created by Ashkan Amiri, Jacaranda Perez, Iryna Gnatenko och Salem Koldzo
 * Date:  2020-11-29
 * Time:  12:21
 * Project: bankSystem
 * Copyright: MIT
 */
public class CurrentAccount extends Account implements IInterestRate {

    public CurrentAccount(long accountNumber, double balance, String date) {
        super(accountNumber, balance, date);
        setRate();
    }

    @Override
    public void setRate() {
        rate = (getBaseRate() - 1) / 100;
    }

    @Override
    public void calculateInterestRate() {
    }
}