package bankFacade;

import account.Account;
import account.AccountEnum;
import customer.Customer;
import customer.RegisterOperation;
import database.Database;
import database.History;

import java.util.*;

/**
 * Created by Ashkan Amiri, Jacaranda Perez, Iryna Gnatenko och Salem Koldzo
 * Date:  2020-12-07
 * Time:  11:24
 * Project: bankSystem
 * Copyright: MIT
 */
public class Facade {

    List<Customer> customerFromDB = new ArrayList<>();
    protected Database dataDB = new Database();
    RegisterOperation registerOperation;

    public Facade() {
        welcomeDialogue();
    }

    public void welcomeDialogue() {
        registerOperation = new RegisterOperation();

        try {
            customerFromDB = dataDB.addDataToCustomerList();
        } catch (Exception e) {
            System.out.println("Could not find file. ");
            e.printStackTrace();
        }

        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to Your Bank! \n Please press your desired option: \n (1)login | (2)register ");
        while (scan.hasNext()) {
            String chosenOption = scan.next();
            if (chosenOption.equals("2")) {
                makeNewCustomer();
            } else if (chosenOption.equals("1")) {
                System.out.println("Please enter your customerID:");
                int inputCustomerID = (int) getInfoFromUser();
                System.out.println("Please enter your pin code:");
                int inputCustomerPinCode = (int) getInfoFromUser();
                if (checkInputInfo(inputCustomerID, inputCustomerPinCode) == null) {
                    System.out.println("Wrong customerID or pincode. Try again");
                    welcomeDialogue();
                } else {
                    Customer c = checkInputInfo(inputCustomerID, inputCustomerPinCode);
                    RegisterOperation.blankspaces();
                    System.out.println("Welcome " + c.getFirstName() + " " + c.getLastName() + "\n");
                    System.out.println("Choose an account to make transactions");
                    System.out.println("1. Savings account");
                    System.out.println("2. Current account");
                    System.out.println("3. Credit account");
                    System.out.println("4. Close session");
                    int choice = (int) getInfoFromUser();
                    if (choice == 1 || choice == 2 || choice == 3) {
                        RegisterOperation.blankspaces();
                        List<Customer> customerListOfAcc = new ArrayList<>();
                        customerListOfAcc = getChosenAccount(inputCustomerID, inputCustomerPinCode, choice);
                        System.out.println(" Pleas enter your selected account number that you want to continue with!");
                        System.out.println(Arrays.toString(c.customerAccountListToString(customerListOfAcc)));
                        long accountNumber = getInfoFromUser();
                        for (int i = 0; i < customerListOfAcc.size(); i++) {
                            if (accountNumber == customerListOfAcc.get(i).getAccount().getAccountNumber()) {
                                c = customerListOfAcc.get(i);
                            }
                        }
                        displayMenu(c);
                    } else if (choice == 4) {
                        System.out.println("Closing session");
                        System.exit(0);
                    } else
                        System.out.println("Invalid option. Try again");
                }

            } else
                System.out.println("Invalid option. Press 1 to login. Press 2 to register as a new customer");
        }
    }

