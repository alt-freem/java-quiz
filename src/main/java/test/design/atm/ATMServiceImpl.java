package test.design.atm;

public class ATMServiceImpl implements ATMService {
    private final TxManager tx;
    private final CardValidator validator;
    private final AccountDao accountDao;

    public ATMServiceImpl(TxManager tx, CardValidator validator, AccountDao accountDao) {
        this.tx = tx;
        this.validator = validator;
        this.accountDao = accountDao;
    }

    @Override
    public void cashWithdrawal(ATM atm, Card card, long amount) {

    }
}
