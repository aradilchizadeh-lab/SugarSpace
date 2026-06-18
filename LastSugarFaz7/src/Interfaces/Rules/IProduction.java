package Interfaces.Rules;

import java.util.ArrayList;

import Interfaces.Agent.IAgent_Production;
import Interfaces.Patch.IPatch_Production;
import Models.Agent;

public interface IProduction {

    public void production(IAgent_Production agent, IPatch_Production[][] patches, ArrayList<Agent> agents);
}
