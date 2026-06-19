package Rules;

import Interfaces.Agent.IAgent_Aging;
import Interfaces.Patch.IPatch_Aging;
import Interfaces.Rules.IAging;
import Models.Agent;

import java.util.ArrayList;

public class Aging implements IAging{
    public void ageRule(IAgent_Aging agent, IPatch_Aging[][] patches, ArrayList<Agent> agents) {
        agent.changeAge();
        //---[reset parent status and checking age status]---
        if (agent.getPhysiology().getAge() < agent.getPhysiology().getMaxAge()) {
            agent.getFertilityInfo().setParent(false);
        } else {
            agents.remove((Agent)agent);
            patches[agent.getPosition().getX()][agent.getPosition().getY()].setPAgent(null);
        }
    }
}
