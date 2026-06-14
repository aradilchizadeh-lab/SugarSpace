package Models;

import Data.Config;
import Data.ResourceType;
import Interfaces.IAgent_Loan;

public class LoanInfo {
 private IAgent_Loan Lender;
 private IAgent_Loan Borrower;
 private ResourceType ResourceType;
 private int Amount;
 private int LoanTick;

    public LoanInfo(IAgent_Loan lender, IAgent_Loan borrower, ResourceType resourceType, int amount, int loanTick) {
        Lender = lender;
        Borrower = borrower;
        ResourceType = resourceType;
        Amount = amount * (Config.Interest + 1);
        LoanTick = loanTick;
    }

    public IAgent_Loan getLender() {
        return Lender;
    }

    public IAgent_Loan getBorrower() {
        return Borrower;
    }

    public ResourceType getResourceType() {
        return ResourceType;
    }

    public int getAmount() {
        return Amount;
    }

    public int getLoanTick() {
        return LoanTick;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public void setLoanTick(int loanTick) {
        LoanTick = loanTick;
    }
}
