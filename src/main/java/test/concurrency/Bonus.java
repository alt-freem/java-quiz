package test.concurrency;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

import static java.util.concurrent.CompletableFuture.allOf;
import static java.util.concurrent.CompletableFuture.runAsync;
import static org.junit.Assert.assertEquals;

public class Bonus {
    private AtomicLong _moneySpend = new AtomicLong();
    private AtomicLong _bonusEarned = new AtomicLong();

    public void earn(long bonuses) {
        _bonusEarned.addAndGet(bonuses);
    }

    public void spent(long money) {
        _moneySpend.addAndGet(money);
        _bonusEarned.addAndGet(money / 200L);
    }

    @Override
    public String toString() {
        return "{money=" + _moneySpend.get() + ", bonus=" + _bonusEarned.get() + "}";
    }

    @Test
    public void clicksAndViews() {
        Bonus advert = new Bonus();
        assertEquals("{money=0, bonus=0}", advert.toString());

        advert.earn(10);
        assertEquals("{money=0, bonus=10}", advert.toString());

        advert.spent(500);
        advert.spent(500);
        assertEquals("{money=1000, bonus=14}", advert.toString());
    }

    @Test
    public void concurrentTest() {
        Bonus bonus = new Bonus();
        allOf(
                runAsync(() -> bonus.earn(1)),
                runAsync(() -> bonus.spent(1500)),
                runAsync(() -> bonus.spent(500)),
                runAsync(() -> bonus.earn(2))
        ).join();
        assertEquals("{money=2000, bonus=12}", bonus.toString());
    }

    @Test
    public void consistencyTest() {
        Bonus bonus = new Bonus();
        Runnable spentSomeMoney = () -> IntStream.range(0, 100000).forEach(i -> bonus.spent(400));
        Runnable spentMoreMoney = () -> IntStream.range(0, 100000).forEach(i -> bonus.spent(1200));
        Runnable checkTask = () -> IntStream.range(0, 10000).forEach(i -> {
            String json = bonus.toString();
            int eq1 = json.indexOf('=') + 1;
            int split = json.indexOf(',', eq1);
            int eq2 = json.indexOf('=', split) + 1;
            long moneySpend = Long.parseLong(json.substring(eq1, split));
            long bonusEarned = Long.parseLong(json.substring(eq2, json.indexOf('}', eq2)));
            assertEquals("Inconsistency detected: " + json, moneySpend / 200, bonusEarned);
        });

        allOf(
                runAsync(spentSomeMoney),
                runAsync(spentMoreMoney),
                runAsync(checkTask)
        ).join();
    }
}
