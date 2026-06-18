package Interfaces.Agent;

import Interfaces.IPositionView;

public interface IAgent_Trade {

    public IPositionView getPosition();

    public IResourceUpdate getWallet();

    public  double getWelfare(double w1, double w2);

    public  double getMRS(double w1, double w2);

    public IBehavior_Ability getBehavior();

}
