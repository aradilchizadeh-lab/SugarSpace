package Interfaces.Rules;

import java.util.ArrayList;

import Interfaces.Agent.IAgent_Emigration;
import Interfaces.Patch.IPatch_Emigration;
import Models.Agent;

public interface IEmigration {

    public void emigrate(IAgent_Emigration agent, IPatch_Emigration[][] patches, ArrayList<Agent> agents);
}
