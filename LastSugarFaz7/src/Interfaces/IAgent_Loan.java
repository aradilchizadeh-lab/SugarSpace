package Interfaces;

import Data.AgeType;
import Models.LoanInfo;

import java.util.ArrayList;

public interface IAgent_Loan extends IPositionView, IResourceUpdate, IBioView{

    public boolean canBeLender();

    public int requiredSpiceAmount();

    public int requiredSugarAmount();

    public boolean needsSugar();

    public boolean needsSpice();

    public ArrayList<LoanInfo> getLoanInfos();

    public IBehavior_Ability getBehavior();

}
