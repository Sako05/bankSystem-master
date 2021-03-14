package database;

import account.AccountEnum;
import account.CreditAccount;
import account.CurrentAccount;
import account.SavingAccount;
import customer.Customer;

import java.io.*;
import java.util.*;

/**
 * Created by Ashkan Amiri, Jacaranda Perez, Iryna Gnatenko och Salem Koldzo
 * Date:  2020-11-29
 * Time:  12:21
 * Project: bankSystem
 * Copyright: MIT
 */
public class Database {




    public static void AddDataToFile(String filePathOut, String textToAppend) {
        try (FileWriter fileWriter = new FileWriter(filePathOut, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.println(textToAppend);  //New line
        } catch (FileNotFoundException e) {
            System.out.println("File could not be found");
        } catch (IOException e) {
            System.out.println("Could not write to file");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("File error");
        }
    }

    public static List<String[]> readDataFromFile(String file) {
        String dataRow;
        List<String[]> data = new LinkedList<>();
        try (BufferedReader inputReader = new BufferedReader((new FileReader(file)))) {
            while ((dataRow = inputReader.readLine()) != null) {
                String[] dataRecords = dataRow.split(";");
                data.add(dataRecords);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not find file");
        } catch (IOException e) {
            System.out.println("could not read file");
        }
        return data;
    }

    public List<Customer> addDataToCustomerList(){
        String csVFile = "resources/CustomerList.csv";
        List<String[]> newAccountHolders = readDataFromFile(csVFile);
        List<Customer> customerList = new ArrayList<>();
        for (String[] accountHolder : newAccountHolders) {
            Customer customer = new Customer();
            int customerID = Integer.parseInt(accountHolder[0]);
            customer.setCustomerId(customerID);
            short customerPinCode = Short.parseShort(accountHolder[1]);
            customer.setCustomerPinCode(customerPinCode);
            String firstName = accountHolder[2];
            customer.setFirstName(firstName);
            String lastName = accountHolder[3];
            customer.setLastName(lastName);
            int accountNumber = Integer.parseInt(accountHolder[4]);
            double balance = Double.parseDouble(accountHolder[5]);
            double salary = Double.parseDouble(accountHolder[6]);
            customer.setSalary(salary);
            String accountType = accountHolder[7];
            String date = accountHolder[8];

            if (accountType.equals("Saving account")) {
                customer.setAccountType(AccountEnum.getAccountType(1));
                customer.setAccount(new SavingAccount(accountNumber, balance,date));

            } else if (accountType.equals("Current account")) {
                customer.setAccountType(AccountEnum.getAccountType(2));
                customer.setAccount(new CurrentAccount(accountNumber, balance, date));
            }else if (accountType.equals("Credit account")) {
                    customer.setAccountType(AccountEnum.getAccountType(10));
                    customer.setAccount(new CreditAccount(accountNumber, balance,date));

                } else {
                System.out.println(" Error AccountType");
            }
            customerList.add(customer);
        }
        return customerList;
    }
}
