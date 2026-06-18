package Interfaces;

public interface IAgent_Trade extends IPositionView, IResourceUpdate {

    public  double getWelfare(double w1, double w2);

    public  double getMRS(double w1, double w2);

    public IBehavior_Ability getBehavior();

}
