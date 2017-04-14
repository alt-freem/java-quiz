package test.account;


public class InMemoryAccountService implements AccountService {
    @Override
    public void credit(Account from, Account to, double amount) {

    }

    @Override
    public Account newAccount(Currency currency, double amount) {
        return null;
    }

    @Override
    public Account get(long accNumber) {
        return null;
    }
}
