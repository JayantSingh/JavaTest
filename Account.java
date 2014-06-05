package com.abc;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Account {


    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;


    private final int accountType;
    public List<Transaction> transactions;


    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }


    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }


public void withdraw(double amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("amount must be greater than zero");
    } else {
        transactions.add(new Transaction(-amount));
    }
}

public double interestEarnedDaily() {
    //Date curDate = DateProvider.getInstance().now() ;
    
    int listSize = transactions.size();
    Transaction cur_t;
    Transaction prev_t;
    Date curDate = null;
    Date prevDate = null;
    double prevAmount = 0;
    double curAmount = 0;
    double interestEarned = 0;
    
    
    int loop_cnt = 0;

    for (int i = 0; i < listSize ; i++ ){
    	  
    	  if (loop_cnt == 0){
    	  
    		  prev_t = transactions.get(i);
    		  prevAmount = prev_t.getAmount();
    		  prevDate = prev_t.getTransactionDate();
    		  loop_cnt++;
    	  }

    	  cur_t = transactions.get(i+1);

    	  curDate = cur_t.getTransactionDate();
    	  curAmount = cur_t.getAmount();
    	  
    	  //numOfDays = curDate.compareTo(prevDate);
    	  
    	  interestEarned = calculateInterest( curDate,  prevDate, prevAmount);
    	  prevAmount = prevAmount + curAmount + interestEarned;
    	  prevDate = curDate;

    	  
  } // For loop 
    
    // Now take care of last tran date - today's date interest
     Date todayDate = DateProvider.getInstance().now();
     interestEarned = calculateInterest( todayDate,  prevDate, prevAmount);
    
    return interestEarned;
}
    // Calculate interest between two dates
    public double calculateInterest(Date curDate, Date prevDate, double totalAmt){
    	  double interest_so_far;
    	  int numOfDays = 0;
    	  //numOfDays = curDate.compareTo(prevDate);
    	  
    	  long diff = curDate.getTime() - prevDate.getTime();
    	  numOfDays = (int) (diff / 1000L / 60L / 60L / 24L);
    	  
    	  // System.out.println("Num of Days  " + curDate + ' ' + prevDate + ' ' +  numOfDays  );
    	  switch(accountType){
          case SAVINGS:
              if (totalAmt <= 1000){
              	  
              	interest_so_far = numOfDays * totalAmt * (0.001)/365;
              	//System.out.println("Num of Days 1  " + curDate + ' ' + prevDate + ' ' +  numOfDays + ' '+ interest_so_far );
              } 	
              else {
                
              	interest_so_far =  numOfDays* (1/ 365) + (totalAmt-1000) * (0.002/365) * numOfDays;
                //System.out.println("Num of Days 2 " + curDate + ' ' + prevDate + ' ' +  numOfDays + ' '+ interest_so_far );
              }  
//          case SUPER_SAVINGS:
//              if (amount <= 4000)
//                  return 20;
          case MAXI_SAVINGS:
          	if (checkIfWithdrawalWithinTenDays()){
          		interest_so_far =  totalAmt * (0.001/365) * numOfDays;
          		
          	}
          	else
          	{
          		interest_so_far =  totalAmt * (0.05/365) * numOfDays ; 	
          	}

          default:
          	interest_so_far =  totalAmt * (0.001/365) * numOfDays ;
      }
    	//System.out.println("Interest :  "  + interest_so_far );   
    	return interest_so_far;
    	
    }
    // Old Method
    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
            	if (checkIfWithdrawalWithinTenDays()){
            		return amount * 0.01;
            		
            	}
            	else
            	{
            		return amount * 0.01; 	
            	}

            default:
                return amount * 0.001;
        }
    }


    public double sumTransactions() {
       return checkIfTransactionsExist();
    }


    private double checkIfTransactionsExist() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
            
        return amount;
    }
    
    // Check If withdrawal occurred within last 10 days Make it private after testing
    public boolean checkIfWithdrawalWithinTenDays() {
        Date curDate = DateProvider.getInstance().now() ;
        int numOfDays = 0;
        int listSize = transactions.size();
        Transaction t;
        for (int i = listSize - 1; i >= 0; i-- ){
        	
        	  t = transactions.get(i);
        	  
        	  long diff = curDate.getTime() - t.getTransactionDate().getTime();
        	  numOfDays = (int) (diff / 1000L / 60L / 60L / 24L);

        	  numOfDays = curDate.compareTo(t.getTransactionDate());
        	  //System.out.println("Num of Days 1  " +  numOfDays + " amount " + t.amount );
              if ((numOfDays <= 10) && (t.amount < 0)){
            	  //System.out.println("Num of Days 2  " +  numOfDays + " amount " + t.amount );
            	  return true;
              }	  
              else if (numOfDays > 10)
            	  return false;
        }
            
            
        return false;
    }


    public int getAccountType() {
        return accountType;
    }
    
    public List<Transaction> getTransactions(){
    	return this.transactions;
    	
    }


}