    public void displayMenu(Customer customer) {
        int temp = -1;
        while (temp != 0) {
            System.out.println("Welcome " + customer.getFirstName() + " " + customer.getLastName() + "\n");
            System.out.println("Please choose from the menu");
            System.out.println("1: " + AccountEnum.getAccountType(3).getDescription());
            System.out.println("2: " + AccountEnum.getAccountType(4).getDescription());
            System.out.println("3: " + AccountEnum.getAccountType(5).getDescription());
            System.out.println("4: " + AccountEnum.getAccountType(6).getDescription());
            System.out.println("5: " + AccountEnum.getAccountType(7).getDescription());
            System.out.println("6: " + AccountEnum.getAccountType(8).getDescription());
            System.out.println("7: " + AccountEnum.getAccountType(11).getDescription());
            System.out.println("0: " + AccountEnum.getAccountType(9).getDescription());


            temp = (int) getInfoFromUser();
            double amount;
            switch (temp) {
                case 1:
                    System.out.println("Please introduce the amount you want to deposit");
                    amount = getAmountFromUser();
                    makeDeposit(amount, customer.getAccount(), customer);
                    registerOperation.continueORquit();
                    break;

                case 2:
                    System.out.println("Please introduce the amount you want to withdraw");
                    amount = getAmountFromUser();
                    makeWithdraw(amount, customer.getAccount(), customer);
                    registerOperation.continueORquit();
                    break;

                case 3:
                    System.out.println("Please introduce the amount you want to Transfer ");
                    amount = getAmountFromUser();
                    System.out.println("please enter the account number that you want to send money to");
                    long destinationAccount = (long) getAmountFromUser();
                    Customer c = new Customer();
                    for (int i = 0; i < customerFromDB.size(); i++) {
                        if (destinationAccount == customerFromDB.get(i).getAccount().getAccountNumber()) {
                            c = customerFromDB.get(i);
                        }
                    }
                    System.out.println(" you want transfer to " + c.getFirstName() + " " + c.getLastName() + "\n" +
                            "If this is the one that you approve of pleas press 1 or 0 to ignore");
                    int input = (int) getInfoFromUser();
                    if (input == 0)
                        System.exit(0);
                    String destFullName = c.getFirstName() + " " + c.getLastName();
                    makeTransfer(amount, customer.getAccount(), destinationAccount, destFullName);
                    registerOperation.continueORquit();
                    break;

                case 4:
                    RegisterOperation.blankspaces();
                    System.out.println("Transaction history:");
                    String filePathOut = "resources/CustomersHistory.csv";
                    List<String[]> customersInfoList = Database.readDataFromFile(filePathOut);
                    for (String[] s : customersInfoList) {
                        long accountNumber = Long.parseLong(s[0]);
                        if (accountNumber == customer.getAccount().getAccountNumber()) {
                            System.out.println("Accountnumber: " + s[0] + " | Accounttype:" + s[1] +
                                    " | Operation:" + s[2] + " | Amount:" + s[3] + " | New balance:" + s[4]
                                    + " | Transfer To--->"+ s[5]+" | Datestamp:" + s[6] + "\n");
                        }
                    }
                    registerOperation.continueORquit();
                    break;

                case 5:
                    customer.getAccount().printBalance();
                    registerOperation.continueORquit();
                    break;

                case 6:
                    System.out.println("Do you want change pinCod?\n" +
                            "Please enter your new pin code! ");
                    String newPinCode = String.valueOf(getInfoFromUser());
                    History.replaceSelected(String.valueOf(customer.getCustomerPinCode()), newPinCode);
                    registerOperation.continueORquit();
                    break;
                case 7:
                    System.out.println("Savings calculator\n " +
                                    "With our savings calculator, you can calculate how your savings can grow over time!\n" +
                                    " Fill in any starting amount: ");
                    double amountFromUser = getAmountFromUser();
                    System.out.println("How long(year) you want to save?");
                    int year = (int) getInfoFromUser();
                    customer.getAccount().balanceWithRate(amountFromUser,year);
                    registerOperation.continueORquit();
                    break;

                case 0:
                    System.out.println("Session closed");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    public void makeDeposit(double amount, Account account, Customer customer) {
        account.deposit(amount, customer);
    }

    public void makeWithdraw(double amount, Account account, Customer customer) {
        account.withDraw(amount, customer);
    }

    public void makeTransfer(double amount, Account fromAccount, long toAccount, String name) {
        fromAccount.transfer(amount, toAccount,name);
    }

    public void makeNewCustomer() {
        registerOperation.registerNewCustomer();
    }

    public double getAmountFromUser() {
        double amount = -2;
        while (amount == -2) {
            try {
                Scanner s = new Scanner(System.in);
                amount = s.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Just numbers allowed. Try again");
            }
        }
        return amount;
    }

    public Customer checkInputInfo(int inputCustomerID, int inputCustomerPinCode) {
        customerFromDB = dataDB.addDataToCustomerList();

        if (customerFromDB.size() == 0) {
            System.out.println("Empty list");
        }
        for (int i = 0; i < customerFromDB.size(); i++) {
            if (customerFromDB.get(i).getCustomerPinCode() == inputCustomerPinCode
                    && customerFromDB.get(i).getCustomerId() == inputCustomerID)
                if (customerFromDB.get(i).getAccountEnum().getAccountType() == 1) {
                    Customer c = customerFromDB.get(i);
                    return c;
                } else
                    return null;
        }
        return null;
    }

    public long getInfoFromUser() {
        long input = -2;
        while (input == -2) {
            try {
                Scanner s = new Scanner(System.in);
                input = s.nextLong();
            } catch (InputMismatchException e) {
                System.out.println("Just numbers allowed. Please try again \n");
            }
        }
        return input;
    }

    public List<Customer> getChosenAccount(int inputCustomerID, int inputCustomerPinCode, int choice) {
        List<Customer> oneCustLoList = new ArrayList<>();
        for (int i = 0; i < customerFromDB.size(); i++) {
            if (customerFromDB.get(i).getCustomerPinCode() == inputCustomerPinCode && customerFromDB.get(i).getCustomerId() == inputCustomerID) {
                if (customerFromDB.get(i).getAccountEnum().getAccountType() == 1 && choice == 1) {
                    oneCustLoList.add(customerFromDB.get(i));
                } else if (customerFromDB.get(i).getAccountEnum().getAccountType() == 2 && choice == 2) {
                    oneCustLoList.add(customerFromDB.get(i));
                } else if (customerFromDB.get(i).getAccountEnum().getAccountType() == 10 && choice == 3) {
                    oneCustLoList.add(customerFromDB.get(i));
                }
            }
        }
        return oneCustLoList;
    }
}

