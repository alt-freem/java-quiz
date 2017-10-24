package test.design.atm;

public class ATMService {
    class Card {
        long num;
    }

    class Account {
        long num;
        long amount;
    }

    interface Auth<T> {
        boolean isAuthorized(T permission);
    }

    interface TxManager {
        void start();

        void rollback();

        void commit();
    }

    interface AccountDao {
        Account getAccountByCardNum(Card card);

        void updateAmount(Account account, int newAmmount);
    }

    private final TxManager tx;
    private final Auth<String> auth;
    private final AccountDao accountDao;

    public ATMService(TxManager tx, Auth<String> auth, AccountDao accountDao) {
        this.tx = tx;
        this.auth = auth;
        this.accountDao = accountDao;
    }

    public void cashWithdrawal(Card card, long amount) {

    }
}