package Interfaces;

import Models.Space;

public interface IAgent_Emigration {

    public void survival(ISpaceProvider space);

    public  double getWelfare(double w1, double w2);

}
