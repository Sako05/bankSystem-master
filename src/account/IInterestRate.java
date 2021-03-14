package account;

import customer.Customer;

/**
 * Created by Ashkan Amiri
 * Date:  2020-12-16
 * Time:  08:44
 * Project: bankSystem
 * Copyright: MIT
 */
public interface IInterestRate {
     default double getBaseRate(){
      return 2.5;
     }
     void calculateInterestRate();
     void balanceWithRate(double amount ,int year);
     }
