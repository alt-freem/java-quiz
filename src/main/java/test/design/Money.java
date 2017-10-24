package test.design;

/**
 * Задача спроектировать класс для работы с деньгами в определенной валюте.
 * Класс должен поддерживать операции сложения и вычитания с учетом разной валюты: 3 USD + 5 EUR - 25 RUB
 * Желательно предусмотреть возможность расчета суммарного значения множества денег.
 */
public class Money {
    enum Currency {RUB, USD, EUR}

    interface Rates {
        double rate(Currency from, Currency to);
    }

    public final double amount;
    public final Currency currency;

    public Money(double amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

}
