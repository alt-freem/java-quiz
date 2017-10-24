package test.design.atm;

/**
 * Задача реализовать сервис снятия средств через банкомат.
 * Логика работы сервиса подразумевает проверку карты на наличие блокировок
 * Получения счета клиента по номеру карты из БД, проверку возможности снятия (наличия) средств со счета,
 * фиксацию списания средств в БД, подтверждение банкоманту на выдачу запрошенной суммы.
 * Так же необходимо разработать юнит тест.
 */
public interface ATMService {

    void cashWithdrawal(ATM atm, Card card, long amount);

    interface ATM {
        void accept();

        void denny(String reason);
    }

    class Card {
        long num;
    }

    class Account {
        long num;
        long amount;
    }

    interface CardValidator {
        boolean isValid(Card card);
    }

    interface TxManager {
        void start();

        void rollback();

        void commit() throws TxCommitException;
    }

    class TxCommitException extends Exception {

    }

    interface AccountDao {
        Account getAccountByCardNum(Card card);

        void updateAmount(Account account, long newAmmount);
    }
}