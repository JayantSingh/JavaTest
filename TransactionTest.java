package com.abc;


import org.junit.Test;


import static org.junit.Assert.assertTrue;


public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(5);
        System.out.println("OK");
        assertTrue(t instanceof Transaction);
    }
}