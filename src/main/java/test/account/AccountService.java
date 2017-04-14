package test.account;

interface AccountService {
    void credit(Account from, Account to, double amount);

    Account newAccount(Currency currency, double amount);

    Account get(long accNumber);
}
