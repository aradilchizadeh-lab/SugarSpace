package Interfaces;

public interface IAgent_Production extends IPositionView, IWalletView, IBioView, IParentageUpdate{

    public void reproductionInherit();

    public boolean canBeParent();

    public IBehavior_Ability getBehavior();
}
