/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amneziac.galaga.transaction;

import com.amneziac.galaga.assignment5.Main;
import com.amneziac.galaga.account.CheckingAccount;

/**
 *
 * @author KidA
 */
public class OverdraftCharge extends ServiceCharge {
    
    public static final double AMOUNT = 10.00;
    
    public OverdraftCharge (int transNum)
    {
        super(transNum);
        this.transAmt = AMOUNT;
    }
    
//    @Override
//    public String toString()
//    {
//        String str = "Transaction #" + (transNum + 1) + " (Overdraft Charge): $"
//                + Assignment2.format.format(transAmt);
//        
//        return str;
//    }
    
}
