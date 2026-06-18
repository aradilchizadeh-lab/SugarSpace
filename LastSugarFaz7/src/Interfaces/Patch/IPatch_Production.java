package Interfaces.Patch;

import Interfaces.IPositionView;
import Interfaces.Agent.IAgent_Production;
import Models.Agent;

public interface IPatch_Production {

    public IPositionView getPosition();

    public IAgent_Production getPAgent();

    public void setPAgent(Agent PAgent);

}
