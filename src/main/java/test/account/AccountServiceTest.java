package test.account;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountServiceTest {

    private final AccountService service = new InMemoryAccountService();

    @Test
    public void credit() throws Exception {
        int rubAmount = 100500;
        int usdAmount = 1000;
        Account rub = service.newAccount(Currency.RUB, rubAmount);
        Account usd = service.newAccount(Currency.USD, usdAmount);

        service.credit(rub, usd, 500);
        service.credit(usd, rub, 500);
        assertEquals(rubAmount - 500 + (500 * Currency.USD.rate), service.get(rub.number).amount, 0.001);
        assertEquals(usdAmount + (500 / Currency.USD.rate) - 500, service.get(usd.number).amount, 0.001);
    }

}