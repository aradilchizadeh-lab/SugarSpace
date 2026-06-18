package Rules;

import Interfaces.IAgent_Aging;
import Interfaces.IPatch_Aging;
import Models.Agent;

import java.util.ArrayList;

public class Aging {
    public void ageRule(IAgent_Aging agent, IPatch_Aging[][] patches, ArrayList<Agent> agents) {
        agent.changeAge();
        //---[reset parent status and checking age status]---
        if (agent.getAge() < agent.getMaxAge()) {
            agent.setParent(false);
        } else {
            agents.remove((Agent)agent);
            patches[agent.getX()][agent.getY()].setPAgent(null);
        }
    }
}
