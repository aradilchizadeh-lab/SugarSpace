package Interfaces;

import Data.AgeType;

public interface IIdentity_Loan {

    public int getX();

    public int getY();

    public AgeType getAgeType();

    public boolean canLoan();

    public void setCanLoan(boolean canLoan);
}
