package database;

import customer.Customer;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Ashkan Amiri , Jacaranda Perez, Iryna Gnatenko och Salem Koldzo
 * Date:  2020-11-29
 * Time:  12:21
 * Project: bankSystem
 * Copyright: MIT
 */
public class History {

    public static String getDateNowFormat() {
        LocalDateTime DateNow = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return DateNow.format(formatter);
    }

    public static void replaceSelected(String target, String replacement) {
        try {
            BufferedReader file = new BufferedReader(new FileReader("resources/CustomerList.csv"));
            StringBuffer inputBuffer = new StringBuffer();
            String line;

            while ((line = file.readLine()) != null) {
                inputBuffer.append(line);
                inputBuffer.append('\n');
            }
            file.close();
            String inputStr = inputBuffer.toString();

                inputStr = inputStr.replace(target, replacement);

            FileOutputStream fileOut = new FileOutputStream("resources/CustomerList.csv");
            fileOut.write(inputStr.getBytes());
            fileOut.close();

        } catch (Exception e) {
            System.out.println("Problem reading file.");
        }
    }

    public static void historyLog(Customer customer, double amount , int action, int action1 , String desFullName) {
        String filePathOut = "resources/CustomersHistory.csv";
        String textToAppend = customer.historyToString(action, action1,amount, desFullName);
        Database.AddDataToFile(filePathOut, textToAppend);
    }
}
