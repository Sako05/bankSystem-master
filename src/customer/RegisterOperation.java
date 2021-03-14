package customer;

import account.AccountEnum;
import account.CreditAccount;
import account.CurrentAccount;
import account.SavingAccount;
import bankFacade.Facade;
import database.Database;
import database.History;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RegisterOperation {

    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    String filePathOut = "resources/CustomerList.csv";
    protected List<Customer> customerFromDB = new ArrayList<>();
    protected Database dataDB = new Database();
    Facade bankFacade;

    public static void blankspaces() {
        for (int clear = 0; clear < 5; clear++) {
            System.out.println("\b");
        }
    }

    //a method to generate a random number (ID, PIN, account number)
    public int generateRandomNumber(int upperbound, int lowerbound) {
        Random rand = new Random();
        return rand.nextInt(upperbound - lowerbound) + lowerbound;
    }

    public Customer getCurrentRow(String name, String lastName) {
        Customer c = new Customer();
        for (int i = 0; i < customerFromDB.size(); i++) {
            if (customerFromDB.get(i).getFirstName().equalsIgnoreCase(name) && customerFromDB.get(i).getLastName().equalsIgnoreCase(lastName)) {
                c = customerFromDB.get(i);
            }
        }
        return c;
    }

    public boolean findByName(String name, String lastName) {
        boolean customer = false;
        for (Customer c : customerFromDB) {
            if (c.getFirstName().equalsIgnoreCase(name) && c.getLastName().equalsIgnoreCase(lastName)) {
                System.out.println("Welcome " + name +" "+ lastName + " "+ "\nYou are an existing customer.");
                customer = true;
                break;

            } else customer = false;

        }
        return customer;
    }

    public void welcomeMenuORquit() {
        System.out.println("What would you like to do next?\nPress (1) for Main menu or press (2) for EXIT");
        Scanner s = new Scanner(System.in);
        int customerchoise = s.nextInt();
        if (customerchoise == 1) {
            bankFacade = new Facade();
            bankFacade.welcomeDialogue();
        } else if (customerchoise == 2) {
            System.out.println("Thanks for choosing JavaBank. Good Bye!");
            System.exit(0);
        } else {
            System.out.println("Invalid choice\n\n");
        }
    }

    public void continueORquit() {
        System.out.println("What would you like to do next?\nPress (1) for Main menu or press (2) for EXIT");
        Scanner s = new Scanner(System.in);
        int customerchoise = s.nextInt();
        if (customerchoise == 1) {
            blankspaces();
        } else if (customerchoise == 2) {
            System.out.println("Thanks for choosing JavaBank. Good Bye!");
            System.exit(0);
        } else {
            System.out.println("Invalid choice\n\n");
        }
    }


    public void registerNewCustomer() {
        customerFromDB = dataDB.addDataToCustomerList();
        try {
            blankspaces();
            Scanner input = new Scanner(System.in);
            System.out.println("Welcome to JavaBANK!");
            System.out.println("!!Christmas Offer!!");
            System.out.println("Register now to recive 1-year 0% No-Fee checking account and saving account");
            System.out.println("\n ------------------------- \n");
            System.out.println("Please enter your First name: ");
            String name = input.nextLine().trim();
            System.out.println("Please enter your Last name: ");
            String lastName = input.nextLine().trim();


            System.out.println("\nWaiting for a bank worker to connect..... \n");
            try {
                Thread.sleep(4000);
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println("--------------------------------\n");
            System.out.println("You have been assigned an bank officer and will now verify your application. ");
            System.out.println("Please wait....\n ");

            try {
                Thread.sleep(4000);
            } catch (Exception e) {
                System.out.println(e);
            }


            Customer newCustomer = new Customer();

            newCustomer.setFirstName(name);
            newCustomer.setLastName(lastName);
            if (findByName(name, lastName)) {
                newCustomer=getCurrentRow(name,lastName);
                newCustomer.setCustomerId(newCustomer.getCustomerId());
                newCustomer.setSalary(newCustomer.getSalary());
                newCustomer.setCustomerPinCode(newCustomer.getCustomerPinCode());
            } else {
                newCustomer.setCustomerId(customerFromDB.size() + 1);
                System.out.println("Please enter your salary: ");
                double salary = Double.parseDouble(input.nextLine().trim());
                newCustomer.setSalary(salary);
                newCustomer.setCustomerPinCode((short) generateRandomNumber(9000, 1000));
            }

            System.out.println("Choose the account type you want to creat!");
            System.out.println("1- Saving");
            System.out.println("2- current");
            System.out.println("3- Credit");
            int choice = input.nextInt();
            System.out.println("--------------------------------\n");
            System.out.println("Your application has been" + ANSI_GREEN + " approved!\n" + ANSI_RESET);
            System.out.println("You will now be redirected to your account information\n");
            System.out.println("Please wait....");

            try {
                Thread.sleep(4000);
            } catch (Exception e) {
                System.out.println(e);
            }

            if (choice == 1) {
                newCustomer.setAccount(new SavingAccount((generateRandomNumber(17400000, 17300000)),
                        (generateRandomNumber(90, 10)), History.getDateNowFormat()));
                newCustomer.setAccountType(AccountEnum.SAVING_ACCOUNT);
                String newCustomerToFile = newCustomer.toStringCustomerList(newCustomer.getAccountEnum().getAccountType());
                Database.AddDataToFile(filePathOut, newCustomerToFile);
                customerFromDB.add(newCustomer);
                System.out.println();
                System.out.println(newCustomer.customerRegisterInfoShowToUser());
            } else if (choice == 2) {

                newCustomer.setAccount(new CurrentAccount((generateRandomNumber(27400000, 27300000)),
                        (generateRandomNumber(90, 10)), History.getDateNowFormat()));
                newCustomer.setAccountType(AccountEnum.CURRENT_ACCOUNT);
                String newCustomerToFile1 = newCustomer.toStringCustomerList(newCustomer.getAccountEnum().getAccountType());
                Database.AddDataToFile(filePathOut, newCustomerToFile1);
                customerFromDB.add(newCustomer);
                System.out.println(newCustomer.customerRegisterInfoShowToUser());
                System.out.println();
            } else if (choice == 3) {
                CreditAccount creditAccount = new CreditAccount();
                double balanceCredit = creditAccount.requestCreditCard(newCustomer);
                if (balanceCredit == 0) {
                    System.out.println("Sorry, your request is rejected");
                    System.exit(0);
                }
                newCustomer.setAccount(new CreditAccount((generateRandomNumber(37400000, 37300000)),
                        balanceCredit, History.getDateNowFormat()));
                newCustomer.setAccountType(AccountEnum.CREDIT_ACCOUNT);
                String newCustomerToFile1 = newCustomer.toStringCustomerList(newCustomer.getAccountEnum().getAccountType());
                Database.AddDataToFile(filePathOut, newCustomerToFile1);
                customerFromDB.add(newCustomer);
                System.out.println(newCustomer.customerRegisterInfoShowToUser());
                System.out.println();
            }
            welcomeMenuORquit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
