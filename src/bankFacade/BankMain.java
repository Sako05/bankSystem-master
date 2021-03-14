package bankFacade;

import account.SavingAccount;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ashkan Amiri, Jacaranda Perez, Iryna Gnatenko och Salem Koldzo
 * Date:  2020-11-29
 * Time:  12:20
 * Project: bankSystem
 * Copyright: MIT
 */
public class BankMain {

    public static void main(String[] args) {
        Facade facade = new Facade();
    }

    // TODO: 2020-12-17  timerMethod to change data base for saving account
    public void timerMethod() {
        SavingAccount acc = new SavingAccount();
        Timer timer = new Timer();
        System.out.println("Your daily profit schedule is running");
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                acc.calculateInterestRate();
                System.out.println("profit :" + acc.getBalance());
                System.exit(0);
            }
        }, 0, acc.myLong);
    }

}