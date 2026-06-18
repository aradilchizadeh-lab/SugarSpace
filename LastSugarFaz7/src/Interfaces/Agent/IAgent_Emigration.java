package Interfaces.Agent;

import Models.Agent;
import Models.Patch;

import java.util.ArrayList;

import Interfaces.IPositionUpdate;

public interface IAgent_Emigration extends IBioView{

    public IPositionUpdate getPosition();

    public IResourceUpdate getWallet();

    public  double getWelfare(double w1, double w2);

    void survival(ArrayList<Agent> agents, Patch[][] patches );

}
