package Interfaces;

import Data.AgeType;
import Models.LoanInfo;

import java.util.ArrayList;

public interface IAgent_Loan {

    public boolean canBeLender();

    public int requiredSpiceAmount();

    public int requiredSugarAmount();

    public boolean needsSugar();

    public boolean needsSpice();

    public ArrayList<LoanInfo> getLoanInfos();

}
