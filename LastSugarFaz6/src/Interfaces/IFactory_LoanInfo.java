package Interfaces;

import Data.ResourceType;
import Models.LoanInfo;

public interface IFactory_LoanInfo {

    public static LoanInfo loanInfoCreator(IAgent_Loan lender, IAgent_Loan borrower, ResourceType type, int amount, int tick){
        return new LoanInfo(lender, borrower, type, amount, tick);
    }
}
