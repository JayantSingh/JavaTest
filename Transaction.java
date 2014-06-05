
package com.abc;


import java.text.SimpleDateFormat;
import java.util.Date;


public class Transaction {
    public final double amount;
	
    public String date_str;

   // For testing purpose only ; Make it private after test
   // @SuppressWarnings("unused")
    private Date transactionDate;
   //	public Date transactionDate;


    public Transaction(double amount) {
        this.amount = amount;
        this.setTransactionDate(DateProvider.getInstance().now());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
        date_str = sdf.format (this.getTransactionDate());
        //System.out.println("Date  " + date_str  );
    }

    // For testing purpose only
	public Date getTransactionDate() {
		return transactionDate;
	}
	
	public double getAmount() {
		return amount;
	}

    // For testing purpose only
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}


}

