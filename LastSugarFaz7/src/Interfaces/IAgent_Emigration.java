package Interfaces;

import Models.Agent;
import Models.Patch;

import java.util.ArrayList;

public interface IAgent_Emigration extends IPositionUpdate, IResourceUpdate, IBioView{

    public  double getWelfare(double w1, double w2);

    void survival(ArrayList<Agent> agents, Patch[][] patches );

}
