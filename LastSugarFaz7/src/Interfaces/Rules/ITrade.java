package Interfaces.Rules;

import Interfaces.Agent.IAgent_Trade;
import Interfaces.Patch.IPatch_Trade;

public interface ITrade {

    public  void trade(IAgent_Trade agent, IPatch_Trade[][] patches);
}
