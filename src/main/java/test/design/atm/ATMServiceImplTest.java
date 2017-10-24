package test.design.atm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import test.design.atm.ATMService.ATM;
import test.design.atm.ATMService.AccountDao;
import test.design.atm.ATMService.CardValidator;
import test.design.atm.ATMService.TxManager;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class ATMServiceImplTest {
    @Mock
    CardValidator validator;

    @Mock
    TxManager tx;

    @Mock
    AccountDao accountDao;

    @Mock
    ATM atm;

    @InjectMocks
    ATMServiceImpl atmService;

    @Test
    public void test() {
        assertNotNull(atmService);
    }
}