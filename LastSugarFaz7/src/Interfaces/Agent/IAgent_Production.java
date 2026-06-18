package Interfaces.Agent;

import Interfaces.IPositionView;

public interface IAgent_Production extends IBioView, IParentageUpdate{

    public IPositionView getPosition();

    public IWalletView getWallet();
    
    public void reproductionInherit();

    public boolean canBeParent();

    public IBehavior_Ability getBehavior();
}
