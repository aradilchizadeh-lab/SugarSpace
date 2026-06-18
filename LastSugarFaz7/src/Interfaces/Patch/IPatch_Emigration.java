package Interfaces.Patch;

import Interfaces.IPositionView;
import Interfaces.Agent.IAgent_Emigration;
import Models.Agent;

public interface IPatch_Emigration{

    public IPatch_ResourceUpdate getResource();

    public IPositionView getPosition();

    public IAgent_Emigration getPAgent();

    public void setPAgent(Agent PAgent);

}
