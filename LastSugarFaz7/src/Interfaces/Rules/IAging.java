package Interfaces.Rules;

import java.util.ArrayList;

import Interfaces.Agent.IAgent_Aging;
import Interfaces.Patch.IPatch_Aging;
import Models.Agent;

public interface IAging {
    public void ageRule(IAgent_Aging agent, IPatch_Aging[][] patches, ArrayList<Agent> agents); 
}
