package Interfaces.Agent;

import Interfaces.IPositionView;
import Models.LoanInfo;
import java.util.ArrayList;

public interface IAgent_Loan extends IBioView{

    public IPositionView getPosition();

    public IResourceUpdate getWallet();
    
    public boolean canBeLender();

    public int requiredSpiceAmount();

    public int requiredSugarAmount();

    public boolean needsSugar();

    public boolean needsSpice();

    public ArrayList<LoanInfo> getLoanInfos();

    public IBehavior_Ability getBehavior();

}
