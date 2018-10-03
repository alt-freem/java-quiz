package test.design.kettle;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static org.junit.Assert.*;

public class KettleManagerTest {

    private KettleManager teaHouse;

    @Test
    public void oneTeaPlease() throws InterruptedException {
        Kettle kettle = new Kettle();
        kettle.setMinimumWaterLevel(200);
        kettle.setMaximumWaterLevel(1200);
        teaHouse  = new KettleManager(new Kettle[]{kettle});

        teaHouse.makeSomeTee(2, KettleManager.GREEN_TEE);
        assertEquals(KettleManager.GREEN_TEE, kettle.getCurrentTemperature());
    }

    @Test
    public void twoKettles() throws ExecutionException, InterruptedException {
        Kettle[] kettles = new Kettle[2];
        kettles[0] = new Kettle();
        kettles[0].setMinimumWaterLevel(200);
        kettles[0].setMaximumWaterLevel(1200);
        kettles[1] = new Kettle();
        kettles[1].setMinimumWaterLevel(200);
        kettles[1].setMaximumWaterLevel(1800);
        teaHouse  = new KettleManager(kettles);

        CompletableFuture<? extends Number> firstClient = supplyAsync(() -> {
            try {
                return teaHouse.makeSomeTee(2, KettleManager.GREEN_TEE);
            } catch (InterruptedException e) {
                throw new IllegalStateException("interrupted", e);
            }
        });
        CompletableFuture<? extends Number> secondClient = supplyAsync(() -> {
            try {
                return teaHouse.makeSomeTee(1, KettleManager.BLACK_TEE);
            } catch (InterruptedException e) {
                throw new IllegalStateException("interrupted", e);
            }
        });
        assertEquals(KettleManager.GREEN_TEE, firstClient.get());
        assertEquals(KettleManager.BLACK_TEE, secondClient.get());
    }
}