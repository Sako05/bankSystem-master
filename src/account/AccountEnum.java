package account;

/**
 * Created by Ashkan Amiri, Jacaranda Perez, Iryna Gnatenko och Salem Koldzo
 * Date:  2020-11-29
 * Time:  12:22
 * Project: bankSystem
 * Copyright: MIT
 */
public enum AccountEnum {

    SAVING_ACCOUNT(1, "Saving account"),
    CURRENT_ACCOUNT(2, "Current account"),
    DEPOSIT(3, "Make a deposit"),
    WITHDRAW(4, "Withdraw"),
    TRANSFER(5, "Make a transfer"),
    HISTORY(6, "Check your transactions' history"),
    CHECK_BALANCE(7, "Check your balance"),
    UPDATE_INFO(8, "Update your personal information"),
    LOGOUT(9, "Log out"),
    CREDIT_ACCOUNT(10, "Credit account"),
    SAVINGS_CALCULATOR(11, "Savings calculator"),


    UNKNOWN(0, "Unknown");


    private final int accountType;
    private final String description;

    AccountEnum(Integer accountType, String description) {
        this.accountType = accountType;
        this.description = description;
    }

    public int getAccountType() {
        return accountType;
    }

    public String getDescription() {
        return description;
    }

    public static AccountEnum getAccountType(int code) {
        AccountEnum[] values = values();
        AccountEnum[] array = values;
        int length = values().length;
        for (int i = 0; i < length; i++) {
            AccountEnum value = array[i];
            if (value.getAccountType() == code)
                return value;
        }
        return AccountEnum.getAccountType(0);
    }
}