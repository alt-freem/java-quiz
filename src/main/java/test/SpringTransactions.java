package test;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract class SpringTransactions {

    class User {
    }

    class Account {
        User owner;
        long id;
        long amount;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void transfer(Account from, Account to, long amount) {
        logTransferAttempt(from, to, amount);
        //....
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public abstract void logTransferAttempt(Account from, Account to, long amount);

}
