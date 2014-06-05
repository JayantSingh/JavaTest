package com.abc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


public class AccountTest {
	private static final double DOUBLE_DELTA = 0.0001;
	
    Account checkingAccount = new Account(Account.CHECKING);
    Account savingsAccount = new Account(Account.SAVINGS);
    
    Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);
    

	
    @SuppressWarnings("deprecation")
	@Test //Test calculate Interest
    public void testCalculateInterest(){
    	DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
    	Date curDate = null;
		try {
			curDate = formatter.parse("01/31/00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Date prevDate = null;
		try {
			prevDate = formatter.parse("01/01/00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	double totalAmt= 1000.00;
    	assertEquals(0.08219, savingsAccount.calculateInterest(curDate, prevDate,totalAmt), DOUBLE_DELTA);
    	
    	
    	
    }
	
	@Test //Test interestEarnedDaily
    public void testInterestEarnedDaily(){
		
		List<Transaction> transactions;
		Transaction first = null;
		DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
		Date prevDate = null;
			try {
				prevDate = formatter.parse("01/01/00");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		Account savingsAccount = new Account(Account.SAVINGS);

		
        savingsAccount.deposit(4000.0);
        transactions = savingsAccount.getTransactions();
        first = transactions.get(0);
        first.setTransactionDate(prevDate);
        savingsAccount.withdraw(200.0);
        
        //interestEarned = savingsAccount.interestEarned();
        
        assertEquals(6.6, savingsAccount.interestEarned(), DOUBLE_DELTA);
        

		
	}
	
	@Test //Test interestEarnedDaily
    public void testcheckIfWithdrawalWithinTenDays(){
		Account maxisavingsAccount = new Account(Account.MAXI_SAVINGS);

		
		maxisavingsAccount.deposit(4000.0);
		maxisavingsAccount.withdraw(200.0);
		
		assertTrue(maxisavingsAccount.checkIfWithdrawalWithinTenDays());
		
		
	}

}
