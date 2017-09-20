package test.tx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;

import java.util.concurrent.atomic.AtomicInteger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringTransactions.Config.class)
@Configuration
@EnableTransactionManagement
public class SpringTransactions {

    private final static Logger log = LoggerFactory.getLogger(SpringTransactions.class);

    @Autowired
    private AccountManager accountManager;

    @Test
    public void testDI() {
        accountManager.transfer(null, null, 0);
    }

    static class Account {
        long id;
        long amount;
    }

    static class AccountManager {

        @Transactional(propagation = Propagation.REQUIRED)
        public void transfer(Account from, Account to, long amount) {
            logTransferAttempt(from, to, amount);
            log.info("TRANSFER");
        }

        @Transactional(propagation = Propagation.REQUIRES_NEW)
        public void logTransferAttempt(Account from, Account to, long amount) {
            log.info("LOG");
        }

    }

    static class Config {
        @Bean
        AccountManager accountManager() {
            return new AccountManager();
        }

        @Bean
        PlatformTransactionManager txManager() {
            return new AbstractPlatformTransactionManager() {
                private final Logger log = LoggerFactory.getLogger("TxManager");
                private final AtomicInteger txNum = new AtomicInteger(1);

                @Override
                protected Object doGetTransaction() throws TransactionException {
                    String tx = "Tx" + txNum.getAndIncrement();
                    return tx;
                }

                @Override
                protected void doBegin(Object o, TransactionDefinition transactionDefinition) throws TransactionException {
                    log.info("BEGIN TX: {}", o);
                }

                @Override
                protected void doCommit(DefaultTransactionStatus defaultTransactionStatus) throws TransactionException {
                    log.info("COMMIT TX: {}", defaultTransactionStatus.getTransaction());
                }

                @Override
                protected void doRollback(DefaultTransactionStatus defaultTransactionStatus) throws TransactionException {
                    log.info("ROLLBACK TX: {}", defaultTransactionStatus.getTransaction());
                }
            };
        }
    }
}
