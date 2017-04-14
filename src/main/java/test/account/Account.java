package test.account;

class Account {
    final long number;
    double amount;
    final Currency currency;

    public Account(long number, Currency currency) {
        this.number = number;
        this.currency = currency;
    }
}
