package Interfaces;

public interface IAgent_Trade {

    public  double getWelfare(double w1, double w2);

    public  double getMRS(double w1, double w2);

    public IWallet_Trade getWallet();

    public IIdentity_Trade getIdentity();

}
